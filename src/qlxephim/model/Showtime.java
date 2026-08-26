package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Showtime {

    private String id;
    private Movie movie;
    private CinemaRoom cinemaRoom;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Showtime(
            String id,
            Movie movie,
            CinemaRoom cinemaRoom,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        this.id = id;
        this.movie = movie;
        this.cinemaRoom = cinemaRoom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter
    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /*
     * Kiểm tra suất chiếu đang hoạt động.
     */
    public boolean isActive() {

        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(startTime)
                && now.isBefore(endTime);
    }

    /*
     * Kiểm tra suất chiếu chưa bắt đầu.
     */
    public boolean isUpcoming() {

        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(startTime);
    }

    /*
     * Kiểm tra suất chiếu đã kết thúc.
     */
    public boolean isFinished() {

        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(endTime);
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return String.format(
                "ID: %-5s | Phim: %-25s | Phòng: %-10s | " +
                        "Bắt đầu: %s | Kết thúc: %s",
                id,
                movie != null ? movie.getTitle() : "Không có",
                cinemaRoom != null ? cinemaRoom.getName() : "Không có",
                startTime.format(formatter),
                endTime.format(formatter)
        );
    }
}