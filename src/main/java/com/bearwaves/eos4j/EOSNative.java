package com.bearwaves.eos4j;


/*JNI
#include <eos_sdk.h>
#include <cstring>
#include "jni_utils.h"
*/
class EOSNative {

    static native int initialize(EOS.InitializeOptions options); /*
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

    static native int shutdown(); /*
        return static_cast<int>(EOS_Shutdown());
    */

}
