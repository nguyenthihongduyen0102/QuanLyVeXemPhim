package repository;

import model.Customer;
import java.util.ArrayList;

public class CustomerRepository {
    private ArrayList<Customer> customers = new ArrayList<>();
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public Customer findById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }
    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }
}
