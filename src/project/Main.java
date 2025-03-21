package project;

public class Main {
    public static void main(String[] args) {
        Store store = new Store("California", "SanJose");
        Employee employee = new Employee("Vincent", 100, store);
        Customer customer = new Customer("Kendrick", 85845, true, true, 0.1);
        Vehicle vehicle = new Vehicle("VIN88754", "Toyota", "Corolla", 2022, 10000);
	    
    }
}
