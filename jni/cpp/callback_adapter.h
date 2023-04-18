#pragma once

#include <functional>
#include <jni.h>

namespace EOS4J {

typedef std::function<void(JNIEnv* env, jobject callback)>
	InvokeCallbackFunction;

class CallbackAdapter {
public:
	CallbackAdapter(JNIEnv* env, jobject callback);
	virtual ~CallbackAdapter();
	void attach(InvokeCallbackFunction fn) const;

private:
	bool attachThread(JNIEnv** env) const;
	void detachThread() const;
	JavaVM* vm;
	jobject callback;
};

} // namespace EOS4J