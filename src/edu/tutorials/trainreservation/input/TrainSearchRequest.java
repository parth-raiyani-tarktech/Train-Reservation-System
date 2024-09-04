package edu.tutorials.trainreservation.input;

import edu.tutorials.trainreservation.domain.CoachType;

import java.time.LocalDate;

public class TrainSearchRequest {
    private final String sourceCity;
    private final String destinationCity;
    private CoachType coachType;
    private final LocalDate travelDate;
    private final int passengerCount;

    public TrainSearchRequest(String sourceCity, String destinationCity, LocalDate travelDate, CoachType coachType,
            int noOfPassengers) {

        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.coachType = coachType;
        this.travelDate = travelDate;
        this.passengerCount = noOfPassengers;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String toString() {
        return "Train Search Request: " + sourceCity + " " + destinationCity + " " + travelDate + " " + coachType + " "
                + passengerCount;
    }
}
