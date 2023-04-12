package com.bearwaves.eos4j;

public class EOSException extends Exception {
    private final int errorCode;
    private final String message;

    public EOSException(String message) {
        this.message = message;
        this.errorCode = 0;
    }

    public EOSException(int errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (this.message != null) {
            return this.message;
        }
        return "EOS Error code: " + getErrorString(errorCode);
    }

    /*JNI
    #include <eos_sdk.h>
    */

    static native String getErrorString(int errorCode); /*
        return env->NewStringUTF(EOS_EResult_ToString(static_cast<EOS_EResult>(errorCode)));
    */
}
