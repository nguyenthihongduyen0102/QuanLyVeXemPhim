import model.Customer;
import model.CustomerType;

public class TestCustomer {
    public static void main(String[] args) {

        Customer customer = new Customer(
                "KH001",
                "Pham Minh Thanh",
                "0348630168",
                "thanh@gmail.com",
                CustomerType.STUDENT
        );

        System.out.println(customer);
    }
}