package com.txxia.demos.jni;

public class NativeInterface {

    static {
        System.loadLibrary("mylib");
    }
    public static native String nativeString();
}
