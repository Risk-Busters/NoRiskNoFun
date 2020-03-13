package com.riskbusters.norisknofun.domain.enumeration;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The SeverityType enumeration.
 */
public enum SeverityType {
    NULL, BAD, LESSBAD, NEUTRAL, SOSO, OK;

    public static SeverityType getAverage(SeverityType[] severities) {
        Arrays.sort(severities, Comparator.comparing(SeverityType::ordinal));
        return severities[severities.length / 2];
    }
}

