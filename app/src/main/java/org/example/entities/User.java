package org.example.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String emailId;
    private String userId;
    private char gender;
    private String password;
    private String hashedPassword;
    private LocalDate dateOfBirth;
    private List<Ticket> bookedTickets = new ArrayList<>();
}
