package com.bearwaves.eos4j;

public class EOSConnect {

    private final long handle;

    EOSConnect(long handle) {
        this.handle = handle;
    }

    public void login(LoginOptions loginOptions, LoginCallback callback) {
        EOSConnectNative.login(handle, loginOptions, callback);
    }

    public IdToken copyIdToken(EOS.ProductUserId localUserId) throws EOSException {
        return EOSConnectNative.copyIdToken(handle, localUserId.ptr);
    }

    public void createUser(EOS.ContinuanceToken continuanceToken, CreateUserCallback callback) {
        EOSConnectNative.createUser(handle, continuanceToken.ptr, callback);
    }

    public static class Credentials {
        public final EOS.ExternalCredentialType type;
        public final String token;

        public Credentials(EOS.ExternalCredentialType type, String token) {
            this.type = type;
            this.token = token;
        }
    }

    public static class UserLoginInfo {
        public final String displayName;

        public UserLoginInfo(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class LoginOptions {
        public final Credentials credentials;
        public final UserLoginInfo userLoginInfo;

        public LoginOptions(Credentials credentials, UserLoginInfo userLoginInfo) {
            this.credentials = credentials;
            this.userLoginInfo = userLoginInfo;
        }
    }

    public static class LoginCallbackInfo {
        public final int resultCode;
        public final EOS.ProductUserId localUserId;
        public final EOS.ContinuanceToken continuanceToken;

        LoginCallbackInfo(int resultCode, EOS.ProductUserId localUserId, EOS.ContinuanceToken continuanceToken) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.continuanceToken = continuanceToken;
        }
    }

    public static class CreateUserCallbackInfo {
        public final int resultCode;
        public final EOS.ProductUserId localUserId;

        CreateUserCallbackInfo(int resultCode, EOS.ProductUserId localUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
        }
    }

    public static class IdToken extends EOSHandle {
        IdToken(long ptr) {
            super(ptr);
        }

        public String getJsonWebToken() {
            if (ptr == 0) {
                return "";
            }
            return EOSConnectNative.getIdTokenJwt(ptr);
        }

        @Override
        public String toString() {
            return getJsonWebToken();
        }

        public void release() {
            EOSConnectNative.releaseIdToken(ptr);
        }
    }

    public interface LoginCallback {
        void run(LoginCallbackInfo data);
    }

    public interface CreateUserCallback {
        void run(CreateUserCallbackInfo data);
    }

}
