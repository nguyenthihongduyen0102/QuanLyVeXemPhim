package repository;

import model.Showtime;
import model.Movie;
import model.CinemaRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository {
    private final String file_txt = "showtime.txt";
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final MovieRepository movieRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    public ShowtimeRepository(MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;

        File file = new File(file_txt);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file showtime.txt: " + e.getMessage());
        }
    }

    // Đọc toàn bộ danh sách suất chiếu từ file showtime.txt
    public List<Showtime> findAll() {
        List<Showtime> showtimes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_txt))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String movieId = parts[1].trim();
                    String roomName = parts[2].trim();
                    LocalTime startTime = LocalTime.parse(parts[3].trim(), timeFormatter);
                    LocalTime endTime = LocalTime.parse(parts[4].trim(), timeFormatter);
                    double basePrice = Double.parseDouble(parts[5].trim());

                    Movie movie = movieRepository.findById(movieId);
                    CinemaRoom room = cinemaRoomRepository.findByName(roomName);

                    Showtime showtime = new Showtime(id, movie, room, startTime, endTime, basePrice);
                    showtimes.add(showtime);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file showtime.txt: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi định dạng dữ liệu trong showtime.txt: " + e.getMessage());
        }

        return showtimes;
    }

    // Ghi lại toàn bộ danh sách suất chiếu vào file showtime.txt
    public void saveAll(List<Showtime> showtimes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_txt))) {
            for (Showtime st : showtimes) {
                String movieId = (st.getMovie() != null) ? st.getMovie().getId() : "";
                String roomName = (st.getCinemaRoom() != null) ? st.getCinemaRoom().getName() : "";

                String line = String.format("%s,%s,%s,%s,%s,%.0f",
                        st.getId(),
                        movieId,
                        roomName,
                        st.getStartTime().format(timeFormatter),
                        st.getEndTime().format(timeFormatter),
                        st.getBasePrice());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file showtime.txt: " + e.getMessage());
        }
    }

    // Tìm suất chiếu theo Mã ID
    public Showtime findById(String id) {
        for (Showtime st : findAll()) {
            if (st.getId().equalsIgnoreCase(id)) {
                return st;
            }
        }
        return null;
    }

    // Tìm suất chiếu theo Mã Phim
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
