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
    */

    private static native int initializeNative(InitializeOptions options); /*
        jclass cls = env->GetObjectClass(options);

        jfieldID product_name_id = env->GetFieldID(cls, "productName", "Ljava/lang/String;");
        jstring product_name_object = (jstring) env->GetObjectField(options, product_name_id);
        const char* product_name = env->GetStringUTFChars(product_name_object, nullptr);

        jfieldID product_version_id = env->GetFieldID(cls, "productVersion", "Ljava/lang/String;");
        jstring product_version_object = (jstring) env->GetObjectField(options, product_version_id);
        const char* product_version = env->GetStringUTFChars(product_version_object, nullptr);

        EOS_InitializeOptions sdk_options;
        memset(&sdk_options, 0, sizeof(sdk_options));
        sdk_options.ApiVersion = EOS_INITIALIZE_API_LATEST;
        sdk_options.ProductName = product_name;
        sdk_options.ProductVersion = product_version;
        auto result = static_cast<int>(EOS_Initialize(&sdk_options));

        env->ReleaseStringUTFChars(product_name_object, product_name);
        env->ReleaseStringUTFChars(product_version_object, product_version);

        return result;
    */

    private static native int shutdownNative(); /*
        return static_cast<int>(EOS_Shutdown());
    */

}
