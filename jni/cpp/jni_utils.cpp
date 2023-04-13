#include "jni_utils.h"

using EOS4J::JavaString;

JavaString::JavaString(JNIEnv* env, jstring&& str)
	: env{env}
	, str{std::move(str)} {
	m_c_str = env->GetStringUTFChars(str, nullptr);
}

JavaString::~JavaString() {
	env->ReleaseStringUTFChars(str, m_c_str);
}

std::unique_ptr<JavaString>
EOS4J::javaStringFromObjectField(JNIEnv* env, jobject obj, const char* field) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "Ljava/lang/String;");
	jstring str = (jstring)env->GetObjectField(obj, fid);
	return std::make_unique<JavaString>(env, std::move(str));
}