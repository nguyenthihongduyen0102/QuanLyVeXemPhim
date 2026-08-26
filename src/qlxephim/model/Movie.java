package model;

public class Movie {

    private String id;
    private String title;
    private int duration;
    private String genre;
    private String ageRestriction;
    private String status;

    public Movie(String id, String title, int duration,
                 String genre, String ageRestriction, String status) {

        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.ageRestriction = ageRestriction;
        this.status = status;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-5s | Tên: %-25s | Thời lượng: %3d phút | " +
                        "Thể loại: %-12s | Độ tuổi: %-5s | Trạng thái: %s",
                id,
                title,
                duration,
                genre,
                ageRestriction,
                status
        );
    }
}