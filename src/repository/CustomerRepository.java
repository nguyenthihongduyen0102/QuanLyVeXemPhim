package repository;

import model.Customer;
import model.CustomerType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CustomerRepository {
    private final String file_txt = "customers.txt";

    public CustomerRepository() {
        File file = new File(file_txt);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file customers.txt: " + e.getMessage());
        }
    }

    // Đọc danh sách khách hàng từ file
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_txt))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String fullName = parts[1].trim();
                    String phone = parts[2].trim();
                    String email = parts[3].trim();
                    CustomerType customerType = CustomerType.valueOf(parts[4].trim().toUpperCase());

                    Customer customer = new Customer(id, fullName, phone, email, customerType);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file customers.txt: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi định dạng loại khách hàng (CustomerType): " + e.getMessage());
        }

        return customers;
    }

    // Ghi toàn bộ danh sách khách hàng vào file
    public void saveAll(List<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_txt))) {
            for (Customer c : customers) {
                String line = String.format("%s,%s,%s,%s,%s",
                        c.getId(),
                        c.getFullName(),
                        c.getPhone(),
                        c.getEmail(),
                        c.getCustomerType().name());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file customers.txt: " + e.getMessage());
        }
    }

    // Tìm khách hàng theo Mã ID
    public Customer findById(String id) {
        for (Customer c : findAll()) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // Tìm khách hàng theo Số điện thoại
    public Customer findByPhone(String phone) {
        for (Customer c : findAll()) {
            if (c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    // Thêm khách hàng mới
    public void add(Customer customer) {
        List<Customer> customers = findAll();
        customers.add(customer);
        saveAll(customers);
    }
}
