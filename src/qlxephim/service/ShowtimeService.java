package qlxephim.service;

import qlxephim.model.Showtime;
import qlxephim.repository.ShowtimeRepository;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public ShowtimeService() {
        showtimeRepository = new ShowtimeRepository();
    }

    /*
     * Lấy toàn bộ suất chiếu.
     */
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.getAllShowtimes();
    }

    /*
     * Kiểm tra suất chiếu có tồn tại hay không.
     */
    public boolean exists(String showtimeId) {

        Showtime showtime =
                showtimeRepository.findById(showtimeId);

        return showtime != null;
    }

    /*
     * Kiểm tra suất chiếu có đang hoạt động hay không.
     */
    public boolean isActive(String showtimeId) {

        Showtime showtime =
                showtimeRepository.findById(showtimeId);

        if (showtime == null) {
            return false;
        }

        return showtime.isActive();
    }

    /*
     * Tìm suất chiếu theo ID.
     */
    public Showtime findById(String showtimeId) {

        return showtimeRepository.findById(showtimeId);
    }

    /*
     * Lấy danh sách các suất chiếu đang hoạt động.
     */
    public List<Showtime> getActiveShowtimes() {

        List<Showtime> result = new ArrayList<>();

        for (Showtime showtime :
                showtimeRepository.getAllShowtimes()) {

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

        List<Showtime> result = new ArrayList<>();

        for (Showtime showtime :
                showtimeRepository.getAllShowtimes()) {

            if (showtime.getMovie()
                    .getId()
                    .equalsIgnoreCase(movieId)) {

                result.add(showtime);
            }
        }

        return result;
    }
}