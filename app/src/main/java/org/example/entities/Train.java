package org.example.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Train {

    private String trainId;
    private String journeyId;
    private String name;
    private int trainStatus;
    private LocalTime delayedTime = LocalTime.of(0, 0, 0);
    private String source;
    private String destionation;
    private LocalDateTime departureTime;
    private LinkedHashMap<String, LocalDateTime> stations;
    private LinkedList<Coach> coaches;

}
