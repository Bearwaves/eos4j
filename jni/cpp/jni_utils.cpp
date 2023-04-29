#include "jni_utils.h"
#include <iostream>

using EOS4J::JavaString;

JavaString::JavaString(JNIEnv* env, jstring str)
	: env{env}
	, str{str} {
	m_c_str = env->GetStringUTFChars(str, nullptr);
}

JavaString::~JavaString() {
	if (m_c_str) {
		env->ReleaseStringUTFChars(str, m_c_str);
		m_c_str = nullptr;
	}
}

JavaString::JavaString(JavaString&& other) {
	this->env = other.env;
	this->str = other.str;
	this->m_c_str = other.m_c_str;
	other.m_c_str = nullptr;
}

JavaString& JavaString::operator=(JavaString&& other) {
	this->env = other.env;
	this->str = other.str;
	this->m_c_str = other.m_c_str;
	other.m_c_str = nullptr;
	return *this;
}

std::unique_ptr<JavaString>
EOS4J::javaStringFromObjectField(JNIEnv* env, jobject obj, const char* field) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "Ljava/lang/String;");
	jstring str = (jstring)env->GetObjectField(obj, fid);
	return std::make_unique<JavaString>(env, std::move(str));
}

std::vector<JavaString> EOS4J::javaStringVectorFromObjectField(
	JNIEnv* env, jobject obj, const char* field) {
	std::vector<JavaString> strings;
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "[Ljava/lang/String;");
	jobjectArray array = (jobjectArray)env->GetObjectField(obj, fid);
	if (array == nullptr) {
		return strings;
	}
	int string_count = env->GetArrayLength(array);

	for (int i = 0; i < string_count; i++) {
		jstring string = (jstring)env->GetObjectArrayElement(array, i);
		strings.push_back(JavaString{env, string});
	}
	return strings;
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
	return EOS4J::javaEnumValueFromObject(env, enum_obj);
}

jint EOS4J::javaIntFromObjectField(
	JNIEnv* env, jobject obj, const char* field) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "I");
	return env->GetIntField(obj, fid);
}

jlong EOS4J::javaLongFromObjectField(
	JNIEnv* env, jobject obj, const char* field) {
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, field, "J");
	return env->GetLongField(obj, fid);
}

jint EOS4J::javaEnumValueFromObject(JNIEnv* env, jobject obj) {
	jclass enum_cls = env->GetObjectClass(obj);
	jmethodID ord = env->GetMethodID(enum_cls, "ordinal", "()I");
	return env->CallIntMethod(obj, ord);
}