package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketBookingService {
    private final TrainService trainService;
    private final List<Ticket> bookedTickets;

    public TicketBookingService(TrainService trainService) {
        this.trainService = trainService;
        bookedTickets = new ArrayList<>();
    }

    //    public Ticket bookTicket(String trainNumber, CoachType coachType, LocalDate travelDate, int passengerCount) {
    public Ticket bookTicket(String trainNumber, TrainSearchRequest trainSearchRequest) {
        CoachType coachType = trainSearchRequest.getCoachType();
        LocalDate travelDate = trainSearchRequest.getTravelDate();
        int passengerCount = trainSearchRequest.getPassengerCount();
        String sourceStation = trainSearchRequest.getSourceCity();
        String destinationStation = trainSearchRequest.getDestinationCity();

        Train selectedTrain = trainService.getTrainByNumber(trainNumber);

        List<Seat> bookedSeats = selectedTrain.reserveSeats(coachType, travelDate, passengerCount);
        System.out.println();
        double totalFare = coachType.calculateFare(selectedTrain.getTotalDistance(sourceStation, destinationStation), passengerCount);

        long pnr = bookedTickets.size() + 1 + 10000000;

        Ticket ticket = new Ticket(pnr, selectedTrain.getTrainNumber(), selectedTrain.getSource(sourceStation), selectedTrain.getDestination(destinationStation), travelDate, totalFare, bookedSeats);
        bookedTickets.add(ticket);

        return ticket;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public List<Ticket> getBookedTicketsFromPNR(int PNR) {
        List<Ticket> bookedTicket = new ArrayList<>();
        for (Ticket ticket : bookedTickets)
            if (ticket.getPnr() == PNR)
                bookedTicket.add(ticket);

        return bookedTicket;
    }

}