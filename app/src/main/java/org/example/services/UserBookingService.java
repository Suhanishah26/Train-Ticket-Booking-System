package org.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.entities.User;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserBookingService {
    private User user;

    private final static String usersURL = "localdb/users.json";

    private List<User> listOfUsers;
    ObjectMapper mapper = new ObjectMapper();

    public UserBookingService(User user) throws RuntimeException {
        this.user = user;
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(usersURL);
        if (in == null) {
            throw new RuntimeException("Users.json file not found");
        }

        try {
            listOfUsers = mapper.readValue(in, new TypeReference<List<User>>() {
            });
        } catch (StreamReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean isLogin(User user) {
        Optional<User> user1 = listOfUsers.stream().filter(
                (u) -> user.getUserId() == u.getUserId()

        ).findFirst();

        return user1.isPresent();
    }
}
