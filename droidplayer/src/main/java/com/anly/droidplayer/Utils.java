package com.anly.droidplayer;

import com.google.android.exoplayer2.C;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by mingjun on 16/9/29.
 */

public class Utils {

    public static boolean checkNotNull(Object o) {
        return o != null;
    }

    private static final StringBuilder formatBuilder = new StringBuilder();
    private static final Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

    public static String stringForTime(long timeMs) {
        if (timeMs == C.TIME_UNSET) {
            timeMs = 0;
        }
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        formatBuilder.setLength(0);
        return hours > 0 ? formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                : formatter.format("%02d:%02d", minutes, seconds).toString();
    }
}
