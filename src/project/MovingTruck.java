package project;

public class MovingTruck extends Vehicle implements Rentable {
    private double capacityInLBS;
    private boolean hasInsurance;
    private int daysRented;
    private boolean isAvailable;
    private double dailyRate;
    Customer currentOwner;

    public MovingTruck (String VIN, String brand, String model, int year, double mileage, Store location, double capacityInLBS, boolean hasInsurance){
        super(VIN, brand, model, year, mileage, location); //comes from parent class
        this.capacityInLBS = capacityInLBS;
        this.hasInsurance = hasInsurance;
        this.daysRented = 0; //defualt days rented
        this.isAvailable = true;
    }

    // Getters and Setters
    public double getCapacityInLBS() {
        return capacityInLBS;
    }

    public void setCapacityInLBS(double capacityInLBS) {
        this.capacityInLBS = capacityInLBS;
    }

    public boolean hasInsurance() {
        return hasInsurance;
    }

    public void setInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }
    public boolean isAvailable(){
        return this.isAvailable;
    }
    public void setAvailability(boolean availability) {
        this.isAvailable = availability;  // Set the availability status
    }
    public double getDailyRate() {
		return this.dailyRate;
	}
	
	public void setDailyRate(double newRate) {
		this.dailyRate = newRate;
	}
    public Customer getCurrentOwner() {
		return this.currentOwner;
	}
    public void setCurrentOwner(Customer newOwner) {
		this.currentOwner = newOwner;
	}
    //implement Rentable interface methods
    @Override
    public boolean rent(Customer customer, int days) {
        // Check if the truck is available
        if (!this.isAvailable()) {
            System.out.println("Sorry! This moving truck is already rented. Please wait until it's available again.");
            return false; // Cannot rent if not available
        }

        // Validate customer and rental period
        if (customer == null || days <= 0) {
            System.out.println("Invalid rental request. Please provide a valid customer and rental days.");
            return false; // Invalid rental request
        }

        // Set the current customer as the owner and update rental status
        this.setCurrentOwner(customer);
        this.setAvailability(false); // Set truck to unavailable
        this.setDaysRented(days); // Set the number of rental days

        System.out.println("Success! You have successfully rented the moving truck for " + days + " days.");
        return true; // Rental was successful
    }
    @Override
    public double calculateLateFees(int days){
        return days * 25.0; //$25 per extra late day 
    }
    @Override
    public void extendRental(int days){
        this.daysRented += days;
    }
    @Override
    public double generateRate(){
        double baseRate = 100.0; //base rate for a moving truck
        double insuranceFee;
        if (hasInsurance){
            insuranceFee = 0;
        }else{
            insuranceFee = 10.0;
        }
        return (baseRate + insuranceFee) * daysRented;
    }
    //start with returnTOLot










}
