package com.bearwaves.eos4j;

/*JNI
#include <eos_auth.h>
#include <cstring>
#include "jni_utils.h"
#include "callback_adapter.h"
*/

class EOSAuthNative {
    static native void login(long handle, EOSAuth.LoginOptions options, EOSAuth.LoginCallback callback); /*
        auto creds_obj = EOS4J::javaObjectFromObjectField(env, options, "credentials", "Lcom/bearwaves/eos4j/EOSAuth$Credentials;");
        auto credType = EOS4J::javaEnumValueFromObjectField(env, creds_obj, "type", "Lcom/bearwaves/eos4j/EOS$LoginCredentialType;");
        auto id = EOS4J::javaStringFromObjectField(env, creds_obj, "id");
        auto token = EOS4J::javaStringFromObjectField(env, creds_obj, "token");
        auto scope_flags = EOS4J::javaIntFromObjectField(env, options, "scopeFlags");

        EOS_Auth_Credentials credentials;
        memset(&credentials, 0, sizeof(credentials));
        credentials.ApiVersion = EOS_AUTH_CREDENTIALS_API_LATEST;
        credentials.Type = static_cast<EOS_ELoginCredentialType>(credType);
        if (id) {
            credentials.Id = id->c_str();
        }
        credentials.Token = token->c_str();

        EOS_Auth_LoginOptions login_options;
        login_options.ApiVersion = EOS_AUTH_LOGIN_API_LATEST;
        login_options.ScopeFlags = static_cast<EOS_EAuthScopeFlags>(scope_flags);
        login_options.Credentials = &credentials;

        auto callback_adapter = std::make_unique<EOS4J::CallbackAdapter>(env, callback);
        EOS_Auth_Login(reinterpret_cast<EOS_HAuth>(handle), &login_options, callback_adapter.release(), [](const EOS_Auth_LoginCallbackInfo* data) -> void {
            std::unique_ptr<EOS4J::CallbackAdapter> callback_adapter{reinterpret_cast<EOS4J::CallbackAdapter*>(data->ClientData)};
            callback_adapter->attach([&](JNIEnv* env, jobject callback) -> void {
                jclass cls = env->GetObjectClass(callback);
                jclass ptr_class = env->FindClass("com/bearwaves/eos4j/EOS$EpicAccountId");

                jmethodID ptr_ctor = env->GetMethodID(ptr_class, "<init>", "(J)V");
                auto callback_info_class = env->FindClass("com/bearwaves/eos4j/EOSAuth$LoginCallbackInfo");
                jmethodID callback_info_ctor = env->GetMethodID(callback_info_class, "<init>", "(ILcom/bearwaves/eos4j/EOS$EpicAccountId;Lcom/bearwaves/eos4j/EOS$EpicAccountId;)V");
                auto local_user_id = env->NewObject(ptr_class, ptr_ctor, data->LocalUserId);
                auto selected_account_id = env->NewObject(ptr_class, ptr_ctor, data->SelectedAccountId);
                auto callback_info = env->NewObject(callback_info_class, callback_info_ctor, static_cast<int>(data->ResultCode), local_user_id, selected_account_id);

                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSAuth$LoginCallbackInfo;)V");
                env->CallVoidMethod(callback, mid, callback_info);
            });
        });
    */

    static native EOSAuth.IdToken copyIdToken(long handle, long epicAccountIdPtr) throws EOSException; /*
        EOS_Auth_IdToken* token;
        EOS_EpicAccountId account_id = reinterpret_cast<EOS_EpicAccountId>(epicAccountIdPtr);

        EOS_Auth_CopyIdTokenOptions copy_options;
        copy_options.ApiVersion = EOS_AUTH_COPYIDTOKEN_API_LATEST;
        copy_options.AccountId = account_id;
        auto copy_result = EOS_Auth_CopyIdToken(reinterpret_cast<EOS_HAuth>(handle), &copy_options, &token);
        if (copy_result != EOS_EResult::EOS_Success) {
            jclass ex_cls = env->FindClass("com/bearwaves/eos4j/EOSException");
            env->ThrowNew(ex_cls, EOS_EResult_ToString(copy_result));
            return nullptr;
        }

        jclass result_cls = env->FindClass("com/bearwaves/eos4j/EOSAuth$IdToken");
        jmethodID result_ctor = env->GetMethodID(result_cls, "<init>", "(J)V");
        return env->NewObject(result_cls, result_ctor, (long long) token);
    */

    static native String getIdTokenJwt(long idTokenPtr); /*
        EOS_Auth_IdToken* token = reinterpret_cast<EOS_Auth_IdToken*>(idTokenPtr);
        return env->NewStringUTF(token->JsonWebToken);
    */

    static native void releaseIdToken(long idTokenPtr); /*
        EOS_Auth_IdToken* token = reinterpret_cast<EOS_Auth_IdToken*>(idTokenPtr);
        EOS_Auth_IdToken_Release(token);
    */
}
