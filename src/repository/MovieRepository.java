package repository;

import model.Movie;

import java.util.ArrayList;

public class MovieRepository {

    private ArrayList<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {

        if (movie == null) {
            throw new IllegalArgumentException(
                    "Phim không hợp lệ"
            );
        }

        if (movie.getMovieId() == null ||
                movie.getMovieId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã phim không hợp lệ"
            );
        }

        if (findById(movie.getMovieId()) != null) {
            throw new IllegalArgumentException(
                    "Mã phim đã tồn tại"
            );
        }

        movies.add(movie);
    }

    public ArrayList<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public Movie findById(String movieId) {

        if (movieId == null || movieId.isEmpty()) {
            return null;
        }

        for (Movie movie : movies) {
            if (movie.getMovieId()
                    .equalsIgnoreCase(movieId)) {
                return movie;
            }
        }

        return null;
    }

    public boolean removeMovie(String movieId) {

        Movie movie = findById(movieId);

        if (movie != null) {
            movies.remove(movie);
            return true;
        }

        return false;
    }

    public void setMovies(ArrayList<Movie> movies) {

        if (movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies = new ArrayList<>(movies);
        }
    }

    public int getMovieCount() {
        return movies.size();
    }

    public boolean isEmpty() {
        return movies.isEmpty();
    }
}