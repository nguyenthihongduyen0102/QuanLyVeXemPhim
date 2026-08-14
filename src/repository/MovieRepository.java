package repository;

import model.Movie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MovieRepository {
    private final String file_txt = "src/data/movies.txt";
    public ArrayList<Movie> loadMovies() {
        ArrayList<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_txt))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }

        return movies;
    }
}
