package project;

import java.util.ArrayList;

public class Store {
	private Inventory inventory;
	private String city;
	private String state;
	private ArrayList<Employee> employees;
	double revenue;
	double profit;


	//Constructor

	public Store(String city, String state) {
        this.city = city;
        this.state = state;
        this.inventory = new Inventory();
        this.employees = new ArrayList<Employee>();
        this.revenue = 0;
        this.profit = 0;
	}
	//Setters and Getters
	public void setCity(String newCity) {
		this.city = newCity;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setState(String newState) {
		this.state = newState;
	}
	
	public String getState() {
		return this.state;
	}
	
	public double getRevenue() {
		return this.revenue;
	}
	
	public void setRevenue(double newRevenue) {
		this.revenue = newRevenue;
	}
	
	public double getProfit() {
		return this.profit;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	public void setProfit(double newProfit) {
		this.revenue = newProfit;
	}
	//Methods in Store
	public void addEmployee(Employee employee) {
		if(!this.employees.contains(employee)) {
			this.employees.add(employee);
			System.out.println("Employee was successfully added.");
		} else {
			System.out.println("This employee already exists in the system.");
		}
		
	}
	
	public void fireEmployees(Employee employee) {
		boolean success = this.employees.remove(employee);
		
		if(success) {
			System.out.println("Employee successfully removed from the system.");
		} else {
			System.out.println("Employee was not found at this location.");
		}
	}
	
	public void payEmployees(int amount) {
		if (amount <= 0) {
			System.out.println("Enter a valid payment amount.");
			return;
		}
		
		for(int i = 0; i < this.employees.size(); i ++) {
			profit -= amount;
		}
	}
	
	public double calculateProfit() {
		
		return this.getProfit();
	}
	
	//Overridden From Object class 
	@Override 
	public String toString() {
		return this.city + ", " + this.getState() + " branch.";
	}
	
}
