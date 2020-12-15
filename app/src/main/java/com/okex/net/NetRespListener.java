package com.okex.net;


public interface NetRespListener<T> {

    /**
     * 网络回调方法
     *
     * @param data   数据数组，可变长数据结构，可用泛型约束，如果需要不同的返回数据类型，使用{@link Object}即可。<br>
     *               具体回调使用根据网络方法的注释获取数据。
     */
    void onMessageResponse(T data);

    void onMessageError(ErrorStatus errorCode);
}
