package service;

import model.Movie;
import model.Showtime;

import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private final List<Movie> movies;
    private final List<Showtime> showtimes;

    public MovieService() {

        movies = new ArrayList<>();
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
                "13+",
                "Đang chiếu"
        );

        Movie movie2 = new Movie(
                "M02",
                "The Conjuring",
                112,
                "Horror",
                "18+",
                "Đang chiếu"
        );

        Movie movie3 = new Movie(
                "M03",
                "Inside Out 2",
                100,
                "Animation",
                "No limit",
                "Sắp chiếu"
        );

        Movie movie4 = new Movie(
                "M04",
                "Interstellar",
                169,
                "Sci-Fi",
                "13+",
                "Đã kết thúc"
        );

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
    }

    // =========================
    // 1. LẤY TẤT CẢ PHIM
    // =========================

    public List<Movie> getAllMovies() {

        return new ArrayList<>(movies);
    }

    // =========================
    // 2. TÌM PHIM THEO ID
    // =========================

    public Movie findById(String movieId) {

        if (movieId == null ||
                movieId.trim().isEmpty()) {

            return null;
        }

        for (Movie movie : movies) {

            if (movie.getId()
                    .equalsIgnoreCase(movieId.trim())) {

                return movie;
            }
        }

        return null;
    }

    // =========================
    // 3. TÌM PHIM THEO TÊN
    // =========================

    public List<Movie> searchMovieByName(
            String keyword) {

        List<Movie> result =
                new ArrayList<>();

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return result;
        }

        String searchKeyword =
                keyword.trim().toLowerCase();

        for (Movie movie : movies) {

            if (movie.getTitle()
                    .toLowerCase()
                    .contains(searchKeyword)) {

                result.add(movie);
            }
        }

        return result;
    }

    // =========================
    // 4. KIỂM TRA PHIM ĐANG CHIẾU
    // =========================

    public boolean isMovieShowing(String movieId) {

        if (movieId == null) {
            return false;
        }

        for (Showtime showtime : showtimes) {

            if (showtime.getMovie() == null) {
                continue;
            }

            boolean sameMovie =
                    showtime.getMovie()
                            .getId()
                            .equalsIgnoreCase(movieId);

            if (sameMovie &&
                    showtime.isActive()) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // 5. LẤY PHIM ĐANG CHIẾU
    // =========================

    public List<Movie> getNowShowingMovies() {

        List<Movie> result =
                new ArrayList<>();

        for (Movie movie : movies) {

            if (isMovieShowing(movie.getId())) {
                result.add(movie);
            }
        }

        return result;
    }

    // =========================
    // 6. LẤY SUẤT CHIẾU CỦA PHIM
    // =========================

    public List<Showtime> getShowtimesOfMovie(
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
    // THÊM SUẤT CHIẾU
    // =========================

    public void addShowtime(Showtime showtime) {

        if (showtime != null) {
            showtimes.add(showtime);
        }
    }
}