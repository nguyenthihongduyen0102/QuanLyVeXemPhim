package handler;

import model.Customer;
import model.CustomerType;
import service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerHandler {

    private final CustomerService customerService;
    private final Scanner scanner;

    // Constructor
    public CustomerHandler() {
        customerService = new CustomerService();
        scanner = new Scanner(System.in);
    }

    // =========================
    // MENU KHÁCH HÀNG
    // =========================
    public void showMenu() {

        int choice;

        do {
            System.out.println("\n========== QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Tìm khách hàng theo ID");
            System.out.println("3. Tìm khách hàng theo Email");
            System.out.println("4. Tìm khách hàng theo SĐT");
            System.out.println("5. Hiển thị danh sách khách hàng");
            System.out.println("6. Sửa khách hàng");
            System.out.println("7. Xóa khách hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    findCustomerById();
                    break;

                case 3:
                    findCustomerByEmail();
                    break;

                case 4:
                    findCustomerByPhone();
                    break;

                case 5:
                    displayCustomers();
                    break;

                case 6:
                    updateCustomer();
                    break;

                case 7:
                    deleteCustomer();
                    break;

                case 0:
                    System.out.println("Đã thoát!");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 0);
    }

    // =========================
    // THÊM KHÁCH HÀNG
    // =========================
    private void addCustomer() {

        System.out.println("\n===== THÊM KHÁCH HÀNG =====");

        System.out.print("Nhập ID: ");
        String id = scanner.nextLine();

        System.out.print("Nhập họ tên: ");
        String fullName = scanner.nextLine();

        System.out.print("Nhập SĐT: ");
        String phone = scanner.nextLine();

        System.out.print("Nhập Email: ");
        String email = scanner.nextLine();

        System.out.println("Các loại khách hàng:");

        CustomerType[] types = CustomerType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Chọn loại khách hàng: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("Loại khách hàng không hợp lệ!");
            return;
        }

        CustomerType customerType = types[typeChoice - 1];

        Customer customer = new Customer(
                id,
                fullName,
                phone,
                email,
                customerType
        );

        customerService.addCustomer(customer);
    }

    // =========================
    // TÌM THEO ID
    // =========================
    private void findCustomerById() {

        System.out.print("Nhập ID khách hàng: ");
        String id = scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng!");
        } else {
            System.out.println("Tìm thấy khách hàng:");
            System.out.println(customer);
        }
    }

    // =========================
    // TÌM THEO EMAIL
    // =========================
    private void findCustomerByEmail() {

        System.out.print("Nhập Email: ");
        String email = scanner.nextLine();

        Customer customer = customerService.findCustomerByEmail(email);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng!");
        } else {
            System.out.println("Tìm thấy khách hàng:");
            System.out.println(customer);
        }
    }

    // =========================
    // TÌM THEO SĐT
    // =========================
    private void findCustomerByPhone() {

        System.out.print("Nhập SĐT: ");
        String phone = scanner.nextLine();

        Customer customer = customerService.findCustomerByPhone(phone);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng!");
        } else {
            System.out.println("Tìm thấy khách hàng:");
            System.out.println(customer);
        }
    }

    // =========================
    // HIỂN THỊ KHÁCH HÀNG
    // =========================
    private void displayCustomers() {

        List<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Danh sách khách hàng đang trống!");
            return;
        }

        System.out.println("\n===== DANH SÁCH KHÁCH HÀNG =====");

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    // =========================
    // SỬA KHÁCH HÀNG
    // =========================
    private void updateCustomer() {

        System.out.print("Nhập ID khách hàng cần sửa: ");
        String id = scanner.nextLine();

        System.out.print("Nhập Email mới: ");
        String newEmail = scanner.nextLine();

        System.out.print("Nhập SĐT mới: ");
        String newPhone = scanner.nextLine();

        customerService.updateCustomer(
                id,
                newEmail,
                newPhone
        );
    }

    // =========================
    // XÓA KHÁCH HÀNG
    // =========================
    private void deleteCustomer() {

        System.out.print("Nhập ID khách hàng cần xóa: ");
        String id = scanner.nextLine();

        customerService.deleteCustomer(id);
    }
}