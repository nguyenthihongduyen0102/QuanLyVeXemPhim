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

    /*
     * Tạo dữ liệu mẫu.
     */
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

        CinemaRoom room1 =
                new CinemaRoom("R01", "Phòng 1", 100);

        CinemaRoom room2 =
                new CinemaRoom("R02", "Phòng 2", 80);

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
    }

    /*
     * Lấy toàn bộ suất chiếu.
     */
    public List<Showtime> getAllShowtimes() {

        return new ArrayList<>(showtimes);
    }

    /*
     * Kiểm tra suất chiếu có tồn tại hay không.
     */
    public boolean exists(String showtimeId) {

        return findById(showtimeId) != null;
    }

    /*
     * Kiểm tra suất chiếu có đang hoạt động hay không.
     */
    public boolean isActive(String showtimeId) {

        Showtime showtime =
                findById(showtimeId);

        if (showtime == null) {
            return false;
        }

        return showtime.isActive();
    }

    /*
     * Tìm suất chiếu theo ID.
     */
    public Showtime findById(String showtimeId) {

        if (showtimeId == null) {
            return null;
        }

        for (Showtime showtime : showtimes) {

            if (showtime.getId()
                    .equalsIgnoreCase(showtimeId)) {

                return showtime;
            }
        }

        return null;
    }

    /*
     * Lấy danh sách các suất chiếu đang hoạt động.
     */
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

    /*
     * Lấy các suất chiếu của một phim.
     */
    public List<Showtime> getShowtimesByMovie(
            String movieId) {

        List<Showtime> result =
                new ArrayList<>();

        if (movieId == null) {
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
}