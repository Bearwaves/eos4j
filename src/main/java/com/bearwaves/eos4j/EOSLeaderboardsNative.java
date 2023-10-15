package com.bearwaves.eos4j;

/*JNI
#include <eos_leaderboards.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

public class EOSLeaderboardsNative {
    static native void queryLeaderboardDefinitions(
            long handle,
            EOSLeaderboards.QueryLeaderboardDefinitionsOptions options,
            EOSLeaderboards.OnQueryLeaderboardDefinitionsCompleteCallback callback
    ); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");

        jobject start_date_obj = EOS4J::javaObjectFromObjectField(env, options, "startTime", "Ljava/util/Date;");
        jobject end_date_obj = EOS4J::javaObjectFromObjectField(env, options, "endTime", "Ljava/util/Date;");
        jclass date_class = env->FindClass("java/util/Date");
        jmethodID timestamp_mid =  env->GetMethodID(date_class, "getTime", "()J");

        int64_t start_time = start_date_obj ? env->CallLongMethod(start_date_obj, timestamp_mid) : EOS_LEADERBOARDS_TIME_UNDEFINED;
        int64_t end_time = end_date_obj ? env->CallLongMethod(end_date_obj, timestamp_mid) : EOS_LEADERBOARDS_TIME_UNDEFINED;

        EOS_Leaderboards_QueryLeaderboardDefinitionsOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_LEADERBOARDS_QUERYLEADERBOARDDEFINITIONS_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_ProductUserId>(local_user_id);
        query_options.StartTime = start_time;
        query_options.EndTime = end_time;

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Leaderboards_QueryLeaderboardDefinitions(reinterpret_cast<EOS_HLeaderboards>(handle), &query_options, callback_adapter.release(), [](const EOS_Leaderboards_OnQueryLeaderboardDefinitionsCompleteCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardDefinitionsCompleteCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(I)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode));

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardDefinitionsCompleteCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getLeaderboardDefinitionCount(long handle); /*
        EOS_Leaderboards_GetLeaderboardDefinitionCountOptions options;
        options.ApiVersion = EOS_LEADERBOARDS_GETLEADERBOARDDEFINITIONCOUNT_API_LATEST;

        return EOS_Leaderboards_GetLeaderboardDefinitionCount(reinterpret_cast<EOS_HLeaderboards>(handle), &options);
    */

    static native EOSLeaderboards.LeaderboardDefinition copyLeaderboardDefinitionByIndex(
            long handle,
            EOSLeaderboards.CopyLeaderboardDefinitionByIndexOptions options
    ); /*
        auto index = EOS4J::javaIntFromObjectField(env, options, "index");

        EOS_Leaderboards_CopyLeaderboardDefinitionByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDDEFINITIONBYINDEX_API_LATEST;
        copy_options.LeaderboardIndex = static_cast<int>(index);

        EOS_Leaderboards_Definition* out_definition;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardDefinitionByIndex(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_definition);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardDefinition");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;Lcom/bearwaves/eos4j/EOSLeaderboards$Aggregation;Ljava/util/Date;Ljava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject start_time = out_definition->StartTime == EOS_LEADERBOARDS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_definition->StartTime);
        jobject end_time = out_definition->EndTime == EOS_LEADERBOARDS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_definition->EndTime);

        auto aggregation_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$Aggregation");
        jmethodID aggregation_func = env->GetStaticMethodID(aggregation_cls, "fromInt", "(I)Lcom/bearwaves/eos4j/EOSLeaderboards$Aggregation;");
        jobject aggregation = env->CallStaticObjectMethod(aggregation_cls, aggregation_func, out_definition->Aggregation);

        return env->NewObject(result_cls, result_ctor, (long long) out_definition, env->NewStringUTF(out_definition->LeaderboardId), env->NewStringUTF(out_definition->StatName), aggregation, start_time, end_time);
     */

    static native EOSLeaderboards.LeaderboardDefinition copyLeaderboardDefinitionByLeaderboardId(
            long handle,
            EOSLeaderboards.CopyLeaderboardDefinitionByLeaderboardIdOptions options
    ); /*
        auto leaderboard_id = EOS4J::javaStringFromObjectField(env, options, "id");

        EOS_Leaderboards_CopyLeaderboardDefinitionByLeaderboardIdOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDDEFINITIONBYLEADERBOARDID_API_LATEST;
        copy_options.LeaderboardId = leaderboard_id->c_str();

        EOS_Leaderboards_Definition* out_definition;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardDefinitionByLeaderboardId(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_definition);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardDefinition");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;Lcom/bearwaves/eos4j/EOSLeaderboards$Aggregation;Ljava/util/Date;Ljava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject start_time = out_definition->StartTime == EOS_LEADERBOARDS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_definition->StartTime);
        jobject end_time = out_definition->EndTime == EOS_LEADERBOARDS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_definition->EndTime);

        auto aggregation_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$Aggregation");
        jmethodID aggregation_func = env->GetStaticMethodID(aggregation_cls, "fromInt", "(I)Lcom/bearwaves/eos4j/EOSLeaderboards$Aggregation;");
        jobject aggregation = env->CallStaticObjectMethod(aggregation_cls, aggregation_func, out_definition->Aggregation);

        return env->NewObject(result_cls, result_ctor, (long long) out_definition, env->NewStringUTF(out_definition->LeaderboardId), env->NewStringUTF(out_definition->StatName), aggregation, start_time, end_time);
     */

    static native void releaseLeaderboardDefinition(long handle); /*
        EOS_Leaderboards_Definition_Release(reinterpret_cast<EOS_Leaderboards_Definition*>(handle));
    */
}
