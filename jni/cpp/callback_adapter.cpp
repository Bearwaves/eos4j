#include "callback_adapter.h"

using EOS4J::CallbackAdapter;
using EOS4J::InvokeCallbackFunction;

CallbackAdapter::CallbackAdapter(JNIEnv* env, jobject callback) {
	env->GetJavaVM(&vm);
	this->callback = env->NewGlobalRef(callback);
}

CallbackAdapter::~CallbackAdapter() {
	if (callback != nullptr) {
		JNIEnv* env;
		bool attached = attachThread(&env);
		if (env && !env->IsSameObject(callback, nullptr)) {
			env->DeleteGlobalRef(callback);
		}
		if (attached) {
			detachThread();
		}
	}
}

void CallbackAdapter::attach(InvokeCallbackFunction fn) const {
	JNIEnv* env;
	bool attached = attachThread(&env);
	fn(env, callback);
	if (attached) {
		detachThread();
	}
}

bool CallbackAdapter::attachThread(JNIEnv** env) const {
	jint status = vm->GetEnv((void**)env, JNI_VERSION_1_6);
	if (status == JNI_EDETACHED) {
		vm->AttachCurrentThread((void**)env, nullptr);
		return true;
	}
	return false;
}

void CallbackAdapter::detachThread() const {
	vm->DetachCurrentThread();
}