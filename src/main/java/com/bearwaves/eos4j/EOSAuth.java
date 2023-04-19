package com.bearwaves.eos4j;

public class EOSAuth {

    private final long handle;

    EOSAuth(long handle) {
        this.handle = handle;
    }

    public void login(LoginOptions options, LoginCallback callback) {
        EOSAuthNative.login(handle, options, callback);
    }

    public enum CredentialsType {
        PASSWORD, EXCHANGE_CODE, PERSISTENT_AUTH, DEVICE_CODE, DEVELOPER, REFRESH_TOKEN, ACCOUNT_PORTAL, EXTERNAL_AUTH
    }

    public static class Credentials {
        public final CredentialsType type;
        public final String id;
        public final String token;

        public Credentials(CredentialsType type, String id, String token) {
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
        public final EOSHandle localUserId;
        public final EOSHandle selectedAccountId;

        LoginCallbackInfo(int resultCode, EOSHandle localUserId, EOSHandle selectedAccountId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.selectedAccountId = selectedAccountId;
        }
    }

    public interface LoginCallback {
        void run(LoginCallbackInfo data);
    }

}
