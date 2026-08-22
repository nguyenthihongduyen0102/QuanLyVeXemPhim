package storage;

import model.Payment;

import java.io.*;
import java.util.ArrayList;

public class PaymentFileStorage {

    private String fileName;

    public PaymentFileStorage(String fileName) {
        this.fileName = fileName;
    }

    public void save(ArrayList<Payment> payments) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Payment payment : payments) {
                writer.write(payment.getPaymentId());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Lỗi khi ghi file: " + e.getMessage()
            );
        }
    }

    public ArrayList<String> load() {
        ArrayList<String> paymentIds = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                paymentIds.add(line);
            }

        } catch (FileNotFoundException e) {
            return paymentIds;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Lỗi khi đọc file: " + e.getMessage()
            );
        }

        return paymentIds;
    }
}