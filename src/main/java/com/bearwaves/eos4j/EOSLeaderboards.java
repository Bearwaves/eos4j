package com.bearwaves.eos4j;

import java.util.Date;

public class EOSLeaderboards {

    private final long handle;

    EOSLeaderboards(long handle) {
        this.handle = handle;
    }

    public void queryLeaderboardDefinitions(QueryLeaderboardDefinitionsOptions options, OnQueryLeaderboardDefinitionsCompleteCallback callback) {
        EOSLeaderboardsNative.queryLeaderboardDefinitions(handle, options, callback);
    }

    public int getLeaderboardDefinitionCount() {
        return EOSLeaderboardsNative.getLeaderboardDefinitionCount(handle);
    }

    public LeaderboardDefinition copyLeaderboardDefinitionByIndex(CopyLeaderboardDefinitionByIndexOptions options) throws EOSException {
        return EOSLeaderboardsNative.copyLeaderboardDefinitionByIndex(handle, options);
    }

    public LeaderboardDefinition copyLeaderboardDefinitionByLeaderboardId(CopyLeaderboardDefinitionByLeaderboardIdOptions options) throws EOSException {
        return EOSLeaderboardsNative.copyLeaderboardDefinitionByLeaderboardId(handle, options);
    }

    public void queryLeaderboardRanks(QueryLeaderboardRanksOptions options, OnQueryLeaderboardRanksCompleteCallback callback) {
        EOSLeaderboardsNative.queryLeaderboardRanks(handle, options, callback);
    }

    public int getLeaderboardRecordCount() {
        return EOSLeaderboardsNative.getLeaderboardRecordCount(handle);
    }

    public LeaderboardRecord copyLeaderboardRecordByIndex(CopyLeaderboardRecordByIndexOptions options) throws EOSException {
        return EOSLeaderboardsNative.copyLeaderboardRecordByIndex(handle, options);
    }

    public LeaderboardRecord copyLeaderboardRecordByUserId(CopyLeaderboardRecordByUserIdOptions options) throws EOSException {
        return EOSLeaderboardsNative.copyLeaderboardRecordByUserId(handle, options);
    }

    public static class QueryLeaderboardDefinitionsOptions {
        public final EOS.ProductUserId localUserId;
        public final Date startTime;
        public final Date endTime;

        public QueryLeaderboardDefinitionsOptions(EOS.ProductUserId localUserId, Date startTime, Date endTime) {
            this.localUserId = localUserId;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public static class OnQueryLeaderboardDefinitionsCompleteCallbackInfo {
        public final int resultCode;

        public OnQueryLeaderboardDefinitionsCompleteCallbackInfo(int resultCode) {
            this.resultCode = resultCode;
        }
    }

    public static class CopyLeaderboardDefinitionByIndexOptions {
        int index;

        public CopyLeaderboardDefinitionByIndexOptions(int index) {
            this.index = index;
        }
    }

    public static class CopyLeaderboardDefinitionByLeaderboardIdOptions {
        String id;

        public CopyLeaderboardDefinitionByLeaderboardIdOptions(String id) {
            this.id = id;
        }
    }

    public static class LeaderboardDefinition extends EOSHandle {
        public final String leaderboardId;
        public final String statName;
        public final Aggregation aggregation;
        public final Date startTime;
        public final Date endTime;

        public LeaderboardDefinition(long handle, String leaderboardId, String statName, Aggregation aggregation, Date startTime, Date endTime) {
            super(handle);
            this.leaderboardId = leaderboardId;
            this.statName = statName;
            this.aggregation = aggregation;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public void release() {
            EOSLeaderboardsNative.releaseLeaderboardDefinition(ptr);
        }
    }

    public static class QueryLeaderboardRanksOptions {
        public final String leaderboardId;
        public final EOS.ProductUserId localUserId;

        public QueryLeaderboardRanksOptions(String leaderboardId, EOS.ProductUserId localUserId) {
            this.leaderboardId = leaderboardId;
            this.localUserId = localUserId;
        }
    }

    public static class OnQueryLeaderboardRanksCompleteCallbackInfo {
        public final int resultCode;

        public OnQueryLeaderboardRanksCompleteCallbackInfo(int resultCode) {
            this.resultCode = resultCode;
        }
    }

    public static class LeaderboardRecord extends EOSHandle {
        public final EOS.ProductUserId userId;
        public final int rank;
        public final int score;
        public final String userDisplayName;

        public LeaderboardRecord(long ptr, EOS.ProductUserId userId, int rank, int score, String userDisplayName) {
            super(ptr);
            this.userId = userId;
            this.rank = rank;
            this.score = score;
            this.userDisplayName = userDisplayName;
        }

        public void release() {
            EOSLeaderboardsNative.releaseLeaderboardRecord(ptr);
        }
    }

    public static class CopyLeaderboardRecordByIndexOptions {
        int index;

        public CopyLeaderboardRecordByIndexOptions(int index) {
            this.index = index;
        }
    }

    public static class CopyLeaderboardRecordByUserIdOptions {
        EOS.ProductUserId userId;

        public CopyLeaderboardRecordByUserIdOptions(EOS.ProductUserId userId) {
            this.userId = userId;
        }
    }

    public interface OnQueryLeaderboardDefinitionsCompleteCallback {
        void run(OnQueryLeaderboardDefinitionsCompleteCallbackInfo data);
    }

    public interface OnQueryLeaderboardRanksCompleteCallback {
        void run(OnQueryLeaderboardRanksCompleteCallbackInfo data);
    }

    public enum Aggregation {
        MIN, MAX, SUM, LATEST;

        static final Aggregation[] values;

        static {
            values = Aggregation.values();
        }

        static Aggregation fromInt(int i) {
            for (Aggregation value : values) {
                if (value.ordinal() == i) {
                    return value;
                }
            }
            return null;
        }
    }
}
