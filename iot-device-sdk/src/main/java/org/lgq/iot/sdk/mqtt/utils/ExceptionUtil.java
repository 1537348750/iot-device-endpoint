package org.lgq.iot.sdk.mqtt.utils;

public class ExceptionUtil {
    private static final int DEFAULT_LINE = 15;

    public static String getExceptionCause(Throwable e) {
        StringBuilder sb;
        for (sb = new StringBuilder(); e != null; e = e.getCause()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }

    public static String getAllExceptionStackTrace(Throwable e) {
        if (e == null) {
            return "";
        } else {
            StringBuilder stackTrace = new StringBuilder(e.toString());
            StackTraceElement[] astacktraceelement = e.getStackTrace();
            int var4 = astacktraceelement.length;

            for (StackTraceElement anAstacktraceelement : astacktraceelement) {
                stackTrace.append("\r\n").append("\tat ").append(anAstacktraceelement);
            }

            return stackTrace.toString();
        }
    }

    public static String getExceptionStackTrace(Throwable e, int lineNum) {
        if (e == null) {
            return "";
        } else {
            StringBuilder stackTrace = new StringBuilder(e.toString());
            StackTraceElement[] astacktraceelement = e.getStackTrace();
            int size = Math.min(lineNum, astacktraceelement.length);

            for (int i = 0; i < size; ++i) {
                stackTrace.append("\r\n").append("\tat ").append(astacktraceelement[i]);
            }

            return stackTrace.toString();
        }
    }

    public static String getBriefStackTrace(Throwable e) {
        return getExceptionStackTrace(e, DEFAULT_LINE);
    }
}
