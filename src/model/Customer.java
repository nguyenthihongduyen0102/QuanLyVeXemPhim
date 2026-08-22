package model;

public class Customer extends User{
    private CustomerType customerType;

    public Customer(){

    }

    public Customer(String id,
                    String fullName,
                    String phone,
                    String email,
                    CustomerType customerType) {
        super(id, fullName, phone, email);
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public boolean isStudent(){
        return customerType == CustomerType.STUDENT;
    }
    public boolean isVip(){
        return customerType == CustomerType.VIP;
    }

    public boolean isNormal(){
        return customerType == CustomerType.NORMAL;
    }
}
