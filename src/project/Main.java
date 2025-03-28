package project;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static ArrayList<Store> allStores = new ArrayList<>();
	static Customer currentCustomer = null;
	static ArrayList<Employee> employees = new ArrayList<>();	
	public static void mainMenu() {
		System.out.println("1: Enter Business perspective");
		System.out.println("2: Enter Customer perspective");
		System.out.println("Enter 1 or 2 to pick: ");
		
		String input = scanner.nextLine();
		exit(input);
		try {
			validateNumInput(input,2);
			int picked = Integer.parseInt(input);
			if (picked == 1) {
				businessMenu();
			} else {
				createCustomer();
			}
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			mainMenu();
		} catch (ObjectOverLimitException e) {
			System.out.println(e.getMessage());
			mainMenu();
		}
		
	}
	//Prompts for Customer View
	
	public static void createCustomer() {
		System.out.println("Please enter your name:");
		String name = scanner.nextLine();
		exit(name);
		
		System.out.println("Are you a member? Answer: (y/n)");
		String isMember = scanner.nextLine();
		boolean membership = false;
		exit(isMember);
		
		try {
			validateInput(isMember,"y:n");
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			createCustomer();
		}
		if(isMember.equalsIgnoreCase("y")) {
			membership = true;
		}
		
		currentCustomer = new Customer(name, 12345, membership);
		
		customerMenu();
	}
	public static void customerMenu() {
		System.out.println("Welcome to our car business. Please select a location from the list below.");
		for(int i = 1; i <= allStores.size(); i++) {
			System.out.println(i + ": " + allStores.get(i-1).toString());
		}
		String input = scanner.nextLine();
		exit(input);
		
		try {
			validateNumInput(input,allStores.size());
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			customerMenu();
		}
		int picked = Integer.parseInt(input);
		storeMenu(allStores.get(picked-1));
		
	}
		
	public static void storeMenu(Store store) {
		System.out.println(store.toString());
		System.out.println("1: Search for Rentals");
		System.out.println("2: Search for Cars on Sale");
		System.out.println("3: Custom Search");
		System.out.println("4: View the cars you have selected");
		System.out.println("5: Exit the program");
		System.out.println("Enter a choice (1 - 5): ");
		
		
		String input = scanner.nextLine();
		exit(input);
		try {
			validateNumInput(input, 5);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			storeMenu(store);
		}
		
		int picked = Integer.parseInt(input);
		
		
		if(picked < 3) {
			displayCars(store, picked);
		} else if(picked == 3){
			searchCustomCar(store);
		} else if (picked == 4) {
			customerViewOwned();
		} else {
			exit("exit");
		}
	}
	public static void customerViewOwned() {
		if(currentCustomer.getRentedVehicles().size() == 0) {
			System.out.println("You don't seem to own any vehicles at this time.");
		} else {
			for(int i = 0; i < currentCustomer.getRentedVehicles().size(); i++) {
				System.out.println(i+1 +": " +currentCustomer.getRentedVehicles().get(i).toString());
			}
			System.out.println("Above are all the cars you own/rent.");
			System.out.println();
			System.out.println("1: Manage rentals");
			System.out.println("2: Return to Main menu");
			
			String input = scanner.nextLine();
			exit(input);
			
			try {
				validateNumInput(input,2);
				
			} catch(InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println();
				customerViewOwned();
			}
			int picked = Integer.parseInt(input);
			if(picked == 1) {
				customerManageRentals();
			} else if (picked == 2) {
				System.out.println("Taking you back to the main menu");
				customerMenu();
			}
		}
	}
	
	public static void customerManageRentals() {
		ArrayList<Vehicle> rentals = new ArrayList<>();
		System.out.println("1: To extend any existing rentals");
		System.out.println("2: To return a rental to a store");
		System.out.println("3: Return to main");
		System.out.println("Enter a choice (1-3): ");
		
		String input = scanner.nextLine();
		exit(input);
		
		try {
			validateNumInput(input,3);
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			customerManageRentals();
		}
		
		int picked = Integer.parseInt(input);
		if(picked == 1 || picked == 2) {
			for(Vehicle v: currentCustomer.getRentedVehicles()) {
				if(v instanceof Rentable) {
					rentals.add(v);
				}
			}
			for(int i = 0; i < rentals.size(); i++) { //Prints out all rentals in the list
				System.out.println(i + 1+": " + rentals.get(i).toString());
			}
			System.out.println("Pick which vehicle you'd like to edit");
			
			String input2 = scanner.nextLine();
			exit(input2);
			
			try {
				validateNumInput(input2, rentals.size());
				
			} catch(InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println();
				customerManageRentals();
			}
			
			int vIndex = Integer.parseInt(input2) - 1;
			Rentable rental = ((Rentable)rentals.get(vIndex));
				if(picked == 1) {
					System.out.println("How many days would you like to extend the rental? Enter days:");
					String days = scanner.nextLine();
					exit(days);
					
					try {
						validateNumInput(days, Integer.MAX_VALUE); //As long as the amount of days is a positive integer, it will be valid.
						
					} catch(InvalidInputException e) {
						System.out.println(e.getMessage());
						System.out.println();
						customerManageRentals();
					}
					int dayNum = Integer.parseInt(days);
					
					rental.setDaysRented(rental.getDaysRented() + dayNum);
					System.out.println("Successfully extend rental by " + dayNum + " days.");
					System.out.println("Sending you back to main menu!");
					System.out.println();
					customerMenu();
				} else if (picked == 2) {
					System.out.println("Which store would you like to return the car to?");
					System.out.println("Please select a location from the list below.");
					System.out.println();
					for(int i = 1; i <= allStores.size(); i++) {
						System.out.println(i + ": " + allStores.get(i-1).toString());
					}
					String store = scanner.nextLine();
					exit(store);
					
					try {
						validateNumInput(store,allStores.size());
						
					} catch(InvalidInputException e) {
						System.out.println(e.getMessage());
						System.out.println();
						customerMenu();
					}
					
					int storeIndex = Integer.parseInt(store) -1;
					
					
					rental.returnToLot(allStores.get(storeIndex));
					System.out.println("Successfully returned rental to " + allStores.get(storeIndex));
					System.out.println("Sending you back to main menu!");
					customerMenu();
				}
		} else if (picked == 3) {
			customerMenu();
		}
		
		
	}	
	
	public static void displayCars(Store store, int choice) {
		Inventory inventory = store.getInventory();
		ArrayList<Vehicle> allCars = inventory.getAllVehicles();
		if(choice == 1) {
			allCars = inventory.search("rental:True");
		} else {
			allCars = inventory.search("ForSale:True");
		}
		
		for(int i = 0; i < allCars.size(); i++) {
			System.out.println(i+1 +": " + allCars.get(i).toString());
		}
		
		System.out.println("Enter the number of the car you are interested in. If there is none, enter 0. ");
		String input = scanner.nextLine();
		exit(input);
		
		try {
			validateNumInput(input,allCars.size());
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			customerMenu();
		}
		
		int picked = Integer.parseInt(input);
		
		if(picked == 0) {
		System.out.println("Sorry you didn't find an option you liked. Returning you to the main menu.");
		System.out.println();
		customerMenu();
		}
		processOrder(allCars.get(picked-1));
	}
	
	public static void searchCustomCar(Store store) {
		System.out.println("We will ask you some questions to help find a car that fits your needs. Answer \"N/A\" if that aspect doesn't matter");
		System.out.println("What is the maximum mileage you would like?");
		String mileage = scanner.nextLine();
		exit(mileage);
		
		System.out.println("What is the minimum year you would like?");
		String year = scanner.nextLine();
		exit(year);
		
		System.out.println("What brand would you like?");
		String brand = scanner.nextLine();
		exit(brand);
		
		System.out.println("What model would you like?");
		String model = scanner.nextLine();
		exit(model);
		
		System.out.println("Are you looking to buy or rent? Enter: buy or rent");
		String purchaseType = scanner.nextLine();
		exit(purchaseType);
		
		Inventory current = store.getInventory();
		ArrayList<Vehicle> matchingCars = current.getAllVehicles();
		
		//Takes the intersection of the entire inventory and the vehicles that match the user's specifications
		if(!mileage.equalsIgnoreCase("n/a")) {
			matchingCars.retainAll(current.search("mileage:"+ mileage));
		}
		if(!year.equalsIgnoreCase("n/a")) {
			matchingCars.retainAll(current.search("year:"+ year));
		}
		if(!brand.equalsIgnoreCase("n/a")) {
			matchingCars.retainAll(current.search("brand:"+ brand));
		}
		if(!model.equalsIgnoreCase("n/a")) {
			matchingCars.retainAll(current.search("model:"+ model));
		}
		if(!purchaseType.equalsIgnoreCase("n/a")) {
			if(purchaseType.equalsIgnoreCase("buy")) {
				matchingCars.retainAll(current.search("forsale:true"));
			} else {
				matchingCars.retainAll(current.search("rental:true"));
			}
		}
		//Prints the list of cars that match the user's specifications.
		if(matchingCars.size() == 0) {
			System.out.println("Unfortunately, we have no cars that match your specifications. Please try again with other specifications.");
			System.out.println("Redirecting you back to the main menu!");
			System.out.println();
			customerMenu();
		} else {
			
			for(int i = 0; i < matchingCars.size(); i++) {
				System.out.println(i+1 +": " + matchingCars.get(i).toString());
			}
		
			System.out.println("Enter the number of the car you are interested in. If there is none, enter 0. ");
			String input = scanner.nextLine();
			exit(input);
			
			try {
				validateNumInput(input,matchingCars.size());
				
			} catch(InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println();
				customerMenu();
			}
			int picked = Integer.parseInt(input);
		
			if(picked != 0 ) {
				processOrder(matchingCars.get(picked-1));
			} else {
				System.out.println("Sorry you didn't find an option you liked. Returning you to the main menu.");
				System.out.println();
				customerMenu();
			}
		}
	}

	//Jorge - Call back to the classes for logic and excutes in the main
	public static void createOrder(Employee employee, Store store) {
   		  System.out.println("Getting an order ready");
    	          Order order = new Order(currentCustomer, employee, store);
  		  System.out.println("Ready to start your order" + employee.getName());
	}

	public static void validateCustomer(Employee employee) {
   		 System.out.println("Getting customers details");
   		 if (currentCustomer != null && currentCustomer.activeMembership()) {
   	         System.out.println("Customer " + currentCustomer.getName() + " is good to go. Authorized by " + employee.getName());
   	 } else {
       		 System.out.println("Customer was not able to be vertified for rentals or purchases. Try again at another time.");
   	 }
	}
	
	public static void Discount(Employee employee) {
 		  System.out.println("Getting your discount");
		  double discountRate = employee.getDiscountRate(); 
  	          System.out.println(employee.getName() + "I was able to get your discount" + discountRate);
	}
	public static void processPayment(Employee employee) {
 		   System.out.println("Getting payment ready");
    		  System.out.println("You are all set to go. Authorized by " + employee.getName());
	}
	//Jorge ^^
	
	
	public static void processOrder(Vehicle vehicle) {
		Order order = new Order(currentCustomer, vehicle.getLocation().getEmployees().get(0), vehicle.getLocation());
		order.addToOrder(vehicle);
		
		if(vehicle instanceof Rentable) {
			
			System.out.println("You have selected a rental. How many days would you like to rent it? Enter number of days: ");
			String input = scanner.nextLine();
			exit(input);
			
			try {
				validateNumInput(input,Integer.MAX_VALUE);
				
			} catch(InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println();
				customerMenu();
			}
			
			int days = Integer.parseInt(input);
			((Rentable)vehicle).setDaysRented(days);
			
			try {
				((Rentable)vehicle).rent(currentCustomer, days);
				order.calculateAmountDue();
			} catch (RentalAvailibilityException e) {
				System.out.println(e.getMessage());
				System.out.println("Seems there was an error. Sending you back to the main menu.");
				System.out.println();
				customerMenu();
			}
		} else {
			System.out.println("You have selected to purchase a " + vehicle.toString());
			System.out.println("Would you like to negotiate? (y/n)");
			String input = scanner.nextLine();
			exit(input);
			try {
				validateInput(input,"y:n");
				
			} catch(InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println();
				customerMenu();
			}
			if(input.equalsIgnoreCase("y")) {
				
				boolean success = false;
				while(!success) {
					System.out.println("What price do you offer? The car is valued at " + ((Car) vehicle).appraiseValue());
					String offer = scanner.nextLine();
					double offerNum = Double.parseDouble(offer);
					exit(offer);
					
					success = ((Car) vehicle).negociatePrice(offerNum);
					if(success) {
						order.setAmountDue(offerNum);
					}
				}
			} else {
				order.calculateAmountDue();
			}
			
		}
		System.out.println(order.toString());
		if(currentCustomer.activeMembership()) {
		System.out.println("All members have access to a 20% discount, would you like to use your discount? (y/n)");
		String input = scanner.nextLine();
		exit(input);
		try {
			validateInput(input,"y:n");
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			customerMenu();
		}
		
			if(input.equalsIgnoreCase("y")) {
				Store store = vehicle.getLocation();
				Employee e = store.getEmployees().get((int) Math.random()* store.getEmployees().size()); //Picked random employee from store
				e.Discount(order, .2);
			}
			
		}
		System.out.println("Please enter your 16 digit credit card number to pay for the order. (No Spaces)");
		String cardnum = scanner.nextLine();
		exit(cardnum);
		if(cardnum.length() == 16 && cardnum.matches("\\d+")) {
			currentCustomer.getRentedVehicles().add(vehicle);
			vehicle.getLocation().getInventory().getAllVehicles().remove(vehicle);
			order.acceptPayment();
			
		} else {
			System.out.println("We could not validate this purchase. Please try again later.");
		}
		System.out.println("Redirecting you to our main menu!");
		System.out.println();
		customerMenu();
		
	}

	
	// Jorge - getting a list, creating space for the order, setting /.add, prompt customer
	public static void payment() {
  		System.out.println("Getting payment.");
		Store store = allStores.get(0); 
		ArrayList<Employee> employees = store.getEmployees(); 
		Employee employee = employees.get(0); 
		Customer customer = currentCustomer;
		Order order = new Order(customer, employee, store); 
		Inventory inventory = store.getInventory(); 
		ArrayList<Vehicle> vehicles = inventory.getAllVehicles(); 
		Vehicle vehicle = vehicles.get(0); 
		order.addToOrder(vehicle); 
	    	currentCustomer.pay(order); 
	}

	// prompt customer, scanning for user input, catch incase nothing is in place 
	public static void rentVehicleFromStore() {
	   	 System.out.println("What vehicle model are you looking for today?");
	  	 String model = scanner.nextLine();
	   	 exit(model);
	
	    try {
	        currentCustomer.rentVehicle(allStores.get(0), model);
	        System.out.println("Here is your vehicle. Drive responsability");
	    } catch (IllegalArgumentException e) {
	        System.out.println("Could not get all the information. Try again.");
	    }
	}

	//checking for vaild input and returning information
	public static void displayRentedVehicles() {
	    ArrayList<Vehicle> rentedVehicles = currentCustomer.getRentedVehicles();

	    if (rentedVehicles.isEmpty()) {
   		    System.out.println("You haven't rented a vehicle yet.");
	    } else {
    		    System.out.println("Here is what you have rented.");
	    for (Vehicle vehicle : rentedVehicles) {
 		    System.out.println(vehicle);
			}   	 	
	    }
	}
	//Jorge ^^^
	
	//Prompts for Business View
	public static void businessMenu() throws ObjectOverLimitException {
		System.out.println("Choose which location to manage below.");
		for(int i = 0; i < allStores.size(); i++) {
			System.out.println(i+1 + ": " + allStores.get(i).toString());
		}
		System.out.println(allStores.size() + 1 +": Create a new location");
		String input = scanner.nextLine();
		
		try {
			validateNumInput(input, allStores.size() + 1);
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			businessMenu();
		}
		int picked = Integer.parseInt(input);
		if(picked < allStores.size()) {
		businessStoreMenu(allStores.get(picked-1));
		} else {
			createStore();
		}
	}
	
	public static void createStore() {
		System.out.println("What city is this store located at?");
		String city = scanner.nextLine();
		
		System.out.println("What state is this store located at?");
		String state = scanner.nextLine();
		boolean exists = false;
		for(Store s: allStores) {
			if (s.getCity().equalsIgnoreCase(city) && s.getState().equalsIgnoreCase(state)) {
				exists = true;
			}
		}
		if(exists) {
			System.out.println("That store seems to exist already in the system. Failed to create the new store.");
			System.out.println();
			try {
				businessMenu();
			} catch (ObjectOverLimitException e) {
				System.out.println(e.getMessage());
				mainMenu();
			}
		} else {
			try {
				allStores.add(new Store(city, state));
			} catch (ObjectOverLimitException e) {
				System.out.println(e.getMessage());
				mainMenu();
			} 
			System.out.println("Sucessfully created the new " + allStores.get(allStores.size()-1).toString());
			
			System.out.println("Returning you to the main menu");
			mainMenu();
		}
	}
	public static void businessStoreMenu(Store store) throws ObjectOverLimitException {
		System.out.println(store.toString());
		System.out.println("1: Manage Vehicles");
		System.out.println("2: Manage Employees");
		System.out.println("3: Exit the program");
		System.out.println("Enter a choice (1 - 3): ");
		
		
		String input = scanner.nextLine();
		
		try {
			validateNumInput(input,3);
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			businessMenu();
		}
		int picked = Integer.parseInt(input);
		if(picked == 1) {
			manageVehiclesMenu();
		}else if(picked == 2) {
			manageEmployeeMenu();
		}else if(picked == 3) {
			exit("exit");
		}
		
	}
	
	public static void manageVehiclesMenu() throws ObjectOverLimitException {
	    System.out.println("1: Add Regular Vehicle");
	    System.out.println("2: Add Rentable Vehicle");
	    System.out.println("3: Delete Vehicle");
	    System.out.println("Enter a choice (1 - 3): ");

	    String input = scanner.nextLine();
	    
	    try {
			validateNumInput(input,3);
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			businessMenu();
		}
	    int picked = Integer.parseInt(input);

	    if (picked == 1) {
	        // Adding a regular vehicle
	        System.out.println("What is the vehicle's VIN?");
	        String vin = scanner.nextLine();
	        System.out.println("What is the vehicle's brand?");
	        String brand = scanner.nextLine();
	        System.out.println("What is the vehicle's model?");
	        String model = scanner.nextLine();
	        System.out.println("Which year is this vehicle from?");
	        int year = scanner.nextInt();
	        System.out.println("What is the vehicle's mileage?");
	        double mil = scanner.nextDouble();
	        scanner.nextLine();  // Consume the newline character left by nextDouble()
	        System.out.println("Where do you want the vehicle to be stored?");
	        String storeName = scanner.nextLine();

	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	                Store store = allStores.get(i);
	                Vehicle vehicle = new Car(vin, brand, model, year, mil, store);
	                allStores.get(i).getInventory().addVehicle(vehicle);
	            }
	        }
	    } 
	    else if (picked == 2) {
	        // Add a rentable vehicle
	        System.out.println("What is the vehicle's VIN?");
	        String vin = scanner.nextLine();
	        System.out.println("What is the vehicle's brand?");
	        String brand = scanner.nextLine();
	        System.out.println("What is the vehicle's model?");
	        String model = scanner.nextLine();
	        System.out.println("Which year is this vehicle from?");
	        int year = scanner.nextInt();
	        System.out.println("What is the vehicle's mileage?");
	        double mil = scanner.nextDouble();
	        scanner.nextLine();  
	        System.out.println("Where do you want the vehicle to be stored?");
	        String storeName = scanner.nextLine();
	        System.out.println("What is the rental price per day?");
	        

	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	                Store store = allStores.get(i);
	                RentalCar rentableVehicle = new RentalCar(vin, brand, model, year, mil, store);
	                allStores.get(i).getInventory().addVehicle(rentableVehicle);
	            }
	        }
	    } 
	    else if (picked == 3) {
	    	System.out.println("Enter the store where the vehicle is located:");
	        String storeName = scanner.nextLine();

	        Store storeToDeleteFrom = null;
	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	                storeToDeleteFrom = allStores.get(i);
	                break;
	            }
	        }

	        if (storeToDeleteFrom == null) {
	            System.out.println("Store not found!");
	            return;  // Exit if store not found
	        }

	        System.out.println("Enter the vehicle's brand to delete:");
	        String brand = scanner.nextLine();
	        System.out.println("Enter the vehicle's model to delete:");
	        String model = scanner.nextLine();

	        boolean vehicleFound = false;
	        for (Vehicle vehicle : storeToDeleteFrom.getInventory().getAllVehicles()) {
	            if (vehicle.getBrand().equalsIgnoreCase(brand) && vehicle.getModel().equalsIgnoreCase(model)) {
	                storeToDeleteFrom.getInventory().getAllVehicles().remove(vehicle);
	                System.out.println("Vehicle successfully deleted!");
	                vehicleFound = true;
	                break;
	            }
	        }

	        if (!vehicleFound) {
	            System.out.println("Vehicle not found with the specified brand and model.");
	        }
	    }
	}

	
	public static void manageEmployeeMenu() throws ObjectOverLimitException {
		System.out.println("1: Add Employees");
		System.out.println("2: Delete Employees");
		System.out.println("3: Pay Employees");
		System.out.println("4: Calculate Profit");
		System.out.println("Enter a choice (1- 4): ");
		
		
		String input = scanner.nextLine();
		try {
			validateNumInput(input,4);
			
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println();
			businessMenu();
		}  
		int picked = Integer.parseInt(input);

	    if (picked == 1) {
			System.out.println("Please enter the Employee's Name: ");
	        String name = scanner.nextLine();
	        System.out.println("Please enter the Employee's ID: ");
	        int id = scanner.nextInt();
	        scanner.nextLine(); 
	        System.out.println("Please enter the Store Name where the Employee works: ");
	        String storeName = scanner.nextLine();
	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	            	Store store = allStores.get(i);
	            	employees.add(new Employee(name, id, store));
	            	System.out.println("Employee added succesfully!!");
	            	mainMenu();
	            }
	      
	        }
	    }else if(picked == 2) {
	    	System.out.println("Please enter the Employee's Name: ");
	        String name = scanner.nextLine();
	        for (Employee employee: employees) {
	        	if(employee.getName().equalsIgnoreCase(name)){
	        		employees.remove(employee);
	        		System.out.println("Employee deleted succesfully!!");
	        		mainMenu();
	        	}
	        }
	    }else if(picked == 3) {
	    	System.out.println("Please enter the Store Name for which you want the profit: ");
	        String storeName = scanner.nextLine();
	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	            	Store store = allStores.get(i);
	            	store.payEmployees(store.calculateProfit()/employees.size());
	            	mainMenu();
	            }
	        }
	    }else if(picked == 4) {
	    	System.out.println("Please enter the Store Name for which you want the profit: ");
	        String storeName = scanner.nextLine();
	        for (int i = 0; i < allStores.size(); i++) {
	            if (allStores.get(i).getCity().equalsIgnoreCase(storeName)) {
	            	Store store = allStores.get(i);
	            	store.calculateProfit();
	            	mainMenu();
	            }
	        }
	    }    
        
		
	}

	
	//Exit method
	public static void exit(String input) { //Takes in a input
		if(!input.equalsIgnoreCase("exit")) { //Only exits if that input matches "exit". Ensures that at any given time, a user can type "exit" to close the program.
			return;								
		}
		System.out.println("Exiting the Program!");
		scanner.close();
		System.exit(0);
	}
	
	//Input Validation methods
	public static void validateInput(String s, String expected) throws InvalidInputException {
		String[] expectedInputs = expected.split(":"); //Separate all valid inputs by :
		for(String possible: expectedInputs) {
			if(s.equalsIgnoreCase(possible)) {
				return;
			}
		} 
		throw new InvalidInputException(s); // If it reached this, then the input is not in expected values.
	}
	
	public static void validateNumInput(String s, int range) throws InvalidInputException {
		if(s.matches("\\d+")) {  //Safe to parse int because only numbers
			int sInt = Integer.parseInt(s);
			
			if(sInt > range) { //If not in range of expected values, throw the error
				throw new InvalidInputException(s);
			}
		} else {
		throw new InvalidInputException(s); //If not a number is an error for sure.
		}
	}
    public static void main(String[] args) throws ObjectOverLimitException {
    	//Preloading the system with Generic values
        try {
        	allStores.add(new Store("San Jose", "California" ));
        	allStores.add(new Store("San Diego","California"));
        	allStores.add(new Store("San Francisco", "California"));
        	Employee employee1 = new Employee("Vincent", 100, allStores.get(0));
            employees.add(employee1);
            
            Employee employee2 = new Employee("Harry", 101, allStores.get(1));
            employees.add(employee2);
            
            Employee employee3 = new Employee("Joe", 102, allStores.get(2));
            employees.add(employee3);
            
            allStores.get(0).getEmployees().add(employee1);
            allStores.get(1).getEmployees().add(employee2);
            allStores.get(2).getEmployees().add(employee3);
            allStores.get(0).getInventory().addVehicle(new Car("ABCD1234", "Toyota", "Prius", 2012, 10100.0, allStores.get(0)));
			allStores.get(0).getInventory().addVehicle(new Car("123123C4", "Toyota", "Highlander", 2015, 10400.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new Car("ABDDZ114", "Honda", "Civic", 2009, 10300.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new Car("LL22441A", "Honda", "Accord", 2017, 10080.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new Car("DC45A1B9", "Toyota", "Corrolla", 2011, 19000.0, allStores.get(0)));
		        
		     
		    allStores.get(1).getInventory().addVehicle(new Car("XYZ98765", "Ford", "Focus", 2018, 12000.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new Car("LMN45678", "Chevrolet", "Malibu", 2014, 13500.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new Car("GHJ23489", "Nissan", "Altima", 2016, 9800.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new Car("QWE56473", "Hyundai", "Elantra", 2020, 8700.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new Car("ASD45896", "Kia", "Sorento", 2013, 15000.0, allStores.get(1)));

		    allStores.get(2).getInventory().addVehicle(new Car("ZXCV6789", "Tesla", "Model 3", 2021, 5000.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new Car("POIU9876", "BMW", "X5", 2019, 11000.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new Car("LKJH5432", "Mercedes", "C-Class", 2017, 8700.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new Car("MNBV1243", "Subaru", "Outback", 2015, 9600.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new Car("YUIO8765", "Jeep", "Wrangler", 2018, 14300.0, allStores.get(2)));
		       
		    allStores.get(0).getInventory().addVehicle(new RentalCar("ABCD1235", "Toyota", "Prius", 2012, 10170.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new RentalCar("123123C5", "Toyota", "Highlander", 2015, 1070.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new RentalCar("ABDDZ115", "Honda", "Civic", 2009, 11030.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new RentalCar("LL224415", "Honda", "Accord", 2017, 150080.0, allStores.get(0)));
		    allStores.get(0).getInventory().addVehicle(new RentalCar("DC45A1B5", "Toyota", "Corrolla", 2011, 51000.0, allStores.get(0)));
		        
		    allStores.get(1).getInventory().addVehicle(new RentalCar("XYZ98766", "Ford", "Focus", 2018, 1200.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new RentalCar("LMN45676", "Chevrolet", "Malibu", 2014, 18300.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new RentalCar("GHJ23486", "Nissan", "Altima", 2016, 98800.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new RentalCar("QWE56476", "Hyundai", "Elantra", 2020, 68700.0, allStores.get(1)));
		    allStores.get(1).getInventory().addVehicle(new RentalCar("ASD45896", "Kia", "Sorento", 2013, 12500.0, allStores.get(1)));

		    allStores.get(2).getInventory().addVehicle(new RentalCar("ZXCV6787", "Tesla", "Model 3", 2021, 50200.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new RentalCar("POIU9877", "BMW", "X5", 2019, 11900.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new RentalCar("LKJH5437", "Mercedes", "C-Class", 2017, 87080.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new RentalCar("MNBV1247", "Subaru", "Outback", 2015, 96090.0, allStores.get(2)));
		    allStores.get(2).getInventory().addVehicle(new RentalCar("YUIO8767", "Jeep", "Wrangler", 2018, 147300.0, allStores.get(2)));
		        
		    allStores.get(0).getInventory().addVehicle(new MovingTruck("A9CD1235", "Ford", "Transit", 2012, 10170.0, allStores.get(0), 1500, true));
		    allStores.get(1).getInventory().addVehicle(new MovingTruck("193123C5", "GMC", "Savana", 2015, 1070.0, allStores.get(1), 700, false));
		    allStores.get(2).getInventory().addVehicle(new MovingTruck("A9DDZ115", "Chevrolet", "Express", 2009, 11030.0, allStores.get(2), 950, true));        
             

		} catch (ObjectOverLimitException e) {
			e.printStackTrace();
		}
      
        //Prompt User to begin the program.
        mainMenu();
               
    }
}
