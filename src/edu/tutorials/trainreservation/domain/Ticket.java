package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class Ticket {
    private final long pnr;
    private final String trainNumber;
    private final City from;
    private final City to;
    private final LocalDate travelDate;
    private final double totalFare;
    private final List<Seat> bookedSeats;

    public Ticket(long pnr, String trainNumber, City from, City to, LocalDate travelDate, double totalFare, List<Seat> bookedSeats) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.from = from;
        this.to = to;
        this.travelDate = travelDate;
        this.totalFare = totalFare;
        this.bookedSeats = bookedSeats;
    }

    public long getPnr() {
        return pnr;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getBookedSeats() {
        StringBuilder seats = new StringBuilder();
        for (Seat s : bookedSeats)
            seats.append(s.toString()).append(" ");

        return seats.toString();
    }

    public String toString() {
        return String.format(String.format("PNR: %d, Train: %s, Travel Date: %s, Total Fare: %.2f, Booked Seats: %s", pnr, trainNumber, travelDate.toString(), totalFare, Utils.toCommaSeparatedSeatNo(bookedSeats)));
    }

}
