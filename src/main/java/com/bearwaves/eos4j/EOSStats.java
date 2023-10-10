package com.bearwaves.eos4j;

import java.util.Date;

public class EOSStats {

    private final long handle;

    EOSStats(long handle) {
        this.handle = handle;
    }

    public void ingestStat(IngestStatOptions options, OnIngestStatCompleteCallback callback) {
        EOSStatsNative.ingestStat(handle, options, callback);
    }

    public void queryStats(QueryStatsOptions options, OnQueryStatsCompleteCallback callback) {
        EOSStatsNative.queryStats(handle, options, callback);
    }

    public int getStatsCount(EOS.ProductUserId targetUserId) {
        return EOSStatsNative.getStatsCount(handle, targetUserId.ptr);
    }

    public Stat copyStatByName(CopyStatByNameOptions options) throws EOSException {
        return EOSStatsNative.copyStatByName(handle, options);
    }

    public Stat copyStatByIndex(CopyStatByIndexOptions options) throws EOSException {
        return EOSStatsNative.copyStatByIndex(handle, options);
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

    public static class QueryStatsOptions {
        public final EOS.ProductUserId localUserId;
        public final Date startTime;
        public final Date endTime;
        public final String[] statNames;
        public final EOS.ProductUserId targetUserId;

        public QueryStatsOptions(EOS.ProductUserId localUserId, Date startTime, Date endTime, String[] statNames, EOS.ProductUserId targetUserId) {
            this.localUserId = localUserId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.statNames = statNames;
            this.targetUserId = targetUserId;
        }
    }

    public static class Stat extends EOSHandle {
        public final String name;
        public final Date startTime;
        public final Date endTime;
        public final int value;

        Stat(long handle, String name, Date startTime, Date endTime, int value) {
            super(handle);
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.value = value;
        }

        public void release() {
            EOSStatsNative.releaseStat(ptr);
        }
    }

    public static class CopyStatByNameOptions {
        public final EOS.ProductUserId targetUserId;
        public final String name;

        public CopyStatByNameOptions(EOS.ProductUserId targetUserId, String name) {
            this.targetUserId = targetUserId;
            this.name = name;
        }
    }

    public static class CopyStatByIndexOptions {
        public final EOS.ProductUserId targetUserId;
        public final int statIndex;

        public CopyStatByIndexOptions(EOS.ProductUserId targetUserId, int statIndex) {
            this.targetUserId = targetUserId;
            this.statIndex = statIndex;
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

    public static class OnQueryStatsCompleteCallbackInfo {
        public final int resultCode;
        public final EOS.ProductUserId localUserId;
        public final EOS.ProductUserId targetUserId;

        OnQueryStatsCompleteCallbackInfo(int resultCode, EOS.ProductUserId localUserId, EOS.ProductUserId targetUserId) {
            this.resultCode = resultCode;
            this.localUserId = localUserId;
            this.targetUserId = targetUserId;
        }
    }

    public interface OnIngestStatCompleteCallback {
        void run(IngestStatCompleteCallbackInfo data);
    }

    public interface OnQueryStatsCompleteCallback {
        void run(OnQueryStatsCompleteCallbackInfo data);
    }

}
