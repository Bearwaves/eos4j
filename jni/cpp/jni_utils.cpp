#include "jni_utils.h"

using EOS4J::JavaString;

JavaString::JavaString(JNIEnv* env, jstring&& str): env{env}, str{std::move(str)} {
    m_c_str = env->GetStringUTFChars(str, nullptr);
}

JavaString::~JavaString() {
    env->ReleaseStringUTFChars(str, m_c_str);
}