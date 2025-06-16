package org.example.entities;

import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

    private String name;
    private String emailId;
    private static final String userIdHelper = "10000ABC";
    private static int toAddOnSuffix = 3;
    private String userId;
    private char gender;
    private String password;
    private String hashedPassword;
    private LocalDate dateOfBirth;
    private List<Ticket> bookedTickets = new ArrayList<>();
    private static List<Ticket> allTickets = new ArrayList<>();

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        InputStream in = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("localdb/tickets.json");

        if (in == null) {
            throw new RuntimeException("ticket.json file not found.");
        }
        try {
            allTickets = mapper.readValue(in, new TypeReference<List<Ticket>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User(String name, String emailId, char gender, String password, int day, int month, int year) {
        setUserId();
        setName(name);
        setEmailId(emailId);
        setGender(gender);
        setPassword(password);
        setDateOfBirth(day, month, year);

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
    public List<Ticket> getBookedTickets() {
        this.bookedTickets = allTickets.stream()
                .filter(t -> t.getUserId().equals(this.getUserId()))
                .collect(Collectors.toList());

        return bookedTickets;
    }

}
