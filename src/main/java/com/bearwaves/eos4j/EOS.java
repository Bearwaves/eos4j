package com.bearwaves.eos4j;

import org.lwjgl.system.Library;
import org.lwjgl.system.Platform;

public class EOS {

    public static boolean loadLibraries() {
        try {
            switch (Platform.get()) {
                case LINUX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Linux-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
                case MACOSX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Mac-Shipping");
                    switch (Platform.getArchitecture()) {
                        case X64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                            break;
                        case ARM64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4jarm64");
                            break;
                    }
                    break;
                case WINDOWS:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Win64-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
            }
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    public static void initialize(InitializeOptions options) throws EOSException {
        int result = initializeNative(options);
        if (result != 0) {
            throw new EOSException(result);
        }
    }

    public static void shutdown() throws EOSException {
        int result = shutdownNative();
        if (result != 0) {
            throw new EOSException(result);
        }
    }

    public static class InitializeOptions {
        public final String productName;
        public final String productVersion;

        public InitializeOptions(String productName, String productVersion) {
            this.productName = productName;
            this.productVersion = productVersion;
        }
    }

    /*JNI
    #include <eos_sdk.h>
    #include <cstring>
    #include "jni_utils.h"
    */

    private static native int initializeNative(InitializeOptions options); /*
        auto product_name = EOS4J::javaStringFromObjectField(env, options, "productName");
        auto product_version = EOS4J::javaStringFromObjectField(env, options, "productVersion");

        EOS_InitializeOptions sdk_options;
        memset(&sdk_options, 0, sizeof(sdk_options));
        sdk_options.ApiVersion = EOS_INITIALIZE_API_LATEST;
        sdk_options.ProductName = product_name->c_str();
        sdk_options.ProductVersion = product_version->c_str();
        auto result = static_cast<int>(EOS_Initialize(&sdk_options));

        return result;
    */

    private static native int shutdownNative(); /*
        return static_cast<int>(EOS_Shutdown());
    */

}
