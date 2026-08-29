package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Ticket;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketJsonStorage {

    private String fileName;

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public TicketJsonStorage(String fileName) {
        this.fileName = fileName;
    }

    public void save(ArrayList<Ticket> tickets) {

        ArrayList<TicketData> dataList = new ArrayList<>();

        for (Ticket ticket : tickets) {

            TicketData data = new TicketData(
                    ticket.getTicketId(),
                    ticket.getCustomer().getId(),
                    ticket.getMovie().getMovieId(),
                    ticket.getShowtime().getShowtimeId(),
                    ticket.getSeat().getSeatNumber(),
                    ticket.getFinalPrice(),
                    ticket.getPaymentStatus()
            );

            dataList.add(data);
        }

        try (FileWriter writer = new FileWriter(fileName)) {

            gson.toJson(dataList, writer);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Lỗi khi ghi file JSON: "
                            + e.getMessage()
            );
        }
    }

    public ArrayList<TicketData> load() {

        try (FileReader reader =
                     new FileReader(fileName)) {

            TicketData[] data =
                    gson.fromJson(reader, TicketData[].class);

            if (data == null) {
                return new ArrayList<>();
            }

            return new ArrayList<>(
                    Arrays.asList(data)
            );

        } catch (IOException e) {

            return new ArrayList<>();
        }
    }
}