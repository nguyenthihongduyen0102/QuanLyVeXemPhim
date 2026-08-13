package qlxephim.model;

import java.time.LocalDateTime;

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
     * Suất chiếu đang hoạt động khi thời điểm hiện tại
     * nằm trong khoảng từ startTime đến endTime.
     */
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(startTime)
                && !now.isAfter(endTime);
    }

    /*
     * Suất chiếu chưa bắt đầu.
     */
    public boolean isUpcoming() {
        return LocalDateTime.now().isBefore(startTime);
    }

    /*
     * Suất chiếu đã kết thúc.
     */
    public boolean isFinished() {
        return LocalDateTime.now().isAfter(endTime);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3s | Phim: %-15s | Phòng: %-10s | %s → %s",
                id,
                movie.getTitle(),
                cinemaRoom.getName(),
                startTime,
                endTime
        );
    }
}