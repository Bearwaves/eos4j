#pragma once

#include <jni.h>
#include <memory>
#include <utility>
#include <vector>

namespace EOS4J {

class JavaString {
public:
	JavaString(JNIEnv* env, jstring str);
	~JavaString();
	// Move-only
	JavaString(const JavaString&) = delete;
	JavaString& operator=(const JavaString&) = delete;
	JavaString(JavaString&&);
	JavaString& operator=(JavaString&&);

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

std::vector<JavaString>
javaStringVectorFromObjectField(JNIEnv* env, jobject obj, const char* field);

jobject javaObjectFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig);

jint javaEnumValueFromObjectField(
	JNIEnv* env, jobject obj, const char* field, const char* sig);

jint javaIntFromObjectField(JNIEnv* env, jobject obj, const char* field);

jlong javaLongFromObjectField(JNIEnv* env, jobject obj, const char* field);

jint javaEnumValueFromObject(JNIEnv* env, jobject obj);

jboolean
javaBooleanFromObjectField(JNIEnv* env, jobject obj, const char* field);

void throwEOSException(JNIEnv* env, int result_code);

} // namespace EOS4J