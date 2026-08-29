package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Seat;
import model.SeatStatus;
import model.SeatType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository {
    private final String file_json = "seats.json";
    private final Gson gson;

    public SeatRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file seats.json: " + e.getMessage());
        }
    }
    // Chuyển chuỗi từ JSON sang Enum SeatType tương ứng
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
    // Chuyển Enum SeatType sang chuỗi định dạng trong JSON
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

    // Đọc toàn bộ danh sách ghế từ file seats.json
    public List<Seat> findAll() {
        List<Seat> seats = new ArrayList<>();
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return seats;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            JsonElement element = JsonParser.parseReader(br);
            if (!element.isJsonArray()) return seats;

            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement item : jsonArray) {
                JsonObject obj = item.getAsJsonObject();

                String seatCode = obj.has("seatCode") ? obj.get("seatCode").getAsString() : "";
                String seatNumber = obj.has("seatNumber") ? obj.get("seatNumber").getAsString() : "";
                String typeStr = obj.has("typeOfSeat") ? obj.get("typeOfSeat").getAsString() : "NORMAL";
                String statusStr = obj.has("seatStatus") ? obj.get("seatStatus").getAsString() : "Available";
                String roomId = obj.has("roomId") ? obj.get("roomId").getAsString() : "";

                SeatType seatType = parseSeatType(typeStr);
                SeatStatus seatStatus = SeatStatus.valueOf(statusStr);

                Seat seat = new Seat(seatCode, seatNumber, seatType, seatStatus, roomId);
                seat.setSeatStatus(seatStatus); // Thiết lập chính xác trạng thái từ JSON
                seats.add(seat);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file seats.json: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu ghế: " + e.getMessage());
        }
        return seats;
    }

    // Ghi danh sách ghế xuống file seat.jdon
    public void saveAll(List<Seat> seats) {
        JsonArray jsonArray = new JsonArray();

        for (Seat s : seats) {
            JsonObject obj = new JsonObject();
            obj.addProperty("seatCode", s.getSeatCode());
            obj.addProperty("seatNumber", s.getSeatNumber());
            obj.addProperty("typeOfSeat", formatSeatType(s.getTypeOfSeat()));
            obj.addProperty("seatStatus", (s.getSeatStatus() != null) ? s.getSeatStatus().name() : "Available");
            obj.addProperty("roomId", s.getRoomId());
            jsonArray.add(obj);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(jsonArray, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file seats.json: " + e.getMessage());
        }
    }

    // Tìm ghế theo Mã ghế (seatCode)
    public Seat findByCode(String seatCode) {
        for (Seat seat : findAll()) {
            if (seat.getSeatCode() != null && seat.getSeatCode().equalsIgnoreCase(seatCode)) {
                return seat;
            }
        }
        return null;
    }

    // Lấy danh sách ghế cho một phòng chiếu cụ thể (roomId)
    public List<Seat> findByRoomId(String roomId) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : findAll()) {
            if (seat.getRoomId() != null && seat.getRoomId().equalsIgnoreCase(roomId)) {
                result.add(seat);
            }
        }
        return result;
    }

    // Đặt ghế (Cập nhật trạng thái ghế sang Booked)
    public void bookSeat(String seatCode) {
        List<Seat> seats = findAll();
        for (Seat seat : seats) {
            if (seat.getSeatCode() != null && seat.getSeatCode().equalsIgnoreCase(seatCode)) {
                seat.bookASeat();
                break;
            }
        }
        saveAll(seats);
    }

    // Thêm ghế mới
    public void add(Seat seat) {
        List<Seat> seats = findAll();
        seats.add(seat);
        saveAll(seats);
    }
}