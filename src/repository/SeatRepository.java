package repository;

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
    private final String file_txt = "seats.txt";

    public SeatRepository() {
        File file = new File(file_txt);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file seats.txt: " + e.getMessage());
        }
    }

    // Ánh xạ từ String trong file sang SeatType enum
    private SeatType parseSeatType(String typeStr) {
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

    // Ánh xạ từ SeatType enum ra dạng chuỗi để lưu file
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

    // Đọc danh sách ghế từ file seats.txt
    public List<Seat> findAll() {
        List<Seat> seats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_txt))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String seatCode = parts[0].trim();
                    String seatNumber = parts[1].trim();
                    SeatType typeOfSeat = parseSeatType(parts[2]);
                    SeatStatus seatStatus = SeatStatus.valueOf(parts[3].trim());
                    String roomId = parts[4].trim();

                    Seat seat = new Seat(seatCode, seatNumber, typeOfSeat, seatStatus, roomId);
                    seat.setSeatStatus(seatStatus); // Ghi đè trạng thái thực tế từ file
                    seats.add(seat);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file seats.txt: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi định dạng dữ liệu trong file seats.txt: " + e.getMessage());
        }

        return seats;
    }

    // Ghi toàn bộ danh sách ghế vào file seats.txt
    public void saveAll(List<Seat> seats) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_txt))) {
            for (Seat s : seats) {
                String line = String.format("%s,%s,%s,%s,%s",
                        s.getSeatCode(),
                        s.getSeatNumber(),
                        formatSeatType(s.getTypeOfSeat()),
                        s.getSeatStatus().name(),
                        s.getRoomId());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file seats.txt: " + e.getMessage());
        }
    }

    // Tìm ghế theo mã ghế
    public Seat findByCode(String seatCode) {
        for (Seat s : findAll()) {
            if (s.getSeatCode().equalsIgnoreCase(seatCode)) {
                return s;
            }
        }
        return null;
    }

    // Tìm danh sách ghế theo mã phòng
    public List<Seat> findByRoomId(String roomId) {
        List<Seat> result = new ArrayList<>();
        for (Seat s : findAll()) {
            if (s.getRoomId().equalsIgnoreCase(roomId)) {
                result.add(s);
            }
        }
        return result;
    }

    // Cập nhật trạng thái ghế
    public void updateSeatStatus(String seatCode, SeatStatus newStatus) {
        List<Seat> seats = findAll();
        for (Seat s : seats) {
            if (s.getSeatCode().equalsIgnoreCase(seatCode)) {
                s.setSeatStatus(newStatus);
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