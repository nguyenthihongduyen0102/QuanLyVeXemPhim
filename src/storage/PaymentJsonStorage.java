package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PaymentJsonStorage {

    private String fileName;

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public PaymentJsonStorage(String fileName) {
        this.fileName = fileName;
    }

    public void save(ArrayList<PaymentData> payments) {

        try (FileWriter writer =
                     new FileWriter(fileName)) {

            gson.toJson(payments, writer);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Lỗi khi ghi file JSON: "
                            + e.getMessage()
            );
        }
    }

    public ArrayList<PaymentData> load() {

        try (FileReader reader =
                     new FileReader(fileName)) {

            PaymentData[] payments =
                    gson.fromJson(
                            reader,
                            PaymentData[].class
                    );

            if (payments == null) {
                return new ArrayList<>();
            }

            return new ArrayList<>(
                    Arrays.asList(payments)
            );

        } catch (IOException e) {

            return new ArrayList<>();
        }
    }
}