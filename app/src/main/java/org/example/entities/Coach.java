package org.example.entities;

import java.util.List;

public class Coach {

    private int coachNo;
    private int noOfCompartments;
    private List<Compartment> listOfCompartment;

    // Jackson needs a no-arg constructor
    public Coach() {

    }

    public int getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(int coachNo) {
        this.coachNo = coachNo;
    }

    public int getNoOfCompartments() {
        return noOfCompartments;
    }

    public void setNoOfCompartments(int noOfCompartments) {
        this.noOfCompartments = noOfCompartments;
    }

    public List<Compartment> getListOfCompartment() {
        return listOfCompartment;
    }

    public void setListOfCompartment(List<Compartment> listOfCompartment) {
        this.listOfCompartment = listOfCompartment;
    }
}
