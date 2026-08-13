package qlxephim.model;

public class Movie {
    private String id;
    private String title;
    private int duration;
    private String genre;

    public Movie(String id, String title, int duration, String genre) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

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

    @Override
    public String toString() {
        return String.format(
                "ID: %-3s | Tên: %-15s | Thời lượng: %3d phút | Thể loại: %s",
                id, title, duration, genre
        );
    }
}