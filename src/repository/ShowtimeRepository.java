package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.CinemaRoom;
import model.Movie;
import model.Showtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository {
    private final String file_json = "showtimes.json";
    private final Gson gson;
    private final MovieRepository movieRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    public ShowtimeRepository(MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file showtimes.json: " + e.getMessage());
        }
    }

    // Đọc danh sách suất chiếu từ file showtimes.json
    public List<Showtime> findAll() {
        List<Showtime> showtimes = new ArrayList<>();
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return showtimes;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            JsonElement element = JsonParser.parseReader(br);
            if (!element.isJsonArray()) return showtimes;

            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement item : jsonArray) {
                JsonObject obj = item.getAsJsonObject();

                String id = obj.has("id") ? obj.get("id").getAsString() : "";
                String movieId = obj.has("movieId") ? obj.get("movieId").getAsString() : "";
                String roomName = obj.has("roomName") ? obj.get("roomName").getAsString() : "";
                String startTimeStr = obj.has("startTime") ? obj.get("startTime").getAsString() : "00:00";
                String endTimeStr = obj.has("endTime") ? obj.get("endTime").getAsString() : "00:00";

                // Ánh xạ đối tượng Movie và CinemaRoom từ Repository tương ứng
                Movie movie = movieRepository.findById(movieId);
                CinemaRoom room = cinemaRoomRepository.findByName(roomName);

                // Chuyển chuỗi giờ "HH:mm" thành LocalDateTime (mặc định lấy ngày hôm nay)
                LocalDate today = LocalDate.now();
                LocalDateTime startDateTime = LocalDateTime.of(today, LocalTime.parse(startTimeStr));
                LocalDateTime endDateTime = LocalDateTime.of(today, LocalTime.parse(endTimeStr));

                Showtime showtime = new Showtime(id, movie, room, startDateTime, endDateTime);
                showtimes.add(showtime);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file showtimes.json: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi ép kiểu dữ liệu suất chiếu: " + e.getMessage());
        }

        return showtimes;
    }

    // Ghi danh sách suất chiếu vào file showtimes.json
    public void saveAll(List<Showtime> showtimes) {
        JsonArray jsonArray = new JsonArray();

        for (Showtime st : showtimes) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", st.getId());
            obj.addProperty("movieId", (st.getMovie() != null) ? st.getMovie().getId() : "");
            obj.addProperty("roomName", (st.getCinemaRoom() != null) ? st.getCinemaRoom().getName() : "");

            String startStr = (st.getStartTime() != null) ? st.getStartTime().toLocalTime().toString() : "00:00";
            String endStr = (st.getEndTime() != null) ? st.getEndTime().toLocalTime().toString() : "00:00";

            obj.addProperty("startTime", startStr);
            obj.addProperty("endTime", endStr);
            obj.addProperty("basePrice", 90000); // Giá gốc mặc định

            jsonArray.add(obj);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(jsonArray, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file showtimes.json: " + e.getMessage());
        }
    }

    // Tìm suất chiếu theo Mã ID
    public Showtime findById(String id) {
        for (Showtime st : findAll()) {
            if (st.getId() != null && st.getId().equalsIgnoreCase(id)) {
                return st;
            }
        }
        return null;
    }

    // Lấy danh sách suất chiếu theo Mã Phim
    public List<Showtime> findByMovieId(String movieId) {
        List<Showtime> result = new ArrayList<>();
        for (Showtime st : findAll()) {
            if (st.getMovie() != null && st.getMovie().getId().equalsIgnoreCase(movieId)) {
                result.add(st);
            }
        }
        return result;
    }

    // Thêm suất chiếu mới
    public void add(Showtime showtime) {
        List<Showtime> showtimes = findAll();
        showtimes.add(showtime);
        saveAll(showtimes);
    }
}