package org.example.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.entities.Coach;
import org.example.entities.Compartment;
import org.example.entities.Seat;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.utils.MapperUtil;
import org.example.utils.UserServiceUtil;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.reflect.ClassPath;

public class UserBookingService {
    private User user;

    private final static String usersURL = "localdb/users.json";

    private List<User> listOfUsers;

    public UserBookingService() throws IOException {

        this.listOfUsers = loadAllUsers();
    }

    private List<User> loadAllUsers() throws RuntimeException, IOException {

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(usersURL);
        if (in == null) {
            throw new RuntimeException("Users.json file not found");
        }
        List<User> tempOfUsers = new ArrayList<>();
        try {
            tempOfUsers = MapperUtil.getMapper().readValue(in, new TypeReference<List<User>>() {
            });
        } catch (StreamReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new IOException("couldn't read users from users.json");
        }
        return tempOfUsers;
    }

    public UserBookingService(User user) throws IOException {
        this.user = user;
        this.listOfUsers = loadAllUsers();

    }

    public User isLoggedIn(User user) {
        Optional<User> user1 = listOfUsers.stream().filter(
                (u) -> user.getName().equalsIgnoreCase(u.getName())
                        && UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword())

        ).findFirst();

        return user1.orElse(null);
    }

    public boolean signIn(User user) throws IOException {

        listOfUsers.add(user);
        URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource("localdb/users.json");

        if (resource == null) {
            throw new FileNotFoundException("users.json not found in resources/localdb.");
        }

        File jsonFile;
        try {
            jsonFile = new File(resource.toURI()); // converts URL to File
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for users.json", e);
        }
        try {
            MapperUtil.getMapper().writerWithDefaultPrettyPrinter()
                    .writeValue(jsonFile, listOfUsers);
            return true;
        } catch (IOException e) {
            throw new IOException("COULDN'T WRITE THE NEW USER TO THE LIST");
        }

    }

    public void fetchBookingDetails() {
        user.printTicketDetails();
    }

    // cancel Booked tickets

    public boolean cancelBookedTicket(String ticketId) throws IOException {
        List<Ticket> bookedTickets = this.user.getBookedTickets();

        if (bookedTickets.isEmpty()) {
            return false;
        }
        // 1️⃣ Find the ticket we want to cancel first
        Ticket ticketToCancel = bookedTickets.stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst()
                .orElse(null);

        if (ticketToCancel == null) {
            return false;
        }

        // 2️⃣ Remove from user's booking
        bookedTickets = bookedTickets.stream()
                .filter(t -> !t.getTicketId().equals(ticketId))
                .collect(Collectors.toList());

        user.setBookedTickets(bookedTickets);

        // 3️⃣ Remove from all tickets in tickets.json
        List<Ticket> allTickets = Ticket.getAllTickets()
                .stream()
                .filter(t -> !t.getTicketId().equals(ticketId))
                .collect(Collectors.toList());

        URL pathToTicketsJson = ClassLoader.getSystemClassLoader()
                .getResource("localdb/tickets.json");

        File jsonFileForTickets;
        try {
            jsonFileForTickets = new File(pathToTicketsJson.toURI());
            MapperUtil.getMapper().writeValue(jsonFileForTickets, allTickets);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 4️⃣ Now revert seats back to available in trains.json
        TrainService trainService = new TrainService();
        List<Train> allTrains = trainService.getAllTrains();

        for (Train train : allTrains) {
            if (train.getTrainId().equals(ticketToCancel.getTrainId())) {
                for (Coach coach : train.getCoaches()) {
                    if (coach.getCoachNo() == ticketToCancel.getCoachNo()) {
                        for (Compartment compartment : coach.getListOfCompartment()) {
                            if (compartment.getCompartmentId() == ticketToCancel.getCompartmentId()) {
                                for (Seat[] row : compartment.getSeats()) {
                                    for (Seat seat : row) {
                                        if (ticketToCancel.getSeatNumber()
                                                .contains(seat.getSeatNo())) {
                                            seat.setIsAvailable(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // 5️⃣ Save back the updated trains
        URL pathToTrainsJson = ClassLoader.getSystemClassLoader()
                .getResource("localdb/trains.json");

        File jsonFileForTrains;
        try {
            jsonFileForTrains = new File(pathToTrainsJson.toURI());
            MapperUtil.getMapper().writeValue(jsonFileForTrains, allTrains);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    // Book a ticket method banana padega ek
    public boolean bookATicket(Ticket ticket) throws IOException {
        List<Ticket> allTickets = new ArrayList<>();
        InputStream in = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("localdb/tickets.json");

        try {
            allTickets = MapperUtil.getMapper().readValue(in, new TypeReference<List<Ticket>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        allTickets.add(ticket);
        URL resource = ClassLoader.getSystemClassLoader()
                .getResource("localdb/tickets.json");

        File jsonFile;

        try {
            jsonFile = new File(resource.toURI());
            MapperUtil.getMapper().writeValue(jsonFile, allTickets);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        // Now we need to update seats in trains.json
        TrainService trainService = new TrainService();
        List<Train> trains = trainService.getAllTrains();

        for (Train train : trains) {
            if (train.getTrainId().equals(ticket.getTrainId())) {
                for (Coach coach : train.getCoaches()) {
                    if (coach.getCoachNo() == ticket.getCoachNo()) {
                        for (Compartment compartment : coach.getListOfCompartment()) {
                            if (compartment.getCompartmentId() == ticket.getCompartmentId()) {

                                // Here seats is a 2D array
                                Seat[][] seats = compartment.getSeats();

                                for (Seat[] row : seats) { // iterate each row
                                    for (Seat seat : row) { // iterate each seat in the row
                                        if (ticket.getSeatNumber().contains(seat.getSeatNo())) {
                                            seat.setIsAvailable(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        // Finally, write back to trains.json
        URL trainsURL = ClassLoader.getSystemClassLoader()
                .getResource("localdb/trains.json");

        try {
            File trainsFile = new File(trainsURL.toURI());
            MapperUtil.getMapper().writeValue(trainsFile, trains);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
