package com.bearwaves.eos4j;

public class EOSPlatform {

    private final long handle;

    public EOSPlatform(Options options) throws EOSException {
        this.handle = EOSPlatformNative.create(options);
        if (this.handle == 0) {
            throw new EOSException("Failed to create platform handle");
        }
    }

    public void release() {
        EOSPlatformNative.release(handle);
    }

    public void tick() {
        EOSPlatformNative.tick(handle);
    }

    public EOSAuth getAuthHandle() throws EOSException {
        long authHandle = EOSPlatformNative.getAuthHandle(handle);
        if (authHandle == 0) {
            throw new EOSException("Failed to get auth handle");
        }
        return new EOSAuth(authHandle);
    }

    public EOSConnect getConnectHandle() throws EOSException {
        long connectHandle = EOSPlatformNative.getConnectHandle(handle);
        if (connectHandle == 0) {
            throw new EOSException("Failed to get connect handle");
        }
        return new EOSConnect(connectHandle);
    }

    public EOSAchievements getAchievementsHandle() throws EOSException {
        long achievementsHandle = EOSPlatformNative.getAchievementsHandle(handle);
        if (achievementsHandle == 0) {
            throw new EOSException("Failed to get achievements handle");
        }
        return new EOSAchievements(achievementsHandle);
    }

    public static class Options {
        public final String productId;
        public final String sandboxId;
        public final String deploymentId;
        public final String clientId;
        public final String clientSecret;

        public Options(String productId, String sandboxId, String deploymentId, String clientId, String clientSecret) {
            this.productId = productId;
            this.sandboxId = sandboxId;
            this.deploymentId = deploymentId;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }
    }
}
