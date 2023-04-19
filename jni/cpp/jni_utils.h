#pragma once

#include <jni.h>
#include <memory>
#include <utility>

namespace EOS4J {

class JavaString {
public:
	JavaString(JNIEnv* env, jstring str);
	~JavaString();

	const char* c_str() {
		return m_c_str;
	}

private:
	JNIEnv* env;
	jstring str;
	const char* m_c_str;
};

std::unique_ptr<JavaString>
javaStringFromObjectField(JNIEnv* env, jobject obj, const char* field);

jobject javaObjectFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig);

jint javaEnumValueFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig);

jint javaIntFromObjectField(JNIEnv* env, jobject obj, const char* field);

jint javaEnumValueFromObject(JNIEnv* env, jobject obj);

} // namespace EOS4J