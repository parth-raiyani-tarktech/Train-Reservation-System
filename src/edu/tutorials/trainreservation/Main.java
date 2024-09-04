package edu.tutorials.trainreservation;

import edu.tutorials.trainreservation.domain.Coach;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;
import edu.tutorials.trainreservation.reader.DataReader;
import edu.tutorials.trainreservation.reader.TrainSearchRequestReader;
import edu.tutorials.trainreservation.service.TicketBookingService;
import edu.tutorials.trainreservation.service.TrainService;

import static edu.tutorials.trainreservation.utils.Constants.SPACE;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Train> trains = DataReader.readTrainData();
        displayTrainDetails(trains);

        TrainService trainService = new TrainService(trains);
        TicketBookingService ticketBookingService = new TicketBookingService(trainService);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "\nEnter train search request \nOR \nEnter PNR to see ticket detail by PNR \nOR \nEnter \"EXIT\" to exit \nOR \nEnter \"REPORT\" to generate booking report");

            String input = sc.nextLine();

            if (isSingleArg(input)) {
                if (input.equals("EXIT")) {
                    break;
                } else if (input.equals("REPORT")) {
                    displayTickets(ticketBookingService.getBookedTickets());
                } else {
                    System.out.println(ticketBookingService.getTicketByPnr(input));
                }
                continue;
            }
            // Search Trains
            TrainSearchRequest trainSearchRequest = TrainSearchRequestReader.read(input);
            System.out.println(trainSearchRequest + "\n");

            List<Train> trainsForRoute = trainService.findTrains(trainSearchRequest);
            displayTrainNo(trainsForRoute);

            if (!trainsForRoute.isEmpty()) {
                // Select Train Number
                String trainNo = DataReader.readTrainNoToBookTicket();
                Ticket bookedTicket = ticketBookingService.bookTicket(trainNo, trainSearchRequest);
                System.out.println("Ticket booked successfully: " + bookedTicket);
            }
        }

        displayTickets(ticketBookingService.getBookedTickets());
    }

    private static void displayTickets(List<Ticket> bookedTickets) {
        System.out.println("Booked Tickets: ");
        System.out.println("PNR, TRAIN, DATE, FROM, TO, FARE, SEATS");
        for (Ticket ticket : bookedTickets) {
            System.out.println(ticket);
        }
    }

    private static void displayTrainNo(List<Train> trains) {
        System.out.println("Found " + trains.size() + " trains");

        for (Train train : trains) {
            System.out.println("Train: " + train.getTrainNumber());
        }
    }

    private static void displayTrainDetails(List<Train> trains) {
        for (Train train : trains) {
            System.out.println("Train: " + train.getTrainNumber());

            for (Coach coach : train.getCoaches()) {
                System.out.println("Coach: " + coach.getCoachName() + " Type: " + coach.getCoachType());

                for (Seat seat : coach.getSeats()) {
                    System.out.print(seat + " ");
                }
                System.out.print("\n\n");
            }
        }
        System.out.println();
    }

    private static boolean isSingleArg(String input) {
        if (input.split(SPACE).length == 1) {
            return true;
        }
        return false;
    }
}
