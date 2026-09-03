package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.Movie;
import service.MovieService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MovieHandler implements HttpHandler {

    private final MovieService movieService;

    public MovieHandler() {
        movieService = new MovieService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        addCorsHeaders(exchange);

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(
                    exchange,
                    405,
                    "{\"success\":false,\"message\":\"Method not allowed\"}"
            );
            return;
        }

        try {

            URI uri = exchange.getRequestURI();

            String path = uri.getPath();

            /*
             * GET /api/movies
             * Lấy tất cả phim
             */
            if ("/api/movies".equals(path)) {

                List<Movie> movies =
                        movieService.getAllMovies();

                sendResponse(
                        exchange,
                        200,
                        moviesToJson(movies)
                );

                return;
            }

            /*
             * GET /api/movies/now-showing
             * Lấy phim đang chiếu
             */
            if ("/api/movies/now-showing".equals(path)) {

                List<Movie> movies =
                        movieService.getNowShowingMovies();

                sendResponse(
                        exchange,
                        200,
                        moviesToJson(movies)
                );

                return;
            }

            /*
             * GET /api/movies/search?name=Avengers
             * Tìm phim theo tên
             */
            if ("/api/movies/search".equals(path)) {

                String keyword =
                        getQueryParameter(uri.getRawQuery(), "name");

                if (keyword == null ||
                        keyword.trim().isEmpty()) {

                    sendResponse(
                            exchange,
                            400,
                            "{\"success\":false,\"message\":\"Thiếu tên phim\"}"
                    );

                    return;
                }

                List<Movie> movies =
                        movieService.searchMovieByName(keyword);

                sendResponse(
                        exchange,
                        200,
                        moviesToJson(movies)
                );

                return;
            }

            /*
             * GET /api/movies/{id}
             * Tìm phim theo ID
             */
            if (path.startsWith("/api/movies/")) {

                String movieId =
                        path.substring("/api/movies/".length());

                if (!movieId.isEmpty()) {

                    Movie movie =
                            movieService.findById(movieId);

                    if (movie == null) {

                        sendResponse(
                                exchange,
                                404,
                                "{\"success\":false,\"message\":\"Không tìm thấy phim\"}"
                        );

                        return;
                    }

                    sendResponse(
                            exchange,
                            200,
                            movieToJson(movie)
                    );

                    return;
                }
            }

            sendResponse(
                    exchange,
                    404,
                    "{\"success\":false,\"message\":\"API không tồn tại\"}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    "{\"success\":false,\"message\":\"Lỗi server\"}"
            );
        }
    }

    /*
     * Chuyển danh sách Movie thành JSON.
     */
    private String moviesToJson(List<Movie> movies) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0; i < movies.size(); i++) {

            json.append(movieToJson(movies.get(i)));

            if (i < movies.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    /*
     * Chuyển một Movie thành JSON.
     */
    private String movieToJson(Movie movie) {

        return "{"
                + "\"id\":\"" + escapeJson(movie.getId()) + "\","
                + "\"title\":\"" + escapeJson(movie.getTitle()) + "\","
                + "\"duration\":" + movie.getDuration() + ","
                + "\"genre\":\"" + escapeJson(movie.getGenre()) + "\""
                + "}";
    }

    /*
     * Lấy parameter trong URL.
     */
    private String getQueryParameter(
            String query,
            String parameter) {

        if (query == null) {
            return null;
        }

        String[] params =
                query.split("&");

        for (String param : params) {

            String[] pair =
                    param.split("=", 2);

            if (pair.length == 2 &&
                    pair[0].equals(parameter)) {

                return URLDecoder.decode(
                        pair[1],
                        StandardCharsets.UTF_8
                );
            }
        }

        return null;
    }

    /*
     * Tránh lỗi khi String chứa dấu "
     */
    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    /*
     * Header cho phép Frontend gọi Backend.
     */
    private void addCorsHeaders(
            HttpExchange exchange) {

        Headers headers =
                exchange.getResponseHeaders();

        headers.set(
                "Access-Control-Allow-Origin",
                "*"
        );

        headers.set(
                "Access-Control-Allow-Methods",
                "GET, OPTIONS"
        );

        headers.set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );

        headers.set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );
    }

    /*
     * Gửi response về Frontend.
     */
    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response) throws IOException {

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );

        try (OutputStream output =
                     exchange.getResponseBody()) {

            output.write(bytes);
        }
    }
}