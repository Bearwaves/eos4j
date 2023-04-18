package com.bearwaves.eos4j;

/*JNI
#include <eos_logging.h>
#include "jni_utils.h"
#include "callback_adapter.h"

using EOS4J::CallbackAdapter;

std::unique_ptr<CallbackAdapter> logging_callback;
*/

class EOSLoggingNative {

    static native int setLogLevel(EOSLogging.LogCategory category, EOSLogging.LogLevel level); /*
        auto log_category = EOS4J::javaEnumValueFromObject(env, category);
        auto log_level = EOS4J::javaEnumValueFromObject(env, level);
        return static_cast<int>(
            EOS_Logging_SetLogLevel(static_cast<EOS_ELogCategory>(log_category), static_cast<EOS_ELogLevel>(log_level))
        );
    */

    static native int setCallback(EOSLogging.LoggingCallback callback); /*
        logging_callback = std::make_unique<CallbackAdapter>(env, callback);
        return static_cast<int>(EOS_Logging_SetCallback([](const EOS_LogMessage* message) {
            logging_callback->attach([&](JNIEnv* env, jobject callback) -> void {
                auto log_message_cls = env->FindClass("com/bearwaves/eos4j/EOSLogging$LogMessage");
                jmethodID log_message_ctor = env->GetMethodID(
                    log_message_cls,
                    "<init>",
                    "(Lcom/bearwaves/eos4j/EOSLogging$LogLevel;Ljava/lang/String;Ljava/lang/String;)V"
                );
                auto log_level_cls = env->FindClass("com/bearwaves/eos4j/EOSLogging$LogLevel");
                jmethodID log_level_func = env->GetStaticMethodID(log_level_cls, "fromInt", "(I)Lcom/bearwaves/eos4j/EOSLogging$LogLevel;");
                jobject log_level = env->CallStaticObjectMethod(log_level_cls, log_level_func, message->Level);
                auto log_message = env->NewObject(
                    log_message_cls, log_message_ctor, log_level,
                    env->NewStringUTF(message->Category),  env->NewStringUTF(message->Message)
                );

                jclass cls = env->GetObjectClass(callback);
                jmethodID mid = env->GetMethodID(cls, "run", "(Lcom/bearwaves/eos4j/EOSLogging$LogMessage;)V");
                env->CallVoidMethod(callback, mid, log_message);
            });
        }));
     */

}
