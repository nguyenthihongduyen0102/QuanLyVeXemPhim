package repository;

import model.Showtime;

import java.util.ArrayList;

public class ShowtimeRepository {

    private ArrayList<Showtime> showtimes = new ArrayList<>();

    public void addShowtime(Showtime showtime) {

        if (showtime == null) {
            throw new IllegalArgumentException(
                    "Suất chiếu không hợp lệ"
            );
        }

        if (showtime.getShowtimeId() == null ||
                showtime.getShowtimeId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã suất chiếu không hợp lệ"
            );
        }

        if (findById(showtime.getShowtimeId()) != null) {
            throw new IllegalArgumentException(
                    "Mã suất chiếu đã tồn tại"
            );
        }

        showtimes.add(showtime);
    }

    public ArrayList<Showtime> getAllShowtimes() {
        return new ArrayList<>(showtimes);
    }

    public Showtime findById(String showtimeId) {

        if (showtimeId == null || showtimeId.isEmpty()) {
            return null;
        }

        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId()
                    .equalsIgnoreCase(showtimeId)) {
                return showtime;
            }
        }

        return null;
    }

    public boolean removeShowtime(String showtimeId) {

        Showtime showtime = findById(showtimeId);

        if (showtime != null) {
            showtimes.remove(showtime);
            return true;
        }

        return false;
    }

    public void setShowtimes(ArrayList<Showtime> showtimes) {

        if (showtimes == null) {
            this.showtimes = new ArrayList<>();
        } else {
            this.showtimes = new ArrayList<>(showtimes);
        }
    }

    public int getShowtimeCount() {
        return showtimes.size();
    }

    public boolean isEmpty() {
        return showtimes.isEmpty();
    }
}