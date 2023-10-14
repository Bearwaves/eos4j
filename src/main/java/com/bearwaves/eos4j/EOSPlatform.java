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

    public EOSStats getStatsHandle() throws EOSException {
        long statsHandle = EOSPlatformNative.getStatsHandle(handle);
        if (statsHandle == 0) {
            throw new EOSException("Failed to get stats handle");
        }
        return new EOSStats(statsHandle);
    }

    public EOSAchievements getAchievementsHandle() throws EOSException {
        long achievementsHandle = EOSPlatformNative.getAchievementsHandle(handle);
        if (achievementsHandle == 0) {
            throw new EOSException("Failed to get achievements handle");
        }
        return new EOSAchievements(achievementsHandle);
    }

    public EOSLeaderboards getLeaderboardsHandle() throws EOSException {
        long leaderboardsHandle = EOSPlatformNative.getLeaderboardsHandle(handle);
        if (leaderboardsHandle == 0) {
            throw new EOSException("Failed to get leaderboards handle");
        }
        return new EOSLeaderboards(leaderboardsHandle);
    }

    public static class Options {
        public final String productId;
        public final String sandboxId;
        public final String deploymentId;
        public final String clientId;
        public final String clientSecret;
        public final int flags;

        public Options(String productId, String sandboxId, String deploymentId, String clientId, String clientSecret, int flags) {
            this.productId = productId;
            this.sandboxId = sandboxId;
            this.deploymentId = deploymentId;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.flags = flags;
        }
    }

    public enum Flag {
        LOADING_IN_EDITOR(0x00001),
        DISABLE_OVERLAY(0x00002),
        DISABLE_SOCIAL_OVERLAY(0x00004),
        RESERVED1(0x00008),
        WINDOWS_ENABLE_OVERLAY_D3D9(0x00010),
        WINDOWS_ENABLE_OVERLAY_D3D10(0x00020),
        WINDOWS_ENABLE_OVERLAY_OPENGL(0x00040);

        private final int value;

        Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
