package org.example.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Train {

    private String trainId;
    private final static String trainIdHelper = "ABC1010000";
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

    public void getMap() {
        for (Map.Entry<String, LocalDateTime> station : this.stations.entrySet()) {
            System.out.println(station.getKey() + "-> " + station.getValue());
        }
    }

    public String isMyTrainDelayed() {
        if (this.trainStatus == 1) {
            return "the train is on time";
        } else if (this.trainStatus == 0 && this.delayedTime != null) {
            return "the train is delayed by " + this.delayedTime.toString();
        }
        return "the train is delayed";
    }
}
