package com.gniot.parkinglot.constants;

public enum SubscriptionTypeFactor {
    ELITE(1.2),
    PREMIUM(1.5),
    SUPER(1.8),
    BASIC(1.0);
    private final double factor;

    SubscriptionTypeFactor(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
