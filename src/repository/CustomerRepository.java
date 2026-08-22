package repository;

import model.Customer;

import java.util.ArrayList;

public class CustomerRepository {

    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Khách hàng không hợp lệ"
            );
        }

        if (customer.getId() == null ||
                customer.getId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã khách hàng không hợp lệ"
            );
        }

        if (findById(customer.getId()) != null) {
            throw new IllegalArgumentException(
                    "Mã khách hàng đã tồn tại"
            );
        }

        customers.add(customer);
    }

    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Customer findById(String id) {

        if (id == null || id.isEmpty()) {
            return null;
        }

        for (Customer customer : customers) {

            if (customer.getId()
                    .equalsIgnoreCase(id)) {
                return customer;
            }
        }

        return null;
    }

    public boolean removeCustomer(String id) {

        Customer customer = findById(id);

        if (customer != null) {
            customers.remove(customer);
            return true;
        }

        return false;
    }

    public void setCustomers(ArrayList<Customer> customers) {

        if (customers == null) {
            this.customers = new ArrayList<>();
        } else {
            this.customers = new ArrayList<>(customers);
        }
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }
}