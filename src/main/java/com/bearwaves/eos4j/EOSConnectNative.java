package com.bearwaves.eos4j;

/*JNI
#include <eos_connect.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

class EOSConnectNative {
    static native void login(long handle, EOSConnect.LoginOptions options, EOSConnect.LoginCallback callback); /*
        auto creds_obj = EOS4J::javaObjectFromObjectField(env, options, "credentials", "Lcom/bearwaves/eos4j/EOSConnect$Credentials;");
        auto credType = EOS4J::javaEnumValueFromObjectField(env, creds_obj, "type", "Lcom/bearwaves/eos4j/EOS$ExternalCredentialType;");
        auto token = EOS4J::javaStringFromObjectField(env, creds_obj, "token");

        EOS_Connect_Credentials credentials;
        memset(&credentials, 0, sizeof(credentials));
        credentials.ApiVersion = EOS_CONNECT_CREDENTIALS_API_LATEST;
        credentials.Type = static_cast<EOS_EExternalCredentialType>(credType);
        credentials.Token = token->c_str();

        EOS_Connect_LoginOptions login_options;
        login_options.ApiVersion = EOS_CONNECT_LOGIN_API_LATEST;
        login_options.Credentials = &credentials;
        login_options.UserLoginInfo = nullptr;

        EOS_Connect_UserLoginInfo user_info;
        auto user_obj = EOS4J::javaObjectFromObjectField(env, options, "userLoginInfo", "Lcom/bearwaves/eos4j/EOSConnect$UserLoginInfo;");
        if (user_obj != nullptr) {
            user_info.ApiVersion = EOS_CONNECT_USERLOGININFO_API_LATEST;
            auto display_name = EOS4J::javaStringFromObjectField(env, user_obj, "displayName");
            user_info.DisplayName = display_name->c_str();
            login_options.UserLoginInfo = &user_info;
        }

        auto callback_adapter = new EOS4J::CallbackAdapter(env, callback);
        EOS_Connect_Login(reinterpret_cast<EOS_HConnect>(handle), &login_options, callback_adapter, [](const EOS_Connect_LoginCallbackInfo* data) -> void {
            EOS4J::CallbackAdapter* callback_adapter = reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData);
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass cls = env->GetObjectClass(callback);

                jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
                jclass cont_cls = env->FindClass("com/bearwaves/eos4j/EOS$ContinuanceToken");
                jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
                jmethodID cont_ctor = env->GetMethodID(cont_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(puid_cls, puid_ctor, data->LocalUserId);
                auto continuance_token = env->NewObject(cont_cls, cont_ctor, data->ContinuanceToken);

                auto callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSConnect$LoginCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$ProductUserId;Lcom/bearwaves/eos4j/EOS$ContinuanceToken;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id, continuance_token);

                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSConnect$LoginCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
            delete callback_adapter;
        });
    */

    static native EOSConnect.IdToken copyIdToken(long handle, long localUserId) throws EOSException; /*
        EOS_Connect_IdToken* token;
        EOS_ProductUserId local_user_id = reinterpret_cast<EOS_ProductUserId>(localUserId);

        EOS_Connect_CopyIdTokenOptions copy_options;
        copy_options.ApiVersion = EOS_CONNECT_COPYIDTOKEN_API_LATEST;
        copy_options.LocalUserId = local_user_id;
        auto copy_result = EOS_Connect_CopyIdToken(reinterpret_cast<EOS_HConnect>(handle), &copy_options, &token);
        if (copy_result != EOS_EResult::EOS_Success) {
            jclass ex_cls = env->FindClass("com/bearwaves/eos4j/EOSException");
            env->ThrowNew(ex_cls, EOS_EResult_ToString(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSConnect$IdToken");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(J)V");
        return env->NewObject(result_cls, result_ctor, (long long) token);
    */

    static native void createUser(long handle, long continuanceToken, EOSConnect.CreateUserCallback callback); /*
        EOS_Connect_CreateUserOptions create_user_options;
        memset(&create_user_options, 0, sizeof(create_user_options));
        create_user_options.ApiVersion = EOS_CONNECT_CREATEUSER_API_LATEST;
        create_user_options.ContinuanceToken = reinterpret_cast<EOS_ContinuanceToken>(continuanceToken);

        auto callback_adapter = new EOS4J::CallbackAdapter(env, callback);
        EOS_Connect_CreateUser(reinterpret_cast<EOS_HConnect>(handle), &create_user_options, callback_adapter, [](const EOS_Connect_CreateUserCallbackInfo* data) -> void {
            EOS4J::CallbackAdapter* callback_adapter = reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData);
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass puid_cls = env->FindClass("com/bearwaves/eos4j/EOS$ProductUserId");
                jmethodID puid_ctor = env->GetMethodID(puid_cls, "<init>", "(J)V");
                auto local_user_id = env->NewObject(puid_cls, puid_ctor, data->LocalUserId);

                jclass cls = env->GetObjectClass(callback);
                auto callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSConnect$CreateUserCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$ProductUserId;)V");
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id);

                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSConnect$CreateUserCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
            delete callback_adapter;
        });
    */

    static native String getIdTokenJwt(long idTokenPtr); /*
        EOS_Connect_IdToken* token = reinterpret_cast<EOS_Connect_IdToken*>(idTokenPtr);
        return env->NewStringUTF(token->JsonWebToken);
    */

    static native void releaseIdToken(long idTokenPtr); /*
        EOS_Connect_IdToken* token = reinterpret_cast<EOS_Connect_IdToken*>(idTokenPtr);
        EOS_Connect_IdToken_Release(token);
    */
}
