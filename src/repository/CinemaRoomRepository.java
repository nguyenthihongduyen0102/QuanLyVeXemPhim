package repository;

import model.CinemaRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CinemaRoomRepository {
    private final String filePath = "cinemaroom.txt";

    public CinemaRoomRepository() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo file cinemaroom.txt: " + e.getMessage());
        }
    }

    // Đọc danh sách phòng chiếu từ file cinemaroom.txt
    public List<CinemaRoom> findAll() {
        List<CinemaRoom> rooms = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int capacity = Integer.parseInt(parts[2].trim());

                    CinemaRoom room = new CinemaRoom(id, name, capacity);
                    rooms.add(room);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file cinemaroom.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng sức chứa phòng: " + e.getMessage());
        }

        return rooms;
    }

    // Lưu danh sách phòng chiếu vào file cinemaroom.txt
    public void saveAll(List<CinemaRoom> rooms) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (CinemaRoom room : rooms) {
                String line = String.format("%s,%s,%d",
                        room.getId(),
                        room.getName(),
                        room.getCapacity());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file cinemaroom.txt: " + e.getMessage());
        }
    }

    // Tìm phòng chiếu theo Mã ID
    public CinemaRoom findById(String id) {
        for (CinemaRoom room : findAll()) {
            if (room.getId().equalsIgnoreCase(id)) {
                return room;
            }
        }
        return null;
    }

    // Tìm phòng chiếu theo Tên phòng (Phục vụ ghép nối dữ liệu trong ShowtimeRepository)
    public CinemaRoom findByName(String name) {
        for (CinemaRoom room : findAll()) {
            if (room.getName().equalsIgnoreCase(name)) {
                return room;
            }
        }
        return null;
    }

    // Thêm phòng chiếu mới
    public void add(CinemaRoom room) {
        List<CinemaRoom> rooms = findAll();
        rooms.add(room);
        saveAll(rooms);
    }
}
