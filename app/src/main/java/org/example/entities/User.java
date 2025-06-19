package org.example.entities;

import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.utils.UserServiceUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class User {

    private String name;
    private String emailId;
    @JsonIgnore
    private static final String userIdHelper = "10000ABC";
    @JsonIgnore
    private static int toAddOnSuffix = 3;
    private String userId;
    private char gender;
    private String password;
    private String hashedPassword;
    private LocalDate dateOfBirth;
    @JsonIgnore
    private List<Ticket> bookedTickets = new ArrayList<>();

    public User(String name, String emailId, char gender, String password, int day, int month, int year)
            throws IllegalArgumentException {
        setUserId();
        setName(name);
        setEmailId(emailId);
        if (genderCheck(gender)) {
            setGender(gender);
        } else {
            throw new IllegalArgumentException("Did not enter correct gender");
        }

        setPassword(password);
        setHashedPassword(UserServiceUtil.hashPassword(password));
        setDateOfBirth(day, month, year);

    }

    private boolean genderCheck(char gender) {
        return gender == 'F' || gender == 'f' || gender == 'm' || gender == 'M';
    }

    public User(String name, String password) {
        setName(name);
        setPassword(password);
        setHashedPassword(UserServiceUtil.hashPassword(password));
    }

    public User() {

    }

    private void setUserId() {
        this.userId = userIdHelper + toAddOnSuffix;
        toAddOnSuffix += 1;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {

        this.emailId = emailId;
    }

    public void setGender(char gender1) {
        char normalizedGender = Character.toUpperCase(gender1);
        this.gender = normalizedGender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(int day, int month, int year) {
        try {
            this.dateOfBirth = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid Date input");
        }
    }

    public String getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public char getGender() {
        return this.gender;
    }

    @JsonIgnore
    public String getDateOfBirthInFormat() {
        int day = this.dateOfBirth.getDayOfMonth();
        Month month = this.dateOfBirth.getMonth();
        int year = this.dateOfBirth.getYear();
        String dob = day + " " + month + " ," + year;
        return dob;

    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    // ListOfTickets
    @JsonIgnore
    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    @JsonIgnore
    public List<Ticket> getBookedTickets() {
        if (this.bookedTickets.isEmpty()) {
            this.bookedTickets = Ticket.getAllTickets().stream()
                    .filter(t -> t.getUserId().equals(this.getUserId()))
                    .collect(Collectors.toList());

        }
        return this.bookedTickets;

    }

    @JsonIgnore
    public void printTicketDetails() {

        this.bookedTickets = this.getBookedTickets();

        if (this.bookedTickets.isEmpty()) {
            System.out.println("No tickets booked yet");
            return;
        }

        for (Ticket ticket : this.bookedTickets) {
            System.out.println("*********************");
            ticket.getTicketInformation(); // make sure you have a getTicketInformation() in Ticket
        }
    }

}
