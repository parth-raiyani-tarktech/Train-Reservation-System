package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class Ticket {
    private final long pnr;
    private final String trainNumber;
    private final LocalDate travelDate;
    private final String from;
    private final String to;
    private final double totalFare;
    private final List<Seat> bookedSeats;

    public Ticket(long pnr, String trainNumber, LocalDate travelDate, String to, String from, double totalFare,
            List<Seat> bookedSeats) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.travelDate = travelDate;
        this.from = from;
        this.to = to;
        this.totalFare = totalFare;
        this.bookedSeats = bookedSeats;
    }

    public long getPnr() {
        return pnr;
    }

    public String toString() {
        return String.format(String.format(
                "PNR: %d, Train: %s, Travel Date: %s, From: %s, To: %s Total Fare: %.2f, Booked Seats: %s",
                pnr, trainNumber, travelDate.toString(), from, to, totalFare,
                Utils.toCommaSeparatedSeatNo(bookedSeats)));
    }

}
