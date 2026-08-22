package storage;

import model.Ticket;

import java.io.*;
import java.util.ArrayList;

public class TicketFileStorage {

    private String fileName;

    public TicketFileStorage(String fileName) {
        this.fileName = fileName;
    }

    // Ghi danh sách vé xuống file
    public void save(ArrayList<Ticket> tickets) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Ticket ticket : tickets) {

                writer.write(
                        ticket.getTicketId()
                                + "|"
                                + ticket.getCustomer().getId()
                                + "|"
                                + ticket.getMovie().getMovieId()
                                + "|"
                                + ticket.getShowtime().getShowtimeId()
                                + "|"
                                + ticket.getSeat().getSeatNumber()
                                + "|"
                                + ticket.getFinalPrice()
                                + "|"
                                + ticket.getPaymentStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Lỗi khi ghi file: " + e.getMessage()
            );
        }
    }

    // Đọc dữ liệu từ file
    public ArrayList<String> load() {

        ArrayList<String> data = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line);
            }

        } catch (FileNotFoundException e) {

            // File chưa tồn tại
            return data;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Lỗi khi đọc file: " + e.getMessage()
            );
        }

        return data;
    }
}