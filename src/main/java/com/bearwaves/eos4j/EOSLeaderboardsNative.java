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

    static native void queryLeaderboardRanks(
            long handle,
            EOSLeaderboards.QueryLeaderboardRanksOptions options,
            EOSLeaderboards.OnQueryLeaderboardRanksCompleteCallback callback
    ); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto leaderboard_id = EOS4J::javaStringFromObjectField(env, options, "leaderboardId");

        EOS_Leaderboards_QueryLeaderboardRanksOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_LEADERBOARDS_QUERYLEADERBOARDRANKS_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_ProductUserId>(local_user_id);
        query_options.LeaderboardId = leaderboard_id->c_str();

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Leaderboards_QueryLeaderboardRanks(reinterpret_cast<EOS_HLeaderboards>(handle), &query_options, callback_adapter.release(), [](const EOS_Leaderboards_OnQueryLeaderboardRanksCompleteCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardRanksCompleteCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(I)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode));

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardRanksCompleteCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getLeaderboardRecordCount(long handle); /*
        EOS_Leaderboards_GetLeaderboardRecordCountOptions options;
        options.ApiVersion = EOS_LEADERBOARDS_GETLEADERBOARDRECORDCOUNT_API_LATEST;

        return EOS_Leaderboards_GetLeaderboardRecordCount(reinterpret_cast<EOS_HLeaderboards>(handle), &options);
    */

    static native EOSLeaderboards.LeaderboardRecord copyLeaderboardRecordByIndex(
            long handle,
            EOSLeaderboards.CopyLeaderboardRecordByIndexOptions options
    ) throws EOSException; /*
        auto index = EOS4J::javaIntFromObjectField(env, options, "index");

        EOS_Leaderboards_CopyLeaderboardRecordByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDRECORDBYINDEX_API_LATEST;
        copy_options.LeaderboardRecordIndex = static_cast<int>(index);

        EOS_Leaderboards_LeaderboardRecord* out_record;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardRecordByIndex(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_record);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardRecord");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLcom/bearwaves/eos4j/EOS$ProductUserId;IILjava/lang/String;)V");

        jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
        jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
        auto user_id = env->NewObject(puid_cls, puid_ctor, out_record->UserId);

        return env->NewObject(result_cls, result_ctor, (long long) out_record, user_id, out_record->Rank, out_record->Score, env->NewStringUTF(out_record->UserDisplayName));
    */

    static native EOSLeaderboards.LeaderboardRecord copyLeaderboardRecordByUserId(
            long handle,
            EOSLeaderboards.CopyLeaderboardRecordByUserIdOptions options
    ) throws EOSException; /*
        jobject user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "userId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto user_id = EOS4J::javaLongFromObjectField(env, user_id_obj, "ptr");

        EOS_Leaderboards_CopyLeaderboardRecordByUserIdOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDRECORDBYUSERID_API_LATEST;
        copy_options.UserId = reinterpret_cast<EOS_ProductUserId>(user_id);

        EOS_Leaderboards_LeaderboardRecord* out_record;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardRecordByUserId(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_record);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardRecord");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLcom/bearwaves/eos4j/EOS$ProductUserId;IILjava/lang/String;)V");

        jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
        jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
        auto record_user_id = env->NewObject(puid_cls, puid_ctor, out_record->UserId);

        return env->NewObject(result_cls, result_ctor, (long long) out_record, record_user_id, out_record->Rank, out_record->Score, env->NewStringUTF(out_record->UserDisplayName));
    */

    static native void releaseLeaderboardRecord(long handle); /*
        EOS_Leaderboards_LeaderboardRecord_Release(reinterpret_cast<EOS_Leaderboards_LeaderboardRecord*>(handle));
    */

    static native void queryLeaderboardUserScores(
            long handle,
            EOSLeaderboards.QueryLeaderboardUserScoresOptions options,
            EOSLeaderboards.OnQueryLeaderboardUserScoresCompleteCallback callback
    ); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");

        jobject start_date_obj = EOS4J::javaObjectFromObjectField(env, options, "startTime", "Ljava/util/Date;");
        jobject end_date_obj = EOS4J::javaObjectFromObjectField(env, options, "endTime", "Ljava/util/Date;");
        jclass date_class = env->FindClass("java/util/Date");
        jmethodID timestamp_mid =  env->GetMethodID(date_class, "getTime", "()J");
        int64_t start_time = start_date_obj ? env->CallLongMethod(start_date_obj, timestamp_mid) : EOS_LEADERBOARDS_TIME_UNDEFINED;
        int64_t end_time = end_date_obj ? env->CallLongMethod(end_date_obj, timestamp_mid) : EOS_LEADERBOARDS_TIME_UNDEFINED;

        // User IDs
        std::vector<EOS_ProductUserId> user_ids;
        jobjectArray user_ids_array = (jobjectArray) EOS4J::javaObjectFromObjectField(env, options, "userIds", "[Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        if (user_ids_array != nullptr) {
            int user_ids_count = env->GetArrayLength(user_ids_array);
            for (int i = 0; i < user_ids_count; i++) {
                jobject user_id_obj = env->GetObjectArrayElement(user_ids_array, i);
                auto user_id = EOS4J::javaLongFromObjectField(env, user_id_obj, "ptr");
                user_ids.push_back(reinterpret_cast<EOS_ProductUserId>(user_id));
            }
        }

        // Stat infos
        jobjectArray stat_info_array = (jobjectArray) EOS4J::javaObjectFromObjectField(env, options, "statInfo", "[Lcom/bearwaves/eos4j/EOSLeaderboards$UserScoresQueryStatInfo;");
        std::vector<EOS_Leaderboards_UserScoresQueryStatInfo> stat_infos;
        // Stop these being disposed prematurely.
        std::vector<std::unique_ptr<EOS4J::JavaString>> stat_names;
        if (stat_info_array != nullptr) {
            int stat_info_count = env->GetArrayLength(stat_info_array);
            for (int i = 0; i < stat_info_count; i++) {
                jobject stat_info_obj = env->GetObjectArrayElement(stat_info_array, i);
                EOS_Leaderboards_UserScoresQueryStatInfo stat_info;
                memset(&stat_info, 0, sizeof(stat_info));
                stat_info.ApiVersion = EOS_LEADERBOARDS_USERSCORESQUERYSTATINFO_API_LATEST;
                auto stat_name = EOS4J::javaStringFromObjectField(env, stat_info_obj, "statName");
                stat_names.push_back(std::move(stat_name));
                stat_info.StatName = stat_names.back()->c_str();
                auto aggregation = EOS4J::javaEnumValueFromObjectField(env, stat_info_obj, "aggregation", "Lcom/bearwaves/eos4j/EOSLeaderboards$Aggregation;");
                stat_info.Aggregation = static_cast<EOS_ELeaderboardAggregation>(aggregation);
                stat_infos.push_back(std::move(stat_info));
            }
        }

        EOS_Leaderboards_QueryLeaderboardUserScoresOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_LEADERBOARDS_QUERYLEADERBOARDUSERSCORES_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_ProductUserId>(local_user_id);
        query_options.StartTime = start_time;
        query_options.EndTime = end_time;
        query_options.UserIds = user_ids.data();
        query_options.UserIdsCount = user_ids.size();
        query_options.StatInfo = stat_infos.data();
        query_options.StatInfoCount = stat_infos.size();

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Leaderboards_QueryLeaderboardUserScores(reinterpret_cast<EOS_HLeaderboards>(handle), &query_options, callback_adapter.release(), [](const EOS_Leaderboards_OnQueryLeaderboardUserScoresCompleteCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardUserScoresCompleteCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(I)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode));

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSLeaderboards$OnQueryLeaderboardUserScoresCompleteCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getLeaderboardUserScoreCount(long handle, EOSLeaderboards.GetLeaderboardUserScoreCountOptions options); /*
        EOS_Leaderboards_GetLeaderboardUserScoreCountOptions count_options;
        count_options.ApiVersion = EOS_LEADERBOARDS_GETLEADERBOARDUSERSCORECOUNT_API_LATEST;
        auto stat_name = EOS4J::javaStringFromObjectField(env, options, "statName");
        count_options.StatName = stat_name->c_str();

        return EOS_Leaderboards_GetLeaderboardUserScoreCount(reinterpret_cast<EOS_HLeaderboards>(handle), &count_options);
    */

    static native EOSLeaderboards.LeaderboardUserScore copyLeaderboardUserScoreByIndex(
            long handle,
            EOSLeaderboards.CopyLeaderboardUserScoreByIndexOptions options
    ) throws EOSException; /*
        auto index = EOS4J::javaIntFromObjectField(env, options, "index");

        EOS_Leaderboards_CopyLeaderboardUserScoreByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDUSERSCOREBYINDEX_API_LATEST;
        copy_options.LeaderboardUserScoreIndex = static_cast<int>(index);
        auto stat_name = EOS4J::javaStringFromObjectField(env, options, "statName");
        copy_options.StatName = stat_name->c_str();

        EOS_Leaderboards_LeaderboardUserScore* out_score;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardUserScoreByIndex(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_score);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardUserScore");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLcom/bearwaves/eos4j/EOS$ProductUserId;I)V");

        jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
        jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
        auto user_id = env->NewObject(puid_cls, puid_ctor, out_score->UserId);

        return env->NewObject(result_cls, result_ctor, (long long) out_score, user_id, out_score->Score);
    */

    static native EOSLeaderboards.LeaderboardUserScore copyLeaderboardUserScoreByUserId(
            long handle,
            EOSLeaderboards.CopyLeaderboardUserScoreByUserIdOptions options
    ) throws EOSException; /*
        jobject user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "userId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto user_id = EOS4J::javaLongFromObjectField(env, user_id_obj, "ptr");

        EOS_Leaderboards_CopyLeaderboardUserScoreByUserIdOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_LEADERBOARDS_COPYLEADERBOARDUSERSCOREBYINDEX_API_LATEST;
        copy_options.UserId = reinterpret_cast<EOS_ProductUserId>(user_id);
        auto stat_name = EOS4J::javaStringFromObjectField(env, options, "statName");
        copy_options.StatName = stat_name->c_str();

        EOS_Leaderboards_LeaderboardUserScore* out_score;
        auto copy_result = EOS_Leaderboards_CopyLeaderboardUserScoreByUserId(reinterpret_cast<EOS_HLeaderboards>(handle), &copy_options, &out_score);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSLeaderboards$LeaderboardUserScore");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLcom/bearwaves/eos4j/EOS$ProductUserId;I)V");

        jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
        jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
        auto out_user_id = env->NewObject(puid_cls, puid_ctor, out_score->UserId);

        return env->NewObject(result_cls, result_ctor, (long long) out_score, out_user_id, out_score->Score);
    */

    static native void releaseLeaderboardUserScore(long handle); /*
        EOS_Leaderboards_LeaderboardUserScore_Release(reinterpret_cast<EOS_Leaderboards_LeaderboardUserScore*>(handle));
    */
}
