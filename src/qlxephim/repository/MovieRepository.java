package qlxephim.repository;

import qlxephim.model.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private static final String FILE_PATH = "src/data/movies.txt";

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 4) {
                    System.out.println("Dữ liệu phim không hợp lệ: " + line);
                    continue;
                }

                String id = data[0].trim();
                String title = data[1].trim();
                int duration = Integer.parseInt(data[2].trim());
                String genre = data[3].trim();

                Movie movie = new Movie(
                        id,
                        title,
                        duration,
                        genre
                );

                movies.add(movie);
            }

        } catch (IOException e) {
            System.out.println("Không thể đọc file movies.txt");
            System.out.println("Chi tiết: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Thời lượng phim không hợp lệ.");
        }

        return movies;
    }

    public Movie findById(String movieId) {

        List<Movie> movies = getAllMovies();

        for (Movie movie : movies) {
            if (movie.getId().equalsIgnoreCase(movieId)) {
                return movie;
            }
        }

        return null;
    }
}