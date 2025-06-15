package org.example.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Ticket {
    private String ticketId;
    private String trainId;
    private String userId;
    private String source;
    private String destionation;
    private String paymentStatus;
    private LocalDateTime creationDateTime;
    private LocalDateTime dateOfTravel;
    private Train trainDetails;
    private List<Integer> seatNumber;

}
