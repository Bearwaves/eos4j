#pragma once

#include <jni.h>
#include <memory>
#include <utility>

namespace EOS4J {

class JavaString {
public:
	JavaString(JNIEnv* env, jstring&& str);
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

} // namespace EOS4J