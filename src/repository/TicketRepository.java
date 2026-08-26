package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Customer;
import model.CustomerType;
import model.Movie;
import model.PaymentStatus;
import model.Seat;
import model.SeatStatus;
import model.SeatType;
import model.Ticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private final String file_json = "tickets.json";
    private final Gson gson;

    public TicketRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file tickets.json: " + e.getMessage());
        }
    }

    // Chuyển chuỗi seatType trong file sang enum SeatType
    private SeatType parseSeatType(String typeStr) {
        if (typeStr == null) return SeatType.Regularchair;
        String type = typeStr.trim().toUpperCase();
        switch (type) {
            case "VIP":
            case "VIPCHAIR":
                return SeatType.VIPchair;
            case "COUPLE":
            case "CUPLECHAIR":
                return SeatType.Cuplechair;
            case "NORMAL":
            case "REGULARCHAIR":
            default:
                return SeatType.Regularchair;
        }
    }

    // Format enum SeatType ra chuỗi chuẩn trong JSON
    private String formatSeatType(SeatType type) {
        if (type == null) return "NORMAL";
        switch (type) {
            case VIPchair:
                return "VIP";
            case Cuplechair:
                return "COUPLE";
            case Regularchair:
            default:
                return "NORMAL";
        }
    }

    // Đọc danh sách vé từ file tickets.json
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return tickets;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            JsonElement element = JsonParser.parseReader(br);
            if (!element.isJsonArray()) return tickets;

            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement item : jsonArray) {
                JsonObject obj = item.getAsJsonObject();

                String ticketId = obj.has("ticketId") ? obj.get("ticketId").getAsString() : "";
                String customerName = obj.has("customerName") ? obj.get("customerName").getAsString() : "";
                String movieTitle = obj.has("movieTitle") ? obj.get("movieTitle").getAsString() : "";
                String showtimeStr = obj.has("showtime") ? obj.get("showtime").getAsString() : "";
                String seatTypeStr = obj.has("seatType") ? obj.get("seatType").getAsString() : "NORMAL";
                double finalPrice = obj.has("finalPrice") ? obj.get("finalPrice").getAsDouble() : 0.0;

                String statusStr = obj.has("paymentStatus") ? obj.get("paymentStatus").getAsString() : "UNPAID";
                PaymentStatus paymentStatus = PaymentStatus.valueOf(statusStr.toUpperCase());

                // Khởi tạo các đối tượng chứa thông tin vừa đọc từ JSON
                Customer customer = new Customer("", customerName, "", "", CustomerType.NORMAL);
                Movie movie = new Movie("", movieTitle, 0, "");
                Seat seat = new Seat("", "", parseSeatType(seatTypeStr), SeatStatus.Booked, "");

                Ticket ticket = new Ticket(ticketId, customer, movie, null, seat, finalPrice, paymentStatus);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file tickets.json: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi dữ liệu trong file tickets.json: " + e.getMessage());
        }

        return tickets;
    }

    // Ghi danh sách vé xuống file tickets.json
    public void saveAll(List<Ticket> tickets) {
        JsonArray jsonArray = new JsonArray();

        for (Ticket t : tickets) {
            JsonObject obj = new JsonObject();
            obj.addProperty("ticketId", t.getTicketId());
            obj.addProperty("customerName", (t.getCustomer() != null) ? t.getCustomer().getFullName() : "");
            obj.addProperty("movieTitle", (t.getMovie() != null) ? t.getMovie().getTitle() : "");

            String showtimeStr = "";
            if (t.getShowtime() != null && t.getShowtime().getStartTime() != null && t.getShowtime().getEndTime() != null) {
                showtimeStr = t.getShowtime().getStartTime().toLocalTime() + " - " + t.getShowtime().getEndTime().toLocalTime();
            }
            obj.addProperty("showtime", showtimeStr);

            SeatType seatType = (t.getSeat() != null) ? t.getSeat().getTypeOfSeat() : SeatType.Regularchair;
            obj.addProperty("seatType", formatSeatType(seatType));
            obj.addProperty("finalPrice", t.getFinalPrice());
            obj.addProperty("paymentStatus", (t.getPaymentStatus() != null) ? t.getPaymentStatus().name() : "UNPAID");

            jsonArray.add(obj);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(jsonArray, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file tickets.json: " + e.getMessage());
        }
    }

    // Tìm vé theo Mã vé
    public Ticket findById(String ticketId) {
        for (Ticket t : findAll()) {
            if (t.getTicketId() != null && t.getTicketId().equalsIgnoreCase(ticketId)) {
                return t;
            }
        }
        return null;
    }

    // Đánh dấu trạng thái vé thành PAID
    public void updatePaymentStatusToPaid(String ticketId) {
        List<Ticket> tickets = findAll();
        for (Ticket t : tickets) {
            if (t.getTicketId() != null && t.getTicketId().equalsIgnoreCase(ticketId)) {
                t.markAsPaid();
                break;
            }
        }
        saveAll(tickets);
    }

    // Thêm vé mới
    public void add(Ticket ticket) {
        List<Ticket> tickets = findAll();
        tickets.add(ticket);
        saveAll(tickets);
    }
}
