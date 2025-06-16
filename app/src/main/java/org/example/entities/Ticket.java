package org.example.entities;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

enum PaymentStatus {
    Paid, Pending, Cancel
}

public class Ticket {

    private final static String ticketIdHelper = "20000AB10";
    private static int suffixHelper = 3;

    private String ticketId;
    private String trainId;
    private String userId;
    private String source;
    private String destination;
    private PaymentStatus paymentStatus;
    private LocalDateTime creationDateTime;
    private LocalDateTime dateOfTravel;
    private List<Integer> seatNumber;

    // **No-argument constructor for Jackson**
    public Ticket() {
    }

    // **Parameterized constructor (optional, for manual instantiation)**
    public Ticket(User user, Train train, String source, String destination, PaymentStatus paymentStatus, int day,
            int month, int year, int hour, int minutes, int seconds, List<Integer> seatNumbers) {

        this.userId = user.getUserId();
        this.trainId = train.getTrainId();
        this.source = source;
        this.destination = destination;
        this.paymentStatus = paymentStatus;
        this.creationDateTime = LocalDateTime.now();

        try {
            LocalDateTime temp = LocalDateTime.of(year, month, day, hour, minutes, seconds);
            this.dateOfTravel = temp;
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date of travel.");
        }
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

    // Additional utility methods (optional)

    public String getCreationTimeString() {
        return "Ticket was created on " + this.creationDateTime.getDayOfMonth()
                + " " + this.creationDateTime.getMonth()
                + " " + this.creationDateTime.getYear()
                + " at " + this.creationDateTime.getHour()
                + ":" + this.creationDateTime.getMinute()
                + ":" + this.creationDateTime.getSecond();
    }

    public String getDateOfTravelString() {
        return "Date of travel is on " + this.dateOfTravel.getDayOfMonth()
                + " " + this.dateOfTravel.getMonth()
                + " " + this.dateOfTravel.getYear()
                + " at " + this.dateOfTravel.getHour()
                + ":" + this.dateOfTravel.getMinute();
    }

    public String getSeatNumbersString() {
        return seatNumber.toString();
    }
}
