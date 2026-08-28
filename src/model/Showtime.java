package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Showtime {

    private String id;
    private Movie movie;
    private CinemaRoom cinemaRoom;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Showtime(String id,
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

    // =========================
    // GETTER
    // =========================

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

    // =========================
    // SETTER
    // =========================

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

    // =========================
    // KIỂM TRA TRẠNG THÁI
    // =========================

    // Đang hoạt động
    public boolean isActive() {

        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(startTime)
                && now.isBefore(endTime);
    }

    // Sắp chiếu
    public boolean isUpcoming() {

        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(startTime);
    }

    // Đã kết thúc
    public boolean isFinished() {

        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(endTime);
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String movieName =
                movie != null
                        ? movie.getTitle()
                        : "Không có";

        String roomName =
                cinemaRoom != null
                        ? cinemaRoom.getName()
                        : "Không có";

        return String.format(
                "ID: %-5s | Phim: %-25s | Phòng: %-10s | " +
                        "Bắt đầu: %s | Kết thúc: %s",
                id,
                movieName,
                roomName,
                startTime.format(formatter),
                endTime.format(formatter)
        );
    }
}
