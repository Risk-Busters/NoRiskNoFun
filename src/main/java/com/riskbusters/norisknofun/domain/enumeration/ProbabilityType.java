package com.riskbusters.norisknofun.domain.enumeration;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The ProbabilityType enumeration.
 */
public enum ProbabilityType {
    NULL, SURE, PROBABLY, MAYBE, NOTLIKELY, NOTGONNAHAPPEN;

    public static ProbabilityType getAverage(ProbabilityType[] probabilities) {
        if (probabilities.length == 0) return ProbabilityType.NULL;

        Arrays.sort(probabilities, Comparator.comparing(ProbabilityType::ordinal));
        return probabilities[probabilities.length / 2];
    }
}
