package project;

import java.util.ArrayList;

public class RentalCar extends Vehicle implements Rentable {
	private boolean isAvailable;
	private double dailyRate;
	private int daysRented;
	Customer currentOwner;
	static int currentObjects = 0;
	
	//Constructor Declaration
	public RentalCar(String VIN, String brand, String model, int year, double mileage, Store location) throws ObjectOverLimitException {
		super(VIN, brand, model, year, mileage, location);
		this.isAvailable = true;
		this.dailyRate = this.generateRate();
		this.daysRented = 0;
		this.currentOwner = null;
		
		currentObjects++;
		
		if(currentObjects > 100) {
			throw new ObjectOverLimitException("rental car");
		}
	}
	
	//Setters and Getters
	public boolean isAvailable() {
		return this.isAvailable;
	}
	
	public void setAvailibility(boolean availibility) {
		this.isAvailable = availibility;
	}
	
	public double getDailyRate() {
		return this.dailyRate;
	}
	
	public void setDailyRate(double newRate) {
		this.dailyRate = newRate;
	}
	
	public int getDaysRented() {
		return this.daysRented;
	}
	
	public void setDaysRented(int numDays) {
		this.daysRented = numDays;
	}
	
	public Customer getCurrentOwner() {
		return this.currentOwner;
	}
	
	public void setCurrentOwner(Customer newOwner) {
		this.currentOwner = newOwner;
	}
	
	//Methods from Rentable Interface
	@Override
	public double generateRate() {
		double baseRate = 50;
		String[] luxuryBrands = {"Lexus","Mercedes", "BMW", "Porsche"};
		
		if(this.getYear() < 2010) {
			baseRate -= 20;
		} 
		
		for(String brand: luxuryBrands) {
			if(this.getBrand().equals(brand)) {
				baseRate *= 1.15;
				break;
			}
		}
		
		return baseRate;
	}

	@Override
	public void returnToLot(Store store) {
		Inventory inventory = store.getInventory();
		
		inventory.addVehicle(this);
		this.setAvailibility(true);
		this.setDaysRented(0);
	}

	@Override
	public boolean rent(Customer customer, int days) {
		if(!this.isAvailable()) {
			System.out.println("Sorry! This rental car has already rented, please wait " + this.getDaysRented() + " and try again later. ");
			return false;
		}
		
		this.setCurrentOwner(customer);
		this.setAvailibility(false);
		this.setDaysRented(days);
		
		System.out.println("Success! You have successfully rented out the car.");
		return true;
	}

	@Override
	public double calculateLateFees(int daysOverdue) {
		double lateFee = daysOverdue * this.getDailyRate();
		
		if(daysOverdue >= 7) {
			lateFee *= 1.1;
		}
		
		return lateFee;
	}

	@Override
	public void extendRental(int days) {
		if(days < 0) {
		System.out.println("Failed to extend rental. Please enter a positive integer for days.");
		}
		
		this.setDaysRented(this.getDaysRented() + days);
	}
	
	// Overridden methods from Vehicle Superclass
	@Override
	public void compareValue(Vehicle other) {
		if(other instanceof Rentable) {
			double currentValue = this.getDailyRate();
			double otherValue = ((Rentable)other).generateRate();
			
			if(currentValue > otherValue) {
				System.out.println(this.toString() + "is of better value.");
			} else {
				System.out.println(other.toString() + "is of better value.");
			}
		} else {
			System.out.println("You can't compare a rental to a non-rental car.");
		}
	}
	
	//To String
	@Override
	public String toString() {
		return this.getYear() + " " + this.getBrand() + " " + this.getModel() + ", Rental Rate: " + this.getDailyRate();
	}
}
