package com.bearwaves.eos4j;

public class EOSLogging {

    static LoggingCallback loggingCallback;

    public static void setLogLevel(LogCategory category, LogLevel level) throws EOSException {
        EOS.throwIfErrorCode(EOSLoggingNative.setLogLevel(category, level));
    }

    public static void setCallback(LoggingCallback callback) throws EOSException {
        loggingCallback = callback;
        EOS.throwIfErrorCode(EOSLoggingNative.setCallback(loggingCallback));
    }

    public enum LogLevel {
        OFF(0),
        FATAL(100),
        ERROR(200),
        WARNING(300),
        INFO(400),
        VERBOSE(500),
        VERY_VERBOSE(600);

        final int level;
        final static LogLevel[] values;

        static {
            values = LogLevel.values();
        }

        LogLevel(int level) {
            this.level = level;
        }

        static LogLevel fromInt(int i) {
            for (LogLevel value : values) {
                if (value.level == i) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum LogCategory {
        CORE(0),
        AUTH(1),
        FRIENDS(2),
        PRESENCE(3),
        USER_INFO(4),
        HTTP_SERIALIZATION(5),
        ECOM(6),
        P2P(7),
        SESSIONS(8),
        RATE_LIMITER(9),
        PLAYER_DATA_STORAGE(10),
        ANALYTICS(11),
        MESSAGING(12),
        CONNECT(13),
        OVERLAY(14),
        ACHIEVEMENTS(15),
        STATS(16),
        UI(17),
        LOBBY(18),
        LEADERBOARDS(19),
        KEYCHAIN(20),
        INTEGRATED_PLATFORM(21),
        TITLE_STORAGE(22),
        MODS(23),
        ANTI_CHEAT(24),
        REPORTS(25),
        SANCTIONS(26),
        PROGRESSION_SNAPSHOTS(27),
        KWS(28),
        RTC(29),
        RTC_ADMIN(30),
        CUSTOM_INVITES(31),

        ALL_CATEGORIES(0x7fffffff);

        final int category;

        LogCategory(int category) {
            this.category = category;
        }
    }

    public static class LogMessage {
        public final LogLevel level;
        public final String category;
        public final String message;

        LogMessage(LogLevel level, String category, String message) {
            this.level = level;
            this.category = category;
            this.message = message;
        }
    }

    public interface LoggingCallback {
        void run(LogMessage message);
    }

}
