# Android Hook Demos
一些android 逆向的一些demo

## 重打包，签名hook
1. 通过JAVA动态代理机制，代理`android.content.pm.IPackageManager`接口的`getPackageInfo`方法，返回原始apk的签名数据
2. 利用反射修改`android.app.ActivityThread`实例的`sPackageManager`对象为动态代理以及`context.getPackageManager()`对象的`mPM`对象为动态代理

代码如下：
```
try {
    Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
    Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
    Object currentActivityThread = currentActivityThreadMethod.invoke(null);
    Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
    sPackageManagerField.setAccessible(true);
    final Object sPackageManager = sPackageManagerField.get(currentActivityThread);
    Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
    Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(), new Class[]{iPackageManagerInterface}, new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getPackageInfo".equals(method.getName())) {
                Log.d("hookPMS getPackageInfo invoke");
                Integer flag = (Integer)args[1];
                if (flag == 64) {
                    Signature sign = new Signature(ORIGIN_SIGN);
                    PackageInfo info = (PackageInfo)method.invoke(sPackageManager, args);
                    info.signatures[0] = sign;
                    return info;
                }
            }

            return method.invoke(sPackageManager, args);
        }
    });
    sPackageManagerField.set(currentActivityThread, proxy);
    PackageManager pm = context.getPackageManager();
    Field mPmField = pm.getClass().getDeclaredField("mPM");
    mPmField.setAccessible(true);
    mPmField.set(pm, proxy);
    Log.d("hookPMS success ");
} catch (Exception e) {
    Log.d("hookPMS exception " + e.getMessage());
}
```

## 动态修改应用的Launcher Icon以及首界面
1. 在`AndroidManifest.xml`中的`activity-alias`的name和targetActivity不能相同，否则会导致安装失败
2. targetActivity必须在`activity-alias`之前申明，可以和主Launcher是一个