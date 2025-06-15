package org.example.entities;

enum TypeOfSeat {
    LEFT_LOWER, LEFT_MIDDLE, LEFT_UPPER, RIGHT_LOWER, RIGHT_MIDDLE, RIGHT_UPPER, SIDE_LOWER, SIDE_UPPER
}

enum Section {
    MAIN, SIDE
}

public class Seat {
    private int seatNo;
    private boolean isAvailable;
    private TypeOfSeat seatType;
    private Section section;

}
