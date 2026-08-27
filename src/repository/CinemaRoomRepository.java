package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.CinemaRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CinemaRoomRepository {
    private final String file_json = "cinemaroom.json";
    private final Gson gson;

    public CinemaRoomRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file cinemaroom.json: " + e.getMessage());
        }
    }

    // Đọc danh sách phòng chiếu từ file JSON
    public List<CinemaRoom> findAll() {
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            Type listType = new TypeToken<ArrayList<CinemaRoom>>() {}.getType();
            List<CinemaRoom> rooms = gson.fromJson(br, listType);
            return (rooms != null) ? rooms : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Lỗi đọc file cinemaroom.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Ghi toàn bộ danh sách phòng chiếu xuống file JSON
    public void saveAll(List<CinemaRoom> rooms) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(rooms, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file cinemaroom.json: " + e.getMessage());
        }
    }

    // Tìm phòng chiếu theo Mã phòng (ID)
    public CinemaRoom findById(String id) {
        for (CinemaRoom room : findAll()) {
            if (room.getId() != null && room.getId().equalsIgnoreCase(id)) {
                return room;
            }
        }
        return null;
    }

    // Tìm phòng chiếu theo Tên phòng
    public CinemaRoom findByName(String name) {
        for (CinemaRoom room : findAll()) {
            if (room.getName() != null && room.getName().equalsIgnoreCase(name)) {
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
