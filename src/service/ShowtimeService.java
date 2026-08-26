package service;

import model.CinemaRoom;
import model.Movie;
import model.Showtime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeService {

    private final List<Showtime> showtimes;

    public ShowtimeService() {

        showtimes = new ArrayList<>();

        createSampleData();
    }

    // =========================
    // TẠO DỮ LIỆU MẪU
    // =========================

    private void createSampleData() {

        Movie movie1 = new Movie(
                "M01",
                "Avengers: Endgame",
                181,
                "Action",
                "T13",
                "Đang chiếu"
        );

        Movie movie2 = new Movie(
                "M02",
                "The Conjuring",
                112,
                "Horror",
                "T18",
                "Đang chiếu"
        );

        Movie movie3 = new Movie(
                "M03",
                "Inside Out 2",
                100,
                "Animation",
                "P",
                "Sắp chiếu"
        );

        Movie movie4 = new Movie(
                "M04",
                "Interstellar",
                169,
                "Sci-Fi",
                "T13",
                "Đã kết thúc"
        );

        CinemaRoom room1 =
                new CinemaRoom(
                        "R01",
                        "Phòng 1",
                        100
                );

        CinemaRoom room2 =
                new CinemaRoom(
                        "R02",
                        "Phòng 2",
                        80
                );

        LocalDateTime now =
                LocalDateTime.now();

        // Đang hoạt động
        showtimes.add(
                new Showtime(
                        "ST01",
                        movie1,
                        room1,
                        now.minusMinutes(30),
                        now.plusMinutes(150)
                )
        );

        // Sắp chiếu
        showtimes.add(
                new Showtime(
                        "ST02",
                        movie2,
                        room2,
                        now.plusHours(2),
                        now.plusHours(4)
                )
        );

        // Đã kết thúc
        showtimes.add(
                new Showtime(
                        "ST03",
                        movie3,
                        room1,
                        now.minusHours(4),
                        now.minusHours(2)
                )
        );

        // Đã kết thúc
        showtimes.add(
                new Showtime(
                        "ST04",
                        movie4,
                        room2,
                        now.minusHours(5),
                        now.minusHours(2)
                )
        );
    }

    // =========================
    // 1. LẤY TẤT CẢ SUẤT CHIẾU
    // =========================

    public List<Showtime> getAllShowtimes() {

        return new ArrayList<>(showtimes);
    }

    // =========================
    // 2. KIỂM TRA TỒN TẠI
    // =========================

    public boolean exists(String showtimeId) {

        return findById(showtimeId) != null;
    }

    // =========================
    // 3. KIỂM TRA ĐANG HOẠT ĐỘNG
    // =========================

    public boolean isActive(String showtimeId) {

        Showtime showtime =
                findById(showtimeId);

        if (showtime == null) {
            return false;
        }

        return showtime.isActive();
    }

    // =========================
    // 4. TÌM THEO ID
    // =========================

    public Showtime findById(String showtimeId) {

        if (showtimeId == null ||
                showtimeId.trim().isEmpty()) {

            return null;
        }

        for (Showtime showtime : showtimes) {

            if (showtime.getId()
                    .equalsIgnoreCase(showtimeId.trim())) {

                return showtime;
            }
        }

        return null;
    }

    // =========================
    // 5. LẤY SUẤT CHIẾU ĐANG HOẠT ĐỘNG
    // =========================

    public List<Showtime> getActiveShowtimes() {

        List<Showtime> result =
                new ArrayList<>();

        for (Showtime showtime : showtimes) {

            if (showtime.isActive()) {
                result.add(showtime);
            }
        }

        return result;
    }

    // =========================
    // 6. LẤY SUẤT CHIẾU THEO PHIM
    // =========================

    public List<Showtime> getShowtimesByMovie(
            String movieId) {

        List<Showtime> result =
                new ArrayList<>();

        if (movieId == null ||
                movieId.trim().isEmpty()) {

            return result;
        }

        for (Showtime showtime : showtimes) {

            if (showtime.getMovie() == null) {
                continue;
            }

            if (showtime.getMovie()
                    .getId()
                    .equalsIgnoreCase(movieId)) {

                result.add(showtime);
            }
        }

        return result;
    }

    // =========================
    // 7. THÊM SUẤT CHIẾU
    // =========================

    public void addShowtime(Showtime showtime) {

        if (showtime != null) {
            showtimes.add(showtime);
        }
    }
}