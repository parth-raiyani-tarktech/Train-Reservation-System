package edu.tutorials.trainreservation.reader;

import edu.tutorials.trainreservation.domain.City;
import edu.tutorials.trainreservation.domain.Coach;
import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.tutorials.trainreservation.utils.Constants.DASH;
import static edu.tutorials.trainreservation.utils.Constants.SPACE;

public class TrainDataParser {
    public static Train parse(String trainDetailsInput, String coachDetailsInput) {
        String[] trainDetails = trainDetailsInput.split(SPACE);
        String trainNo = trainDetails[0];

        List<City> Stations = new ArrayList<>();

        for (int i = 1; i < trainDetails.length; i++)
            Stations.add(parseCityDetails(trainDetails[i]));

        String[] coachDetails = coachDetailsInput.split(SPACE);
        coachDetails = Arrays.copyOfRange(coachDetails, 1, coachDetails.length);
        List<Coach> coaches = buildCoaches(coachDetails);

        return new Train(trainNo, Stations, coaches);
    }

    private static City parseCityDetails(String cityDetails) {
        String[] details = cityDetails.split(DASH);
        return new City(details[0], Integer.parseInt(details[1]));
    }

    private static List<Coach> buildCoaches(String[] coachDetails) {
        List<Coach> coaches = new ArrayList<>();
        for (String coachDetail : coachDetails) {
            String[] coach = coachDetail.split(DASH);
            CoachType coachType = getCoachType(coach[0]);
            coaches.add(new Coach(coach[0], coachType, Integer.parseInt(coach[1])));
        }
        return coaches;
    }

    private static CoachType getCoachType(String coachName) {
        if (coachName.startsWith("S")) {
            return CoachType.SLEEPER;
        } else if (coachName.startsWith("B")) {
            return CoachType.TIER_3_AC;
        } else if (coachName.startsWith("A")) {
            return CoachType.TIER_2_AC;
        } else if (coachName.startsWith("H")) {
            return CoachType.TIER_1_AC;
        }

        throw new IllegalArgumentException("Invalid Coach: " + coachName);
    }
}