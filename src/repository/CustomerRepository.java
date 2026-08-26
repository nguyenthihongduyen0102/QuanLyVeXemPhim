package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class CustomerRepository {
    private final String file_json = "customers.json";
    private final Gson gson;

    public CustomerRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file customers.json: " + e.getMessage());
        }
    }

    // Đọc file JSON kết hợp BufferedReader
    public List<Customer> findAll() {
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            Type listType = new TypeToken<ArrayList<Customer>>() {}.getType();
            List<Customer> customers = gson.fromJson(br, listType);
            return (customers != null) ? customers : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Lỗi đọc file customers.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Ghi file JSON kết hợp BufferedWriter
    public void saveAll(List<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(customers, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file customers.json: " + e.getMessage());
        }
    }
    // Tìm khách hàng theo mã ID
    public Customer findById(String id) {
        for (Customer c : findAll()) {
            if (c.getId() != null && c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
    // Tìm khách hàng theo số điện thoại
    public Customer findByPhone(String phone) {
        for (Customer c : findAll()) {
            if (c.getPhone() != null && c.getPhone().equals(phone)) {
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
