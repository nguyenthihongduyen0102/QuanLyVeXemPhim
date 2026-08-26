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

        Movie movie4 = new Movie(
                "M04",
                "Interstellar",
                169,
                "Sci-Fi",
                "T13",
                "Đã kết thúc"
        );

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);

        // Phòng chiếu
        var room1 = new qlxemphim.model.CinemaRoom(
                "R01",
                "Phòng 1",
                100
        );

        var room2 = new qlxemphim.model.CinemaRoom(
                "R02",
                "Phòng 2",
                80
        );

        /*
         * Có thể thêm Showtime trực tiếp vào danh sách.
         */
        java.time.LocalDateTime now =
                java.time.LocalDateTime.now();

        showtimes.add(
                new Showtime(
                        "ST01",
                        movie1,
                        room1,
                        now.minusMinutes(30),
                        now.plusMinutes(150)
                )
        );

        showtimes.add(
                new Showtime(
                        "ST02",
                        movie2,
                        room2,
                        now.plusHours(2),
                        now.plusHours(4)
                )
        );

        showtimes.add(
                new Showtime(
                        "ST03",
                        movie4,
                        room1,
                        now.minusHours(4),
                        now.minusHours(1)
                )
        );
    }

    /*
     * 1. Lấy danh sách tất cả phim.
     */
    public List<Movie> getAllMovies() {

        return new ArrayList<>(movies);
    }

    /*
     * 2. Tìm phim theo tên.
     *
     * Không phân biệt chữ hoa/chữ thường.
     * Có thể tìm một phần tên.
     */
    public List<Movie> searchMovieByName(String keyword) {

        List<Movie> result = new ArrayList<>();

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

    /*
     * 3. Kiểm tra một phim có đang chiếu hay không.
     */
    public boolean isMovieShowing(String movieId) {

        for (Showtime showtime : showtimes) {

            if (showtime.getMovie() == null) {
                continue;
            }

            boolean sameMovie =
                    showtime.getMovie()
                            .getId()
                            .equalsIgnoreCase(movieId);

            if (sameMovie && showtime.isActive()) {
                return true;
            }
        }

        return false;
    }

    /*
     * 4. Lấy danh sách phim đang chiếu.
     */
    public List<Movie> getNowShowingMovies() {

        List<Movie> result = new ArrayList<>();

        for (Movie movie : movies) {

            if (isMovieShowing(movie.getId())) {
                result.add(movie);
            }
        }

        return result;
    }

    /*
     * Tìm phim theo ID.
     */
    public Movie findById(String movieId) {

        if (movieId == null) {
            return null;
        }

        for (Movie movie : movies) {

            if (movie.getId()
                    .equalsIgnoreCase(movieId)) {

                return movie;
            }
        }

        return null;
    }

    /*
     * Lấy các suất chiếu của một phim.
     */
    public List<Showtime> getShowtimesOfMovie(
            String movieId) {

        List<Showtime> result = new ArrayList<>();

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