package edu.tutorials.trainreservation.domain;

public enum CoachType {
    SLEEPER(1),
    TIER_3_AC(2),
    TIER_2_AC(3),
    TIER_1_AC(4);

    private final double farePerKilometer;

    CoachType(double farePerKilometer) {
        this.farePerKilometer = farePerKilometer;
    }

    public double calculateFare(int distance, int passengerCount) {
        return farePerKilometer * distance * passengerCount;
    }
}
