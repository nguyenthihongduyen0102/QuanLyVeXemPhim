package qlxephim.service;

import qlxephim.model.Movie;
import qlxephim.model.Showtime;
import qlxephim.repository.MovieRepository;
import qlxephim.repository.ShowtimeRepository;

import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    public MovieService() {

        movieRepository = new MovieRepository();
        showtimeRepository = new ShowtimeRepository();
    }

    /*
     * 1. Lấy danh sách tất cả phim.
     */
    public List<Movie> getAllMovies() {

        return movieRepository.getAllMovies();
    }

    /*
     * 2. Tìm phim theo tên.
     *
     * Không phân biệt chữ hoa/chữ thường.
     * Có thể tìm một phần tên.
     */
    public List<Movie> searchMovieByName(
            String keyword) {

        List<Movie> result = new ArrayList<>();

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return result;
        }

        String searchKeyword =
                keyword.trim().toLowerCase();

        for (Movie movie :
                movieRepository.getAllMovies()) {

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
     *
     * Một phim đang chiếu nếu có ít nhất
     * một Showtime đang active.
     */
    public boolean isMovieShowing(String movieId) {

        List<Showtime> showtimes =
                showtimeRepository.getAllShowtimes();

        for (Showtime showtime : showtimes) {

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

        List<Movie> movies =
                movieRepository.getAllMovies();

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

        return movieRepository.findById(movieId);
    }

    /*
     * Lấy các suất chiếu của một phim.
     */
    public List<Showtime> getShowtimesOfMovie(
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