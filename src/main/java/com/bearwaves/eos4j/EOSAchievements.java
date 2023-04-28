package com.bearwaves.eos4j;

public class EOSAchievements {

    private final long handle;

    EOSAchievements(long handle) {
        this.handle = handle;
    }

    public void unlockAchievements(UnlockAchievementsOptions options, OnUnlockAchievementsCompleteCallback callback) {
        EOSAchievementsNative.unlockAchievements(handle, options, callback);
    }

    public static class UnlockAchievementsOptions {
        public final EOS.ProductUserId userId;
        public final String[] achievementIds;

        public UnlockAchievementsOptions(EOS.ProductUserId userId, String[] achievementIds) {
            this.userId = userId;
            this.achievementIds = achievementIds;
        }
    }

    public static class OnUnlockAchievementsCompleteCallbackInfo {
        public final int resultCode;
        public final EOS.ProductUserId userId;
        public final int achievementsCount;

        OnUnlockAchievementsCompleteCallbackInfo(int resultCode, EOS.ProductUserId userId, int achievementsCount) {
            this.resultCode = resultCode;
            this.userId = userId;
            this.achievementsCount = achievementsCount;
        }
    }

    public interface OnUnlockAchievementsCompleteCallback {
        void run(OnUnlockAchievementsCompleteCallbackInfo data);
    }
}
