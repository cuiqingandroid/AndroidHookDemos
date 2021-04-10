//
// Created by cuiqing on 2021/4/10.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL Java_com_txxia_demos_jni_NativeInterface_nativeString(JNIEnv *env,
                                                               jclass thiz){
    std::string hello = "str from native";
    return env->NewStringUTF(hello.c_str());
}

#include <jni.h>
#include <string>
int main(int argc, char **argv) {
    fprintf(stderr, "hello cq");
    return 0;
}