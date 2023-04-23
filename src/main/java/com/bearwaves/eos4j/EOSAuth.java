package com.bearwaves.eos4j;

public class EOSAuth {

    private final long handle;

    EOSAuth(long handle) {
        this.handle = handle;
    }

    public void login(LoginOptions options, LoginCallback callback) {
        EOSAuthNative.login(handle, options, callback);
    }

    public IdToken copyIdToken(EOS.EpicAccountId accountId) throws EOSException {
        return EOSAuthNative.copyIdToken(handle, accountId.ptr);
    }

    public static class Credentials {
        public final EOS.LoginCredentialType type;
        public final String id;
        public final String token;

        public Credentials(EOS.LoginCredentialType type, String id, String token) {
            this.type = type;
            this.id = id;
            this.token = token;
        }
    }

    public static class LoginOptions {
        public final Credentials credentials;
        public final int scopeFlags;

        public LoginOptions(Credentials credentials, int scopeFlags) {
            this.credentials = credentials;
            this.scopeFlags = scopeFlags;
        }
    }

    public static class LoginCallbackInfo {
        public final int resultCode;
        public final EOS.EpicAccountId localUserId;
        public final EOS.EpicAccountId selectedAccountId;

        LoginCallbackInfo(int resultCode, EOS.EpicAccountId localUserId, EOS.EpicAccountId selectedAccountId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.selectedAccountId = selectedAccountId;
        }
    }

    public interface LoginCallback {
        void run(LoginCallbackInfo data);
    }

    public static class IdToken extends EOSHandle {
        IdToken(long ptr) {
            super(ptr);
        }

        public String getJsonWebToken() {
            if (ptr == 0) {
                return "";
            }
            return EOSAuthNative.getIdTokenJwt(ptr);
        }

        @Override
        public String toString() {
            return getJsonWebToken();
        }

        public void release() {
            EOSAuthNative.releaseIdToken(ptr);
        }
    }

}
