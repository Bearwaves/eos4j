#include "jni_utils.h"
#include <iostream>

using EOS4J::JavaString;

JavaString::JavaString(JNIEnv* env, jstring str)
	: env{env}
	, str{str} {
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

jobject EOS4J::javaObjectFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, sig);
	return env->GetObjectField(obj, fid);
}

jint EOS4J::javaEnumValueFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, sig);
	jobject enum_obj = env->GetObjectField(obj, fid);
	jclass enum_cls = env->GetObjectClass(enum_obj);
	jmethodID ord = env->GetMethodID(enum_cls, "ordinal", "()I");
	return env->CallIntMethod(enum_obj, ord);
}

jint EOS4J::javaIntFromObjectField(
	JNIEnv* env, jobject obj, const char* field) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "I");
	return env->GetIntField(obj, fid);
}