package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.exception.UnavailableSeatException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seat {
    private final String seatNo;
    private final List<LocalDate> reservedDates;

    public Seat(String seatNo) {
        this.seatNo = seatNo;
        reservedDates = new ArrayList<>();
    }

    public boolean isAvailableFor(LocalDate date) {
        return !reservedDates.contains(date);
    }

    public String toString() {
        return seatNo;
    }

    public void reserveSeat(LocalDate travelDate) {
        if (!isAvailableFor(travelDate)) {
            throw new UnavailableSeatException("Seat " + seatNo + " is already reserved for the date " + travelDate);
        }

        if (isAvailableFor(travelDate)) {
            reservedDates.add(travelDate);
        }
    }
}
