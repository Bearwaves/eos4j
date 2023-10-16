package com.bearwaves.eos4j;


/*JNI
#include <eos_sdk.h>
#include <cstring>
#include <vector>
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

    static native String productUserIdToString(EOS.ProductUserId productUserId) throws EOSException; /*
        int buffer_len = EOS_PRODUCTUSERID_MAX_LENGTH + 1;
        std::vector<char> out_buffer;
        out_buffer.reserve(buffer_len);
        auto user_id_ptr = EOS4J::javaLongFromObjectField(env, productUserId, "ptr");

        auto result = EOS_ProductUserId_ToString(
            reinterpret_cast<EOS_ProductUserId>(user_id_ptr),
            out_buffer.data(),
            &buffer_len
        );

        if (result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(result));
            return nullptr;
        }

        return env->NewStringUTF(out_buffer.data());
    */

}
