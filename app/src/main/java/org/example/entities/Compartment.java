package org.example.entities;

public class Compartment {

    private int compartmentId;
    private Seat[][] seats = new Seat[4][2];

    // Jackson needs a no-arg constructor
    public Compartment() {

    }

    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public void setSeats(Seat[][] seats) {
        this.seats = seats;
    }
}
