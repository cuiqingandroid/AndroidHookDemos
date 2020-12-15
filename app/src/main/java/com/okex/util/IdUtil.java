package com.okex.util;

import android.content.Context;

public class IdUtil {
    public static int getLayoutId(Context context, String resName) {
        return  context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }
    public static int getViewId(Context context, String viewId) {
        return  context.getResources().getIdentifier(viewId, "id", context.getPackageName());
    }
    public static int getStyleId(Context context, String styleName) {
        return  context.getResources().getIdentifier(styleName, "style", context.getPackageName());
    }
}
