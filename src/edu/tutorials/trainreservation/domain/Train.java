package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.exception.UnavailableSeatException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private final String trainNo;
    private final List<City> stations;
    private final List<Coach> coaches;

    public Train(String trainNo, List<City> stations, List<Coach> coaches) {
        this.trainNo = trainNo;
        this.stations = stations;
        this.coaches = coaches;
    }

    public String getTrainNumber() {
        return trainNo;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public boolean hasRoute(String sourceCity, String destinationCity) {
        boolean source = false, destination = false;
        for (City station : this.stations) {
            if (station.getName().equals(sourceCity)) {
                source = true;
                break;
            }
        }
        for (City station : this.stations) {
            if (station.getName().equals(destinationCity)) {
                destination = true;
                break;
            }
        }

        return source && destination;
    }

    public City getSource(String city) {
        for (City station : this.stations)
            if (station.getName().equals(city))
                return station;

        return stations.getFirst();
    }

    public City getDestination(String city) {
        for (City station : this.stations)
            if (station.getName().equals(city))
                return station;

        return stations.getLast();
    }

    public boolean hasCoachType(CoachType coachType) {
        for (Coach coach : coaches) {
            if (coach.getCoachType().equals(coachType)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAvailableSeats(CoachType coachType, LocalDate travelDate, int passengerCount) {
        List<Seat> availableSeats = getAvailableSeats(coachType, travelDate);

        return availableSeats.size() >= passengerCount;
    }

    public List<Seat> reserveSeats(CoachType coachType, LocalDate travelDate, int passengerCount) {
        if (!hasAvailableSeats(coachType, travelDate, passengerCount)) {
            throw new UnavailableSeatException("No available seats for date " + travelDate + " in coach type " + coachType + " for " + passengerCount + " passengers");
        }

        List<Seat> availableSeats = getAvailableSeats(coachType, travelDate);

        List<Seat> reservedSeats = new ArrayList<>();
        for (int i = 0; i < passengerCount; i++) {

            Seat seat = availableSeats.get(i);
            seat.reserveSeat(travelDate);

            reservedSeats.add(seat);
        }

        return reservedSeats;
    }

    private List<Seat> getAvailableSeats(CoachType coachType, LocalDate travelDate) {
        List<Seat> availableSeats = new ArrayList<>();

        for (Coach coach : coaches) {
            if (coach.getCoachType().equals(coachType)) {
                availableSeats.addAll(coach.getAvailableSeats(travelDate));
            }
        }
        return availableSeats;
    }

    public int getTotalDistance(String source, String destination) {
        int destinationDistance = 0, sourceDistance = 0;
        for (City station : this.stations) {
            if (station.getName().equals(source)) {
                sourceDistance = station.getDistance();
                break;
            }
        }
        for (City station : this.stations) {
            if (station.getName().equals(destination)) {
                destinationDistance = station.getDistance();
                break;
            }
        }
        return destinationDistance - sourceDistance;
    }
}
