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

    // Jackson needs a no-arg constructor
    public Seat() {

    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public TypeOfSeat getSeatType() {
        return seatType;
    }

    public void setSeatType(TypeOfSeat seatType) {
        this.seatType = seatType;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
