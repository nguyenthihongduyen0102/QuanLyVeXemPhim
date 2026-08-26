package repository;

import model.Movie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final String file_txt = "src/data/movies.txt";
    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_txt))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String title = parts[1].trim();
                    String genre = parts[2].trim();
                    int duration = Integer.parseInt(parts[3].trim());
                    String ageRestriction = parts[4].trim();
                    String status = parts[5].trim();

                    Movie movie = new Movie(id, title, genre, duration, ageRestriction, status);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file movies.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi ép kiểu thời lượng phim: " + e.getMessage());
        }
        return movies;
    }
    public void saveAll(List<Movie> movies) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_txt))) {
            for (Movie m : movies) {
                String line = String.format("%s,%s,%s,%d,%s,%s",
                        m.getId(),
                        m.getTitle(),
                        m.getGenre(),
                        m.getDuration(),
                        m.getAgeRestriction(),
                        m.getStatus());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file movies.txt: " + e.getMessage());
        }
    }

    // Lọc danh sách các phim Đang chiếu
    public List<Movie> findNowShowing() {
        List<Movie> result = new ArrayList<>();
        for (Movie m : findAll()) {
            if ("Đang chiếu".equalsIgnoreCase(m.getStatus())) {
                result.add(m);
            }
        }
        return result;
    }

    // Tìm phim theo ID
    public Movie findById(String id) {
        for (Movie m : findAll()) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    // Tìm phim theo tên (chứa từ khóa, không phân biệt hoa/thường)
    public List<Movie> findByTitle(String title) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : findAll()) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }
    // Thêm một phim mới
    public void add(Movie movie) {
        List<Movie> movies = findAll();
        movies.add(movie);
        saveAll(movies);
    }
}
