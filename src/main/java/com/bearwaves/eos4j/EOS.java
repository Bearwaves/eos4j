package com.bearwaves.eos4j;

import org.lwjgl.system.Library;
import org.lwjgl.system.Platform;

public class EOS {

    public static boolean loadLibraries() {
        try {
            switch (Platform.get()) {
                case LINUX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Linux-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
                case MACOSX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Mac-Shipping");
                    switch (Platform.getArchitecture()) {
                        case X64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                            break;
                        case ARM64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4jarm64");
                            break;
                    }
                    break;
                case WINDOWS:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Win64-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
            }
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    public static void initialize(InitializeOptions options) throws EOSException {
        throwIfErrorCode(EOSNative.initialize(options));
    }

    public static void shutdown() throws EOSException {
        throwIfErrorCode(EOSNative.shutdown());
    }

    public static void throwIfErrorCode(int resultCode) throws EOSException {
        if (resultCode != 0) {
            throw new EOSException(resultCode);
        }
    }

    public static class InitializeOptions {
        public final String productName;
        public final String productVersion;

        public InitializeOptions(String productName, String productVersion) {
            this.productName = productName;
            this.productVersion = productVersion;
        }
    }

    public static class EpicAccountId extends EOSHandle {
        EpicAccountId(long ptr) {
            super(ptr);
        }
    }

    public static class ProductUserId extends EOSHandle {
        ProductUserId(long ptr) {
            super(ptr);
        }
    }

    public static class ContinuanceToken extends EOSHandle {
        ContinuanceToken(long ptr) {
            super(ptr);
        }
    }

    public enum LoginCredentialType {
        PASSWORD, EXCHANGE_CODE, PERSISTENT_AUTH, DEVICE_CODE, DEVELOPER, REFRESH_TOKEN, ACCOUNT_PORTAL, EXTERNAL_AUTH
    }

    public enum ExternalCredentialType {
        EPIC,
        STEAM_APP_TICKET,
        PSN_ID_TOKEN,
        XBL_XSTS_TOKEN,
        DISCORD_ACCESS_TOKEN,
        GOG_SESSION_TICKET,
        NINTENDO_ID_TOKEN,
        NINTENDO_NSA_ID_TOKEN,
        UPLAY_ACCESS_TOKEN,
        OPENID_ACCESS_TOKEN,
        DEVICEID_ACCESS_TOKEN,
        APPLE_ID_TOKEN,
        GOOGLE_ID_TOKEN,
        OCULUS_USERID_NONCE,
        ITCHIO_JWT,
        ITCHIO_KEY,
        EPIC_ID_TOKEN,
        AMAZON_ACCESS_TOKEN,
        STEAM_SESSION_TICKET
    }
}

