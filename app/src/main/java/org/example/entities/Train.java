package org.example.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Train {

    private String trainId;
    @JsonIgnore
    private final static String trainIdHelper = "ABC1010000";
    @JsonIgnore
    private static int suffixToAdd = 2;
    private String journeyId;
    private String name;
    private int trainStatus;
    private LocalTime delayedTime = LocalTime.of(0, 0, 0);
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LinkedHashMap<String, LocalDateTime> stations;
    private LinkedList<Coach> coaches;

    // **Public no-argument constructor**
    public Train() {
    }

    public Train(char journeyId, String name, int trainStatus, String source, String destination,
            LocalDateTime departureTime, LinkedHashMap<String, LocalDateTime> stations,
            LinkedList<Coach> coaches) {
        setTrainId();
        this.journeyId = this.trainId + journeyId;
        this.name = name;
        this.trainStatus = trainStatus;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.stations = stations;
        this.coaches = coaches;
    }

    // Getters and setters

    public String getTrainId() {
        return this.trainId;
    }

    private void setTrainId() {
        this.trainId = trainIdHelper + suffixToAdd;
        suffixToAdd++;
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrainStatus() {
        return this.trainStatus;
    }

    public void setTrainStatus(int trainStatus) {
        this.trainStatus = trainStatus;
    }

    public LocalTime getDelayedTime() {
        return delayedTime;
    }

    public void setDelayedTime(LocalTime delayedTime) {
        this.delayedTime = delayedTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LinkedHashMap<String, LocalDateTime> getStations() {
        return stations;
    }

    public void setStations(LinkedHashMap<String, LocalDateTime> stations) {
        this.stations = stations;
    }

    public LinkedList<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(LinkedList<Coach> coaches) {
        this.coaches = coaches;
    }

    @JsonIgnore
    public void getMap() {
        for (Map.Entry<String, LocalDateTime> station : this.stations.entrySet()) {
            System.out.println(station.getKey() + "-> "
                    + station.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    @JsonIgnore
    public String isMyTrainDelayed() {
        if (this.trainStatus == 1) {
            return "the train is on time";
        } else if (this.trainStatus == 0 && this.delayedTime != null) {
            return "the train is delayed by " + this.delayedTime.toString();
        }
        return "the train is delayed";
    }

    @JsonIgnore
    public void getTrainDetails() {
        System.out.println("*********************************");
        System.out.println("Train Id: " + this.getTrainId());
        System.out.println("Train Name: " + this.getName());
        System.out.println("Train Type: " + this.getJourneyId());
        System.out.println("Source station: " + this.getSource());
        System.out.println("End station: " + this.getDestination());
        System.out.println("Train Map: ");
        this.getMap();

    }

    @JsonIgnore
    public LocalDateTime getDepartureTimeOfSpecificStation(String source) {
        String temp = source.substring(1);
        String x = (source.charAt(0) + "").toUpperCase();
        return stations.get(x + temp);

    }

    @JsonIgnore
    public boolean isValidSourceAndDestination(String source, String destination) {
        List<String> temp = new ArrayList<>();

        for (Entry<String, LocalDateTime> item : this.getStations().entrySet()) {

            temp.add(item.getKey().toLowerCase());

        }

        int s = temp.indexOf(source.toLowerCase());
        int d = temp.indexOf(destination.toLowerCase());
        return s != -1 && d != -1 && s < d;

    }

    @JsonIgnore
    public void printSeats() {
        System.out.println("Seats for Train " + this.getTrainId() + " - " + this.getName());

        for (Coach coach : this.coaches) {
            System.out.println("  Coach " + coach.getCoachNo());

            for (Compartment compartment : coach.getListOfCompartment()) {
                System.out.println("    Compartment " + compartment.getCompartmentId());

                Seat[][] seats = compartment.getSeats();

                for (int row = 0; row < seats.length; row++) {
                    for (int col = 0; col < seats[row].length; col++) {
                        Seat seat = seats[row][col];
                        String status = seat.getIsAvailable() ? "Available" : "Booked";

                        System.out.print("[" + seat.getSeatNo() + "-" + seat.getSection() + "-"
                                + " - " + seat.getSeatType()
                                + " - " + status + "] ");
                    }
                    System.out.println();
                }
            }
        }
    }

}
