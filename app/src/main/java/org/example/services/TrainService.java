package org.example.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.example.entities.Train;
import org.example.entities.User;
import org.example.utils.MapperUtil;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TrainService {

    public static List<Train> listOfAllTrains;

    public List<Train> getAllTrains() throws IOException {

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("localdb/trains.json");
        try {
            return MapperUtil.getMapper().readValue(in, new TypeReference<List<Train>>() {
            });
        } catch (IOException e) {
            throw new IOException("couldn't read value from trains.json");
        }

    }

    public TrainService() throws IOException {

        this.listOfAllTrains = getAllTrains();

    }

    public List<Train> getTrains(String source, String destination, LocalDate dateOfTravel) {
        List<Train> filteredTrain = listOfAllTrains.stream().filter(

                (train) -> {
                    List<String> stations = new ArrayList<>();

                    LocalDate x = train.getDepartureTime().toLocalDate();
                    for (Entry<String, LocalDateTime> item : train.getStations().entrySet()) {

                        stations.add(item.getKey().toLowerCase());

                    }

                    int s = stations.indexOf(source.toLowerCase());
                    int d = stations.indexOf(destination.toLowerCase());
                    return s != -1 && d != -1 && s < d && x.equals(dateOfTravel);

                }

        ).collect(Collectors.toList());
        return filteredTrain;

    }

    public void getSeatsOfSelectedTrain(Train train) {
        for (Train t : TrainService.listOfAllTrains) {
            if (t.getTrainId().equals(train.getTrainId())) {
                t.printSeats();
            }
        }

    }

}
