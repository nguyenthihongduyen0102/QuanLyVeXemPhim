package model;

public class Movie {
    private String movieId; //mã phim
    private String title; // tên phim
    private String genre; // thể loại
    private int duration; // thời lượng phim
    private int ageLimit; // độ tuổi cho phép
    private MovieStatus status; // trạng thái phim

    public Movie(){

    }

    public Movie(String movieId,
                 String title,
                 String genre,
                 int duration,
                 int ageLimit,
                 MovieStatus status) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.status = status;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public boolean isNowShowing(){
        return status == MovieStatus.NOW_SHOWING;
    }
}

