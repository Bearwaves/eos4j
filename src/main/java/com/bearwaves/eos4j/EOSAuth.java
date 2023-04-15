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
        public final EOSHandle localUserId;
        public final EOSHandle selectedAccountId;

        LoginCallbackInfo(EOSHandle localUserId, EOSHandle selectedAccountId) {
            this.localUserId = localUserId;
            this.selectedAccountId = selectedAccountId;
        }
    }

    public static abstract class LoginCallback {
        abstract void run(LoginCallbackInfo data);
    }

}
