package project;

public class Employee {
    private String name;
    private int employeeID;
    private Store location; 

    //creating employee information space
    public Employee(String name, int employeeID, Store location) {
        this.name = name;
        this.employeeID = employeeID;
        this.location = location;
    }

    public void processPayment(Order order) {
        System.out.println("Processing payment for order: " + order);
    }

    public Order createOrder(Customer customer, Vehicle vehicle) {
        Order order = new Order(customer, this, location);
        System.out.println("Order created for customer: " + customer.getName());
        return order;
    }

    public void discount(Order order) {
        System.out.println("Applying discount to order: " + order);
        order.setTotalAmountDue(order.getTotalAmountDue() * 0.8);
        System.out.printf("Discount applied. New total: $%.2f\n", order.getTotalAmountDue());
    }

    public boolean validateCustomer(Customer customer) {
        System.out.println("Validating customer driving record...");
        return customer.isValid();
    }

    // Get information of employee (employee ID, location, names)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Store getLocation() {
        return location;
    }

    public void setLocation(Store location) {
        this.location = location;
    }
}
