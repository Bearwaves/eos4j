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

    static native int getOfferCount(long handle, EOSEcom.GetOfferCountOptions options); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");

        EOS_Ecom_GetOfferCountOptions count_options;
        memset(&count_options, 0, sizeof(count_options));
        count_options.ApiVersion = EOS_ECOM_GETOFFERCOUNT_API_LATEST;
        count_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);

        return EOS_Ecom_GetOfferCount(reinterpret_cast<EOS_HEcom>(handle), &count_options);
    */

    static native EOSEcom.CatalogOffer copyOfferByIndex(
            long handle, EOSEcom.CopyOfferByIndexOptions options
    ) throws EOSException; /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto index = EOS4J::javaIntFromObjectField(env, options, "offerIndex");

        EOS_Ecom_CopyOfferByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_ECOM_COPYOFFERBYINDEX_API_LATEST;
        copy_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        copy_options.OfferIndex = static_cast<int>(index);

        EOS_Ecom_CatalogOffer* out_offer;
        auto copy_result = EOS_Ecom_CopyOfferByIndex(reinterpret_cast<EOS_HEcom>(handle), &copy_options, &out_offer);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSEcom$CatalogOffer");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/util/Date;IZJJILjava/util/Date;Ljava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject expiration = out_offer->ExpirationTimestamp == EOS_ECOM_CATALOGOFFER_EXPIRATIONTIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->ExpirationTimestamp);
        jobject release_date = out_offer->ReleaseDateTimestamp == EOS_ECOM_CATALOGOFFER_RELEASEDATETIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->ReleaseDateTimestamp);
        jobject effective_date = out_offer->EffectiveDateTimestamp == EOS_ECOM_CATALOGOFFER_EFFECTIVEDATETIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->EffectiveDateTimestamp);

        return env->NewObject(
            result_cls,
            result_ctor,
            (long long) out_offer,
            out_offer->ServerIndex,
            env->NewStringUTF(out_offer->CatalogNamespace),
            env->NewStringUTF(out_offer->Id),
            env->NewStringUTF(out_offer->TitleText),
            env->NewStringUTF(out_offer->DescriptionText),
            env->NewStringUTF(out_offer->LongDescriptionText),
            env->NewStringUTF(out_offer->CurrencyCode),
            out_offer->PriceResult,
            out_offer->DiscountPercentage,
            expiration,
            out_offer->PurchaseLimit,
            out_offer->bAvailableForPurchase,
            out_offer->OriginalPrice64,
            out_offer->CurrentPrice64,
            out_offer->DecimalPoint,
            release_date,
            effective_date
        );
     */

    static native EOSEcom.CatalogOffer copyOfferById(
            long handle, EOSEcom.CopyOfferByIdOptions options
    ) throws EOSException; /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto offer_id = EOS4J::javaStringFromObjectField(env, options, "offerId");

        EOS_Ecom_CopyOfferByIdOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_ECOM_COPYOFFERBYID_API_LATEST;
        copy_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        copy_options.OfferId = static_cast<EOS_Ecom_CatalogOfferId>(offer_id->c_str());

        EOS_Ecom_CatalogOffer* out_offer;
        auto copy_result = EOS_Ecom_CopyOfferById(reinterpret_cast<EOS_HEcom>(handle), &copy_options, &out_offer);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSEcom$CatalogOffer");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/util/Date;IZJJILjava/util/Date;Ljava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject expiration = out_offer->ExpirationTimestamp == EOS_ECOM_CATALOGOFFER_EXPIRATIONTIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->ExpirationTimestamp);
        jobject release_date = out_offer->ReleaseDateTimestamp == EOS_ECOM_CATALOGOFFER_RELEASEDATETIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->ReleaseDateTimestamp);
        jobject effective_date = out_offer->EffectiveDateTimestamp == EOS_ECOM_CATALOGOFFER_EFFECTIVEDATETIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out_offer->EffectiveDateTimestamp);

        return env->NewObject(
            result_cls,
            result_ctor,
            (long long) out_offer,
            out_offer->ServerIndex,
            env->NewStringUTF(out_offer->CatalogNamespace),
            env->NewStringUTF(out_offer->Id),
            env->NewStringUTF(out_offer->TitleText),
            env->NewStringUTF(out_offer->DescriptionText),
            env->NewStringUTF(out_offer->LongDescriptionText),
            env->NewStringUTF(out_offer->CurrencyCode),
            out_offer->PriceResult,
            out_offer->DiscountPercentage,
            expiration,
            out_offer->PurchaseLimit,
            out_offer->bAvailableForPurchase,
            out_offer->OriginalPrice64,
            out_offer->CurrentPrice64,
            out_offer->DecimalPoint,
            release_date,
            effective_date
        );
     */

    static native void releaseCatalogOffer(long handle); /*
        EOS_Ecom_CatalogOffer_Release(reinterpret_cast<EOS_Ecom_CatalogOffer*>(handle));
    */

    static native void queryEntitlements(
            long handle, EOSEcom.QueryEntitlementsOptions options, EOSEcom.OnQueryEntitlementsCallback callback
    ); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        jboolean include_redeemed = EOS4J::javaBooleanFromObjectField(env, options, "includeRedeemed");

        std::vector<EOS4J::JavaString> entitlement_names = EOS4J::javaStringVectorFromObjectField(env, options, "entitlementNames");
        const char* entitlements[entitlement_names.size()];
        for (size_t i = 0; i < entitlement_names.size(); i++) {
            entitlements[i] = entitlement_names.at(i).c_str();
        }

        EOS_Ecom_QueryEntitlementsOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_ECOM_QUERYENTITLEMENTS_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        query_options.EntitlementNames = reinterpret_cast<EOS_Ecom_EntitlementName*>(entitlements);
        query_options.EntitlementNameCount = entitlement_names.size();
        query_options.bIncludeRedeemed = static_cast<bool>(include_redeemed);

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Ecom_QueryEntitlements(reinterpret_cast<EOS_HEcom>(handle), &query_options, callback_adapter.release(), [](const EOS_Ecom_QueryEntitlementsCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass eaid_cls = env->FindClass("com/bearwaves/eos4j/EOS$EpicAccountId");
                jmethodID eaid_ctor = env->GetMethodID(eaid_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(eaid_cls, eaid_ctor, data->LocalUserId);

                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSEcom$QueryEntitlementsCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$EpicAccountId;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id);

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSEcom$QueryEntitlementsCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native int getEntitlementsCount(long handle, EOSEcom.GetEntitlementsCountOptions options); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");

        EOS_Ecom_GetEntitlementsCountOptions count_options;
        memset(&count_options, 0, sizeof(count_options));
        count_options.ApiVersion = EOS_ECOM_GETENTITLEMENTSCOUNT_API_LATEST;
        count_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);

        return EOS_Ecom_GetEntitlementsCount(reinterpret_cast<EOS_HEcom>(handle), &count_options);
    */

    static native int getEntitlementsByNameCount(long handle, EOSEcom.GetEntitlementsByNameCountOptions options); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto name = EOS4J::javaStringFromObjectField(env, options, "entitlementName");

        EOS_Ecom_GetEntitlementsByNameCountOptions count_options;
        memset(&count_options, 0, sizeof(count_options));
        count_options.ApiVersion = EOS_ECOM_GETENTITLEMENTSBYNAMECOUNT_API_LATEST;
        count_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        count_options.EntitlementName = name->c_str();

        return EOS_Ecom_GetEntitlementsByNameCount(reinterpret_cast<EOS_HEcom>(handle), &count_options);
    */

    static native EOSEcom.Entitlement copyEntitlementByIndex(
            long handle, EOSEcom.CopyEntitlementByIndexOptions options
    ) throws EOSException; /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto index = EOS4J::javaIntFromObjectField(env, options, "entitlementIndex");

        EOS_Ecom_CopyEntitlementByIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_ECOM_COPYENTITLEMENTBYINDEX_API_LATEST;
        copy_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        copy_options.EntitlementIndex = static_cast<int>(index);

        EOS_Ecom_Entitlement* out;
        auto copy_result = EOS_Ecom_CopyEntitlementByIndex(reinterpret_cast<EOS_HEcom>(handle), &copy_options, &out);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSEcom$Entitlement");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject end = out->EndTimestamp == EOS_ECOM_ENTITLEMENT_ENDTIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out->EndTimestamp);

        return env->NewObject(
            result_cls,
            result_ctor,
            (long long) out,
            env->NewStringUTF(out->EntitlementName),
            env->NewStringUTF(out->EntitlementId),
            env->NewStringUTF(out->CatalogItemId),
            out->ServerIndex,
            out->bRedeemed,
            end
        );
     */

    static native EOSEcom.Entitlement copyEntitlementById(
            long handle, EOSEcom.CopyEntitlementByIdOptions options
    ) throws EOSException; /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto id = EOS4J::javaStringFromObjectField(env, options, "entitlementId");

        EOS_Ecom_CopyEntitlementByIdOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_ECOM_COPYENTITLEMENTBYID_API_LATEST;
        copy_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        copy_options.EntitlementId = id->c_str();

        EOS_Ecom_Entitlement* out;
        auto copy_result = EOS_Ecom_CopyEntitlementById(reinterpret_cast<EOS_HEcom>(handle), &copy_options, &out);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSEcom$Entitlement");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject end = out->EndTimestamp == EOS_ECOM_ENTITLEMENT_ENDTIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out->EndTimestamp);

        return env->NewObject(
            result_cls,
            result_ctor,
            (long long) out,
            env->NewStringUTF(out->EntitlementName),
            env->NewStringUTF(out->EntitlementId),
            env->NewStringUTF(out->CatalogItemId),
            out->ServerIndex,
            out->bRedeemed,
            end
        );
     */

    static native EOSEcom.Entitlement copyEntitlementByNameAndIndex(
            long handle, EOSEcom.CopyEntitlementByNameAndIndexOptions options
    ) throws EOSException; /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");
        auto name = EOS4J::javaStringFromObjectField(env, options, "entitlementName");
        auto index = EOS4J::javaIntFromObjectField(env, options, "index");

        EOS_Ecom_CopyEntitlementByNameAndIndexOptions copy_options;
        memset(&copy_options, 0, sizeof(copy_options));
        copy_options.ApiVersion = EOS_ECOM_COPYENTITLEMENTBYNAMEANDINDEX_API_LATEST;
        copy_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        copy_options.EntitlementName = name->c_str();
        copy_options.Index = static_cast<int>(index);

        EOS_Ecom_Entitlement* out;
        auto copy_result = EOS_Ecom_CopyEntitlementByNameAndIndex(reinterpret_cast<EOS_HEcom>(handle), &copy_options, &out);
        if (copy_result != EOS_EResult::EOS_Success) {
            EOS4J::throwEOSException(env, static_cast<int>(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSEcom$Entitlement");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/util/Date;)V");

        jclass date_cls = env->FindClass("java/util/Date");
        jmethodID date_ctor = env->GetMethodID(date_cls, "<init>", "(J)V");
        jobject end = out->EndTimestamp == EOS_ECOM_ENTITLEMENT_ENDTIMESTAMP_UNDEFINED ? nullptr : env->NewObject(date_cls, date_ctor, out->EndTimestamp);

        return env->NewObject(
            result_cls,
            result_ctor,
            (long long) out,
            env->NewStringUTF(out->EntitlementName),
            env->NewStringUTF(out->EntitlementId),
            env->NewStringUTF(out->CatalogItemId),
            out->ServerIndex,
            out->bRedeemed,
            end
        );
     */

    static native void releaseEntitlement(long handle); /*
        EOS_Ecom_Entitlement_Release(reinterpret_cast<EOS_Ecom_Entitlement*>(handle));
    */

    static native void queryEntitlementToken(
            long handle,
            EOSEcom.QueryEntitlementTokenOptions options,
            EOSEcom.OnQueryEntitlementTokenCallback callback
    ); /*
        jobject local_user_id_obj = EOS4J::javaObjectFromObjectField(env, options, "localUserId", "Lcom/bearwaves/eos4j/EOS$EpicAccountId;");
        auto local_user_id = EOS4J::javaLongFromObjectField(env, local_user_id_obj, "ptr");

        std::vector<EOS4J::JavaString> entitlement_names = EOS4J::javaStringVectorFromObjectField(env, options, "entitlementNames");
        const char* entitlements[entitlement_names.size()];
        for (size_t i = 0; i < entitlement_names.size(); i++) {
            entitlements[i] = entitlement_names.at(i).c_str();
        }

        EOS_Ecom_QueryEntitlementTokenOptions query_options;
        memset(&query_options, 0, sizeof(query_options));
        query_options.ApiVersion = EOS_ECOM_QUERYENTITLEMENTTOKEN_API_LATEST;
        query_options.LocalUserId = reinterpret_cast<EOS_EpicAccountId>(local_user_id);
        query_options.EntitlementNames = reinterpret_cast<EOS_Ecom_EntitlementName*>(entitlements);
        query_options.EntitlementNameCount = entitlement_names.size();

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Ecom_QueryEntitlementToken(reinterpret_cast<EOS_HEcom>(handle), &query_options, callback_adapter.release(), [](const EOS_Ecom_QueryEntitlementTokenCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass eaid_cls = env->FindClass("com/bearwaves/eos4j/EOS$EpicAccountId");
                jmethodID eaid_ctor = env->GetMethodID(eaid_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(eaid_cls, eaid_ctor, data->LocalUserId);

                jclass callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSEcom$QueryEntitlementTokenCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$EpicAccountId;Ljava/lang/String;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id, env->NewStringUTF(data->EntitlementToken));

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSEcom$QueryEntitlementTokenCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
     */
}

