import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    // Danh sách lưu khách hàng
    private List<Customer> customers;

    // Constructor
    public CustomerService() {
        customers = new ArrayList<>();
    }

    // THÊM KHÁCH HÀNG
    public boolean addCustomer(Customer customer) {

        if (customer == null) {
            System.out.println("Khách hàng không được để trống!");
            return false;
        }

        // Kiểm tra Email
        if (!isValidEmail(customer.getEmail())) {
            System.out.println("Email không hợp lệ!");
            return false;
        }

        // Kiểm tra số điện thoại
        if (!isValidPhone(customer.getPhone())) {
            System.out.println("Số điện thoại không hợp lệ!");
            return false;
        }

        // Kiểm tra ID đã tồn tại
        if (findCustomerById(customer.getId()) != null) {
            System.out.println("Mã khách hàng đã tồn tại!");
            return false;
        }

        customers.add(customer);

        System.out.println("Thêm khách hàng thành công!");
        return true;
    }

    // =========================
    // TÌM KHÁCH HÀNG THEO ID
    // =========================
    public Customer findCustomerById(String id) {

        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Customer customer : customers) {

            if (customer.getId().equalsIgnoreCase(id)) {
                return customer;
            }
        }

        return null;
    }

    // =========================
    // TÌM KHÁCH HÀNG THEO TÊN
    // =========================
    public List<Customer> searchByName(String name) {

        List<Customer> result = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return result;
        }

        for (Customer customer : customers) {

            if (customer.getFullName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                result.add(customer);
            }
        }

        return result;
    }

    // =========================
    // SỬA KHÁCH HÀNG
    // =========================
    public boolean updateCustomer(
            String id,
            String newEmail,
            String newPhone) {

        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return false;
        }

        // Kiểm tra Email mới
        if (!isValidEmail(newEmail)) {
            System.out.println("Email không hợp lệ!");
            return false;
        }

        // Kiểm tra SĐT mới
        if (!isValidPhone(newPhone)) {
            System.out.println("Số điện thoại không hợp lệ!");
            return false;
        }

        customer.setEmail(newEmail);
        customer.setPhone(newPhone);

        System.out.println("Cập nhật khách hàng thành công!");
        return true;
    }

    // =========================
    // XÓA KHÁCH HÀNG
    // =========================
    public boolean deleteCustomer(String id) {

        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return false;
        }

        customers.remove(customer);

        System.out.println("Xóa khách hàng thành công!");
        return true;
    }

    // =========================
    // HIỂN THỊ KHÁCH HÀNG
    // =========================
    public void displayCustomers() {

        if (customers.isEmpty()) {
            System.out.println("Danh sách khách hàng đang trống!");
            return;
        }

        System.out.println("===== DANH SÁCH KHÁCH HÀNG =====");

        for (Customer customer : customers) {

            System.out.println(
                    "ID: " + customer.getId()
                            + " | Tên: " + customer.getFullName()
                            + " | SĐT: " + customer.getPhone()
                            + " | Email: " + customer.getEmail()
            );
        }
    }

    // =========================
    // LẤY TOÀN BỘ KHÁCH HÀNG
    // =========================
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // =========================
    // KIỂM TRA EMAIL
    // =========================
    private boolean isValidEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    // =========================
    // KIỂM TRA SỐ ĐIỆN THOẠI
    // =========================
    private boolean isValidPhone(String phone) {

        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        return phone.matches("0[35789][0-9]{8}");
    }
}
//