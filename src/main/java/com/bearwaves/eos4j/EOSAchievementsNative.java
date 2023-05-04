package com.bearwaves.eos4j;

/*JNI
#include <eos_achievements.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

class EOSAchievementsNative {

    static native void unlockAchievements(
            long handle,
            EOSAchievements.UnlockAchievementsOptions options,
            EOSAchievements.OnUnlockAchievementsCompleteCallback callback
    ); /*
        jobject user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "userId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto user_id = EOS4J::javaLongFromObjectField(env, user_id_obj, "ptr");
        std::vector<EOS4J::JavaString> achievement_ids = EOS4J::javaStringVectorFromObjectField(env, options, "achievementIds");
        const char* ids[achievement_ids.size()];
        for (size_t i = 0; i < achievement_ids.size(); i++) {
            ids[i] = achievement_ids.at(i).c_str();
        }

        EOS_Achievements_UnlockAchievementsOptions unlock_options;
        memset(&unlock_options, 0, sizeof(unlock_options));
        unlock_options.ApiVersion = EOS_ACHIEVEMENTS_UNLOCKACHIEVEMENTS_API_LATEST;
        unlock_options.UserId = reinterpret_cast<EOS_ProductUserId>(user_id);
        unlock_options.AchievementIds = ids;
        unlock_options.AchievementsCount = achievement_ids.size();

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Achievements_UnlockAchievements(
            reinterpret_cast<EOS_HAchievements>(handle),
            &unlock_options,
            callback_adapter.release(),
            [](const EOS_Achievements_OnUnlockAchievementsCompleteCallbackInfo* data) -> void {
                std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
                callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                    jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
                    jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
                    auto user_id = env->NewObject(puid_cls, puid_ctor, data->UserId);

                    jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSAchievements$OnUnlockAchievementsCompleteCallbackInfo");
                    jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$ProductUserId;I)V");
                    auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), user_id, static_cast<int>(data->AchievementsCount));

                    jclass cls = env->GetObjectClass(callback);
                    jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSAchievements$OnUnlockAchievementsCompleteCallbackInfo;)V");
                    env->CallVoidMethod(callback, mid, callback_info);
                });
            }
        );
     */

}
