package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Seat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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

    // Đọc danh sách ghế từ file JSON
    public List<Seat> findAll() {
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            Type listType = new TypeToken<ArrayList<Seat>>() {}.getType();
            List<Seat> seats = gson.fromJson(br, listType);
            return (seats != null) ? seats : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Lỗi đọc file seats.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Ghi toàn bộ danh sách ghế xuống file JSON
    public void saveAll(List<Seat> seats) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(seats, bw);
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

    // Lấy danh sách ghế theo Mã phòng chiếu (roomId)
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
                seat.bookASeat(); // Gọi phương thức bookASeat() từ class Seat
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