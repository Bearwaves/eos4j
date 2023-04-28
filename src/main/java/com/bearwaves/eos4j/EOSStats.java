package com.bearwaves.eos4j;

public class EOSStats {

    private final long handle;

    EOSStats(long handle) {
        this.handle = handle;
    }

    public void ingestStat(IngestStatOptions options, OnIngestStatCompleteCallback callback) {
        EOSStatsNative.ingestStat(handle, options, callback);
    }

    public static class IngestStatOptions {
        public final EOS.ProductUserId localUserId;
        public final EOS.ProductUserId targetUserId;
        public final IngestData[] stats;

        public IngestStatOptions(EOS.ProductUserId localUserId, EOS.ProductUserId targetUserId, IngestData[] stats) {
            this.localUserId = localUserId;
            this.targetUserId = targetUserId;
            this.stats = stats;
        }
    }

    public static class IngestData {
        public final String statName;
        public final int ingestAmount;

        public IngestData(String statName, int ingestAmount) {
            this.statName = statName;
            this.ingestAmount = ingestAmount;
        }
    }

    public static class IngestStatCompleteCallbackInfo {
        public final int resultCode;
        public final EOS.ProductUserId localUserId;
        public final EOS.ProductUserId targetUserId;

        IngestStatCompleteCallbackInfo(int resultCode, EOS.ProductUserId localUserId, EOS.ProductUserId targetUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.targetUserId = targetUserId;
        }

    }

    public interface OnIngestStatCompleteCallback {
        void run(IngestStatCompleteCallbackInfo data);
    }

}
