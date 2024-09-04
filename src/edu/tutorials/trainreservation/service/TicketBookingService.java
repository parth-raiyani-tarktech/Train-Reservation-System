package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TicketBookingService {
    private final TrainService trainService;
    private final List<Ticket> bookedTickets;

    public TicketBookingService(TrainService trainService) {
        this.trainService = trainService;
        bookedTickets = new ArrayList<>();
    }

    public Ticket bookTicket(String trainNumber, TrainSearchRequest trainSearchRequest) {
        Train selectedTrain = trainService.getTrainByNumber(trainNumber);

        CoachType coachType = trainSearchRequest.getCoachType();
        LocalDate travelDate = trainSearchRequest.getTravelDate();
        int passengerCount = trainSearchRequest.getPassengerCount();
        String sourceCity = trainSearchRequest.getSourceCity();
        String destinationCity = trainSearchRequest.getDestinationCity();

        List<Seat> bookedSeats = selectedTrain.reserveSeats(coachType, travelDate, passengerCount);
        double totalFare = coachType.calculateFare(selectedTrain.getTotalDistance(sourceCity, destinationCity),
                passengerCount);

        long pnr = 100000000 + bookedTickets.size() + 1;

        Ticket ticket = new Ticket(pnr, selectedTrain.getTrainNumber(), travelDate, sourceCity, destinationCity,
                totalFare, bookedSeats);
        bookedTickets.add(ticket);

        return ticket;
    }

    private static void sortTicketsByPnr(List<Ticket> tickets) {
        tickets.sort(Comparator.comparingLong(Ticket::getPnr));
    }

    public List<Ticket> getBookedTickets() {
        sortTicketsByPnr(bookedTickets);
        return bookedTickets;
    }

    public Ticket getTicketByPnr(String input) {
        long pnr = Long.parseLong(input);

        for (Ticket ticket : bookedTickets) {
            if (ticket.getPnr() == pnr) {
                return ticket;
            }
        }
        System.out.println("Invalid PNR");
        return null;
    }
}
