package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainService {
    private final Map<String, Train> trainByNumber = new HashMap<>();

    public TrainService(List<Train> trains) {
        for (Train train : trains) {
            trainByNumber.put(train.getTrainNumber(), train);
        }
    }

    public List<Train> findTrains(TrainSearchRequest trainSearchRequest) {
        String sourceCity = trainSearchRequest.getSourceCity();
        String destinationCity = trainSearchRequest.getDestinationCity();
        CoachType coachType = trainSearchRequest.getCoachType();
        LocalDate travelDate = trainSearchRequest.getTravelDate();
        int passengerCount = trainSearchRequest.getPassengerCount();

        List<Train> matchingTrains = new ArrayList<>();
        for (Train train : trainByNumber.values()) {
            if (train.hasRoute(sourceCity, destinationCity) && train.hasCoachType(coachType)
                    && train.hasAvailableSeats(coachType, travelDate, passengerCount)) {
                matchingTrains.add(train);
            }
        }
        return matchingTrains;
    }

    public Train getTrainByNumber(String trainNumber) {
        return trainByNumber.get(trainNumber);
    }
}
