package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Movie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final String file_json = "movies.json";
    private final Gson gson;

    public MovieRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(file_json);
        try {
            if (!file.exists()) {
                file.createNewFile();
                saveAll(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file movies.json: " + e.getMessage());
        }
    }

    // Đọc danh sách phim từ file JSON
    public List<Movie> findAll() {
        File file = new File(file_json);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file_json))) {
            Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();
            List<Movie> movies = gson.fromJson(br, listType);
            return (movies != null) ? movies : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Lỗi đọc file movies.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Ghi toàn bộ danh sách phim xuống file JSON
    public void saveAll(List<Movie> movies) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_json))) {
            gson.toJson(movies, bw);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file movies.json: " + e.getMessage());
        }
    }

    // Tìm phim theo Mã ID
    public Movie findById(String id) {
        for (Movie m : findAll()) {
            if (m.getId() != null && m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    // Tìm danh sách phim theo Thể loại
    public List<Movie> findByGenre(String genre) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : findAll()) {
            if (m.getGenre() != null && m.getGenre().equalsIgnoreCase(genre)) {
                result.add(m);
            }
        }
        return result;
    }

    // Thêm phim mới
    public void add(Movie movie) {
        List<Movie> movies = findAll();
        movies.add(movie);
        saveAll(movies);
    }
}