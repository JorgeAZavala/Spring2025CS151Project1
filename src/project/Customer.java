package project;

import java.util.ArrayList;

public class Customer {
    private String name;
    private int memberID;
    private boolean hasMembership;
    private boolean isValid;
    private double rentalDiscount;
    private ArrayList<Vehicle> vehicles;

    //creating information space
    public Customer(String name, int memberID, boolean hasMembership, boolean isValid, double rentalDiscount) {
        this.name = name;
        this.memberID = memberID;
        this.hasMembership = hasMembership;
        this.isValid = isValid;
        this.rentalDiscount = rentalDiscount;
        this.vehicles = new ArrayList<>();
    }

    // when customer pays we call employee to help 
    public void payOrder(Order order) {
        System.out.println("Processing payment for order: " + order);
    }

    public void chooseCar(Store store) {
        System.out.println("Choosing a car from store: " + store.getName());
    }

    public String receive() {
        return "Vehicle received.";
    }

    public String displayInfo() {
        return "Customer Name: " + name + ", Member ID: " + memberID + 
               ", Membership Status: " + (hasMembership ? "Active" : "Inactive");
    }

    //Getting information from customer about their status (Info/ member id's, payment, and driving record)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;

    public double getDiscount() {
        return memberDiscount;

    public void chooseCar(Store store) {
        System.out.println("Choosing a car from store: " + store.toString());
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public boolean hasMembership() {
        return hasMembership;
    }

    public void setHasMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getRentalDiscount() {
        return rentalDiscount;
    }

    public void setRentalDiscount(double rentalDiscount) {
        this.rentalDiscount = rentalDiscount;
    }
}
