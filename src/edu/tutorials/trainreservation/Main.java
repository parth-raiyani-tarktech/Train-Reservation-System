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

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Train> trains = DataReader.readTrainData();
        displayTrainDetails(trains); // done Train is saved

        TrainService trainService = new TrainService(trains);
        TicketBookingService ticketBookingService = new TicketBookingService(trainService);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter train search request (type 'exit' to quit)");

            String input = sc.nextLine();
            if (input.equals("exit")) {
                break;
            }
            if (input.equals("REPORT")) {
                displayReport(ticketBookingService.getBookedTickets());
                continue;
            }

            if (!input.contains(" ") && Long.parseLong(input) > 9999999) {
                List<Ticket> ticketBasedOnPNR = ticketBookingService.getBookedTicketsFromPNR(Integer.parseInt(input));
                if (ticketBasedOnPNR.isEmpty())
                    System.out.println("Invalid PNR");
                else
                    displayTickets(ticketBasedOnPNR);
                continue;
            }

            // Search Trains
            TrainSearchRequest trainSearchRequest = TrainSearchRequestReader.read(input);
            System.out.println(trainSearchRequest + "\n");

            List<Train> trainsForRoute = trainService.findTrains(trainSearchRequest);

            if (trainsForRoute.isEmpty())
                System.out.println("No Seats Available");
            else
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

    private static void displayReport(List<Ticket> bookingHistory) {
        System.out.println("PNR, DATE, TRAIN, FROM, TO, FARE, SEATS");
        for (Ticket ticket : bookingHistory) {
            System.out.println(ticket.getPnr() + ", " +
                    ticket.getTravelDate() + ", " +
                    ticket.getTrainNumber() + ", " +
                    ticket.getFrom() + ", " +
                    ticket.getTo() + ", " +
                    ticket.getTotalFare() + ", " +
                    ticket.getBookedSeats()
            );
        }
    }

    private static void displayTickets(List<Ticket> bookedTickets) {
        System.out.println("Booked Tickets: ");
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
}
