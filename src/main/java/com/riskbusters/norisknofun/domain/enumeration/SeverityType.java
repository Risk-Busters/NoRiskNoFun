package com.riskbusters.norisknofun.domain.enumeration;

/**
 * The SeverityType enumeration.
 */
public enum SeverityType {
    NULL, BAD, LESSBAD, NEUTRAL, SOSO, OK;

    public static SeverityType getAverage(SeverityType[] severities) {
        double sum = 0;
        for (SeverityType severity : severities) {
            sum += severity.ordinal();
        }
        sum = sum / severities.length;

        return SeverityType.values()[(int) Math.round(sum)];
    }
}

