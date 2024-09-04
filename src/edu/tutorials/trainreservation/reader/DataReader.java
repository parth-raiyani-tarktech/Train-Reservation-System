package edu.tutorials.trainreservation.reader;

import edu.tutorials.trainreservation.domain.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    public static List<Train> readTrainData() {
        Scanner sc = new Scanner(System.in);
        int trainCount = sc.nextInt();
        sc.nextLine();

        List<Train> trains = new ArrayList<>();

        for (int i = 0; i < trainCount; i++) {
            String trainDetailsInput = sc.nextLine();
            String coachDetailsInput = sc.nextLine();
            Train train = TrainDataParser.parse(trainDetailsInput, coachDetailsInput);
            trains.add(train);
        }

        return trains;
    }

    public static String readTrainNoToBookTicket() {
        return readString("\nEnter train no to book ticket");
    }

    public static String readCoachTypeCode() {
        return readString("Enter the Coach Type code");
    }

    private static String readString(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        return sc.nextLine();
    }
}
