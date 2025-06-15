package org.example.services;

import java.io.File;

import org.example.entities.User;

public class UserBookingService {
    private User user;

    private final static String usersURL = "../localdb/users.json";

    public UserBookingService(User user) {
        this.user = user;
        File users = new File(usersURL);

    }
}
