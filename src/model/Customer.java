package model;

public class Customer extends User {

    private CustomerType customerType;

    public Customer(String id, String fullName, String phone,
                    String email, CustomerType customerType) {
        super(id, fullName, phone, email);
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}