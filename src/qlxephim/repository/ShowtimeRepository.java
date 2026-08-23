package qlxephim.repository;

import qlxephim.model.CinemaRoom;
import qlxephim.model.Movie;
import qlxephim.model.Showtime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository {

    private static final String FILE_PATH = "data/showtimes.txt";

    private final MovieRepository movieRepository;

    public ShowtimeRepository() {
        movieRepository = new MovieRepository();
    }

    public List<Showtime> getAllShowtimes() {

        List<Showtime> showtimes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 5) {
                    System.out.println(
                            "Dữ liệu suất chiếu không hợp lệ: " + line
                    );
                    continue;
                }

                String id = data[0].trim();
                String movieId = data[1].trim();
                String roomId = data[2].trim();

                LocalDateTime startTime =
                        LocalDateTime.parse(data[3].trim());

                LocalDateTime endTime =
                        LocalDateTime.parse(data[4].trim());

                Movie movie = movieRepository.findById(movieId);

                if (movie == null) {
                    System.out.println(
                            "Không tìm thấy phim " + movieId
                    );
                    continue;
                }

                CinemaRoom room = createCinemaRoom(roomId);

                Showtime showtime = new Showtime(
                        id,
                        movie,
                        room,
                        startTime,
                        endTime
                );

                showtimes.add(showtime);
            }

        } catch (IOException e) {

            System.out.println(
                    "Không thể đọc file showtimes.txt"
            );

            System.out.println(
                    "Chi tiết: " + e.getMessage()
            );

        } catch (Exception e) {

            System.out.println(
                    "Dữ liệu suất chiếu không hợp lệ."
            );

            System.out.println(
                    "Chi tiết: " + e.getMessage()
            );
        }

        return showtimes;
    }

    public Showtime findById(String showtimeId) {

        List<Showtime> showtimes = getAllShowtimes();

        for (Showtime showtime : showtimes) {

            if (!Boolean.parseBoolean(showtime.getId()
                    .toString())) {
                continue;
            }

            return showtime;
        }

        return null;
    }

    /*
     * Tạo thông tin phòng dựa trên mã phòng.
     *
     * Nếu sau này có rooms.txt thì có thể
     * tách phần này thành CinemaRoomRepository.
     */
    private CinemaRoom createCinemaRoom(String roomId) {

        switch (roomId.toUpperCase()) {

            case "R01":
                return new CinemaRoom(
                        "R01",
                        "Room 01",
                        100
                );

            case "R02":
                return new CinemaRoom(
                        "R02",
                        "Room 02",
                        120
                );

            case "R03":
                return new CinemaRoom(
                        "R03",
                        "Room 03",
                        150
                );

            default:
                return new CinemaRoom(
                        roomId,
                        "Room " + roomId,
                        100
                );
        }
    }
}