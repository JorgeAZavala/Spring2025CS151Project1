package project;

import java.util.ArrayList;

public class Store {
	private Inventory inventory;
	private String city;
	private String state;
	private ArrayList<Employee> employees;
	double revenue;
	double profit;
	static int currentObjects;
	//Constructor
	public Store(String city, String state) throws ObjectOverLimitException {
        this.city = city;
        this.state = state;
        this.inventory = new Inventory();
        this.employees = new ArrayList<Employee>();
        this.revenue = 0;
        this.profit = 0;
        
        currentObjects++;
		
		if(currentObjects > 100) {
			throw new ObjectOverLimitException("store");
		}
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
	
	public ArrayList<Employee>  getEmployees() {
		return this.employees;
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
	
	public void payEmployees(double amount) {
		if (amount <= 0) {
			System.out.println("Enter a valid payment amount.");
			return;
		}
		
		for(int i = 0; i < employees.size(); i ++) {
			profit -= amount;
		}
	}
	
	public double calculateProfit() {
		
		return this.revenue - 10.57*(100-currentObjects);//10.57 taxes multiplied to number of vehicles sold
	}
	//Overridden From Object class 
	@Override 
	public String toString() {
		return this.city + ", " + this.getState() + " branch.";
	}
	
}
