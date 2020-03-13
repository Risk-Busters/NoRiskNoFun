package com.riskbusters.norisknofun.domain.enumeration;

/**
 * The ProbabilityType enumeration.
 */
public enum ProbabilityType {
    NULL, SURE, PROBABLY, MAYBE, NOTLIKELY, NOTGONNAHAPPEN;

    public static ProbabilityType getAverage(ProbabilityType[] probabilities) {
        double sum = 0;
        for (ProbabilityType probability : probabilities) {
            sum += probability.ordinal();
        }
        sum = sum / probabilities.length;

        return ProbabilityType.values()[(int) Math.round(sum)];
    }
}
