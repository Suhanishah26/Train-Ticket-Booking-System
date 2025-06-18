package org.example.entities;

import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.example.utils.MapperUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

enum PaymentStatus {
    Paid, Pending
}

public class Ticket {

    @JsonIgnore
    private final static String ticketIdHelper = "20000AB10";
    @JsonIgnore
    private static int suffixHelper = 3;

    private String ticketId;
    private String trainId;
    private String userId;
    private String source;
    private String destination;
    private int coachNo;
    private int compartmentId;
    private PaymentStatus paymentStatus;
    private LocalDateTime creationDateTime;
    private LocalDateTime dateOfTravel;
    private List<Integer> seatNumber;
    @JsonIgnore
    private static List<Ticket> allTickets = new ArrayList<>();

    @JsonIgnore
    public static List<Ticket> getAllTickets() {

        InputStream in = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("localdb/tickets.json");

        if (in == null) {
            throw new RuntimeException("ticket.json file not found.");
        }

        try {
            allTickets = MapperUtil.getMapper().readValue(in, new TypeReference<List<Ticket>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allTickets;
    }

    // **No-argument constructor for Jackson**
    public Ticket() {
    }

    // **Parameterized constructor (optional, for manual instantiation)**
    public Ticket(User user, Train train, String source, String destination, int day,
            int month, int year, int coachNo, int compartmentId, List<Integer> seatNumbers) {

        this.userId = user.getUserId();
        this.trainId = train.getTrainId();
        this.source = this.normalizeTheString(source);
        this.destination = this.normalizeTheString(destination);
        this.paymentStatus = PaymentStatus.Paid;
        this.creationDateTime = LocalDateTime.now();

        try {
            this.dateOfTravel = LocalDateTime.of(LocalDate.of(year, month, day),
                    train.getDepartureTimeOfSpecificStation(source).toLocalTime());

        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date of travel.");
        }
        this.coachNo = coachNo;
        this.compartmentId = compartmentId;
        this.seatNumber = seatNumbers;

        setTicketId();
    }

    private void setTicketId() {
        this.ticketId = ticketIdHelper + suffixHelper;
        suffixHelper++;
    }

    // Getters and setters for Jackson

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(LocalDateTime dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public List<Integer> getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(List<Integer> seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(int coachNo) {
        this.coachNo = coachNo;
    }

    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    // Additional utility methods (optional)
    @JsonIgnore
    public String getCreationTimeString() {
        return "Ticket was created on " + this.creationDateTime.getDayOfMonth()
                + " " + this.creationDateTime.getMonth()
                + " " + this.creationDateTime.getYear()
                + " at " + this.creationDateTime.getHour()
                + ":" + this.creationDateTime.getMinute()
                + ":" + this.creationDateTime.getSecond();
    }

    @JsonIgnore
    public String getDateOfTravelString() {
        return "Date of travel is on " + this.dateOfTravel.getDayOfMonth()
                + " " + this.dateOfTravel.getMonth()
                + " " + this.dateOfTravel.getYear()
                + " at " + this.dateOfTravel.getHour()
                + ":" + this.dateOfTravel.getMinute();
    }

    @JsonIgnore
    private String normalizeTheString(String someSring) {
        String temp = someSring.trim().substring(1);
        String x = (someSring.trim().charAt(0) + "").toUpperCase();
        return x + temp;
    }

    @JsonIgnore
    public String getSeatNumbersString() {
        return seatNumber.toString();
    }

    @JsonIgnore
    public void getTicketInformation() {
        System.out.println("Ticket Id :" + getTicketId());
        System.out.println("Train Id :" + getTrainId());
        System.out.println("User Id :" + getUserId());
        System.out.println("Source :" + getSource());
        System.out.println("Destionation :" + getDestination());
        System.out.println("Payment Status :" + getPaymentStatus());
        System.out.println("Created at :" + getCreationTimeString());
        System.out.println("Date Of travel: " + getDateOfTravelString());
        System.out.println("Seat No: " + getSeatNumbersString());
        System.out.println("Coach No:" + getCoachNo());
        System.out.println("Compartment Id:" + getCompartmentId());

    }
}
