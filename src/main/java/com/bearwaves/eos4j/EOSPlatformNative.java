package com.bearwaves.eos4j;

/*JNI
#include <eos_sdk.h>
#include <cstring>
#include "jni_utils.h"
*/

class EOSPlatformNative {
    static native long create(EOSPlatform.Options options); /*
        auto product_id = EOS4J::javaStringFromObjectField(env, options, "productId");
        auto sandbox_id = EOS4J::javaStringFromObjectField(env, options, "sandboxId");
        auto deployment_id = EOS4J::javaStringFromObjectField(env, options, "deploymentId");
        auto client_id = EOS4J::javaStringFromObjectField(env, options, "clientId");
        auto client_secret = EOS4J::javaStringFromObjectField(env, options, "clientSecret");
        int flags = EOS4J::javaIntFromObjectField(env, options, "flags");

        EOS_Platform_Options platform_options;
        std::memset(&platform_options, 0, sizeof(platform_options));
        platform_options.ApiVersion = EOS_PLATFORM_OPTIONS_API_LATEST;
        platform_options.ProductId = product_id->c_str();
        platform_options.SandboxId = sandbox_id->c_str();
        platform_options.DeploymentId = deployment_id->c_str();
        platform_options.Flags = flags;
        platform_options.ClientCredentials.ClientId = client_id->c_str();
        platform_options.ClientCredentials.ClientSecret = client_secret->c_str();
        EOS_HPlatform platform_interface = EOS_Platform_Create(&platform_options);
        return (long long) platform_interface;
    */

    static native void release(long handle); /*
        EOS_Platform_Release(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native void tick(long handle); /*
        EOS_Platform_Tick(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native long getAuthHandle(long handle); /*
        return (long long) EOS_Platform_GetAuthInterface(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native long getConnectHandle(long handle); /*
        return (long long) EOS_Platform_GetConnectInterface(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native long getStatsHandle(long handle); /*
        return (long long) EOS_Platform_GetStatsInterface(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native long getAchievementsHandle(long handle); /*
        return (long long) EOS_Platform_GetAchievementsInterface(reinterpret_cast<EOS_HPlatform>(handle));
    */

    static native long getLeaderboardsHandle(long handle); /*
        return (long long) EOS_Platform_GetLeaderboardsInterface(reinterpret_cast<EOS_HPlatform>(handle));
    */
}
