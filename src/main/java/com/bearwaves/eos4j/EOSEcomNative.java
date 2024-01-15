package com.bearwaves.eos4j;

/*JNI
#include <eos_ecom.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

class EOSEcomNative {
    static native void queryOffers(long handle, EOSEcom.QueryOffersOptions options, EOSEcom.OnQueryOffersCompleteCallback callback); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto override_catalog_namespace = EOS4J::javaStringFromObjectField(env, options, "overrideCatalogNamespace");

        EOS_Ecom_QueryOffersOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_ECOM_QUERYOFFERS_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        if (override_catalog_namespace) {
            query_options.OverrideCatalogNamespace = override_catalog_namespace->c_str();
        }

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Ecom_QueryOffers(reinterpret_cast<EOS_HEcom>(handle), &query_options, callback_adapter.release(), [](const EOS_Ecom_QueryOffersCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass eaid_cls = env->FindClass("com/bearwaves/eos4j/EOS$EpicAccountId");
                jmethodID eaid_ctor = env->GetMethodID(eaid_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(eaid_cls, eaid_ctor, data->LocalUserId);

                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSEcom$QueryOffersCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$EpicAccountId;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id);

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSEcom$QueryOffersCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getOfferCount(long handle); /*
        EOS_Ecom_GetOfferCountOptions options;
        options.ApiVersion = EOS_ECOM_GETOFFERCOUNT_API_LATEST;

        return EOS_Ecom_GetOfferCount(reinterpret_cast<EOS_HEcom>(handle), &options);
    */
}

