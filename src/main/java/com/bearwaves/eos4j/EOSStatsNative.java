package com.bearwaves.eos4j;

/*JNI
#include <eos_stats.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

class EOSStatsNative {

    static native void ingestStat(long handle, EOSStats.IngestStatOptions options, EOSStats.OnIngestStatCompleteCallback callback); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        jobject target_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "targetUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto target_user_id = EOS4J::javaLongFromObjectField(env, target_user_id_obj, "ptr");

        jobjectArray stats_array = (jobjectArray) EOS4J::javaObjectFromObjectField(env, options, "stats", "[Lcom/bearwaves/eos4j/EOSStats$IngestData;");
        int stats_count = env->GetArrayLength(stats_array);
        EOS_Stats_IngestData stat_data[stats_count];
        std::vector<std::unique_ptr<EOS4J::JavaString>> stat_names;
        for (int i = 0; i < stats_count; i++) {
            jobject stat = env->GetObjectArrayElement(stats_array, i);
            stat_data[i].ApiVersion = EOS_STATS_INGESTDATA_API_LATEST;
            stat_names.push_back(EOS4J::javaStringFromObjectField(env, stat, "statName"));
            stat_data[i].StatName = stat_names.back()->c_str();
            stat_data[i].IngestAmount = static_cast<int>(EOS4J::javaIntFromObjectField(env, stat, "ingestAmount"));
        }

        EOS_Stats_IngestStatOptions stats_options;
        memset(&stats_options, 0, sizeof(stats_options));
        stats_options.ApiVersion = EOS_STATS_INGESTSTAT_API_LATEST;
        stats_options.LocalUserId = reinterpret_cast<EOS_ProductUserId>(local_user_id);
        stats_options.TargetUserId = reinterpret_cast<EOS_ProductUserId>(target_user_id);
        stats_options.StatsCount = stats_count;
        stats_options.Stats = stat_data;

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Stats_IngestStat(
            reinterpret_cast<EOS_HStats>(handle),
            &stats_options,
            callback_adapter.release(),
            [](const EOS_Stats_IngestStatCompleteCallbackInfo* data) -> void {
                std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
                callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                    jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
                    jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
                    auto local_user_id = env->NewObject(puid_cls, puid_ctor, data->LocalUserId);
                    auto target_user_id = env->NewObject(puid_cls, puid_ctor, data->TargetUserId);

                    jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSStats$IngestStatCompleteCallbackInfo");
                    jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$ProductUserId;Lcom/bearwaves/eos4j/EOS$ProductUserId;)V");
                    auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id, target_user_id);

                    jclass cls = env->GetObjectClass(callback);
                    jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSStats$IngestStatCompleteCallbackInfo;)V");
                    env->CallVoidMethod(callback, mid, callback_info);
                });
            }
        );
    */

    static native void queryStats(long handle, EOSStats.QueryStatsOptions options, EOSStats.OnQueryStatsCompleteCallback callback); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        jobject target_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "targetUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto target_user_id = EOS4J::javaLongFromObjectField(env, target_user_id_obj, "ptr");

        jobject start_date_obj = EOS4J::javaObjectFromObjectField(env, options, "startTime", "Ljava/util/Date;");
        jobject end_date_obj = EOS4J::javaObjectFromObjectField(env, options, "endTime", "Ljava/util/Date;");
        jclass date_class = env->FindClass("java/util/Date");
        jmethodID timestamp_mid =  env->GetMethodID(date_class, "getTime", "()J");

        int64_t start_time = start_date_obj ? env->CallLongMethod(start_date_obj, timestamp_mid) / 1000 : EOS_STATS_TIME_UNDEFINED;
        int64_t end_time = end_date_obj ? env->CallLongMethod(end_date_obj, timestamp_mid) / 1000 : EOS_STATS_TIME_UNDEFINED;

        std::vector<EOS4J::JavaString> stat_names = EOS4J::javaStringVectorFromObjectField(env, options, "statNames");
        const char* stats[stat_names.size()];
        for (size_t i = 0; i < stat_names.size(); i++) {
            stats[i] = stat_names.at(i).c_str();
        }

        EOS_Stats_QueryStatsOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_STATS_QUERYSTATS_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_ProductUserId>(local_user_id);
        query_options.TargetUserId = reinterpret_cast<EOS_ProductUserId>(target_user_id);
        query_options.StartTime = start_time;
        query_options.EndTime = end_time;
        query_options.StatNames = stat_names.size() == 0 ? nullptr : stats;
        query_options.StatNamesCount = stat_names.size();

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Stats_QueryStats(reinterpret_cast<EOS_HStats>(handle), &query_options, callback_adapter.release(), [](const EOS_Stats_OnQueryStatsCompleteCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
                jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(puid_cls, puid_ctor, data->LocalUserId);
                auto target_user_id = env->NewObject(puid_cls, puid_ctor, data->TargetUserId);

                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSStats$OnQueryStatsCompleteCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$ProductUserId;Lcom/bearwaves/eos4j/EOS$ProductUserId;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id, target_user_id);

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSStats$OnQueryStatsCompleteCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getStatsCount(long handle, long targetUserIdPtr); /*
        EOS_ProductUserId user_id = reinterpret_cast<EOS_ProductUserId>(targetUserIdPtr);

        EOS_Stats_GetStatCountOptions options;
        options.ApiVersion = EOS_STATS_GETSTATSCOUNT_API_LATEST;
        options.TargetUserId = user_id;

        return EOS_Stats_GetStatsCount(reinterpret_cast<EOS_HStats>(handle), &options);
    */

    static native EOSStats.Stat copyStatByName(long handle, EOSStats.CopyStatByNameOptions options) throws EOSException; /*
        auto stat_name = EOS4J::javaStringFromObjectField(env, options, "name");
        jobject target_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "targetUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto target_user_id = EOS4J::javaLongFromObjectField(env, target_user_id_obj, "ptr");

        EOS_Stats_CopyStatByNameOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_STATS_COPYSTATBYNAME_API_LATEST;
        copy_options.TargetUserId = reinterpret_cast<EOS_ProductUserId>(target_user_id);
        copy_options.Name = stat_name->c_str();

        EOS_Stats_Stat* out_stat;
        auto copy_result = EOS_Stats_CopyStatByName(reinterpret_cast<EOS_HStats>(handle), &copy_options, &out_stat);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSStats$Stat");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/util/Date;Ljava/util/Date;I)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject start_time = out_stat->StartTime == EOS_STATS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_stat->StartTime * 1000);
        jobject end_time = out_stat->EndTime == EOS_STATS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_stat->EndTime * 1000);

        return env->NewObject(result_cls, result_ctor, (long long) out_stat, env->NewStringUTF(out_stat->Name), start_time, end_time, out_stat->Value);
    */

    static native EOSStats.Stat copyStatByIndex(long handle, EOSStats.CopyStatByIndexOptions options) throws EOSException; /*
        auto stat_index = EOS4J::javaIntFromObjectField(env, options, "statIndex");
        jobject target_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "targetUserId", "Lcom/bearwaves/eos4j/EOS$ProductUserId;");
        auto target_user_id = EOS4J::javaLongFromObjectField(env, target_user_id_obj, "ptr");

        EOS_Stats_CopyStatByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_STATS_COPYSTATBYINDEX_API_LATEST;
        copy_options.TargetUserId = reinterpret_cast<EOS_ProductUserId>(target_user_id);
        copy_options.StatIndex = static_cast<int>(stat_index);

        EOS_Stats_Stat* out_stat;
        auto copy_result = EOS_Stats_CopyStatByIndex(reinterpret_cast<EOS_HStats>(handle), &copy_options, &out_stat);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSStats$Stat");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/util/Date;Ljava/util/Date;I)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject start_time = out_stat->StartTime == EOS_STATS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_stat->StartTime * 1000);
        jobject end_time = out_stat->EndTime == EOS_STATS_TIME_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_stat->EndTime * 1000);

        return env->NewObject(result_cls, result_ctor, (long long) out_stat, env->NewStringUTF(out_stat->Name), start_time, end_time, out_stat->Value);
    */

    static native void releaseStat(long handle); /*
        EOS_Stats_Stat_Release(reinterpret_cast<EOS_Stats_Stat*>(handle));
    */

}
