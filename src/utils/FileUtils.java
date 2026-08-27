package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Đọc danh sách đối tượng từ file JSON
    public static <T> List<T> readJsonFile(String filePath, Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement != null && jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    T item = GSON.fromJson(element, clazz);
                    list.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file " + filePath + ": " + e.getMessage());
        }
        return list;
    }

    // Ghi danh sách đối tượng xuống file JSON
    public static <T> void writeJsonFile(String filePath, List<T> data) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file " + filePath + ": " + e.getMessage());
        }
    }
}
