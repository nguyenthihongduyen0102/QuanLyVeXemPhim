package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.Showtime;
import service.ShowtimeService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ShowtimeHandler implements HttpHandler {

    private final ShowtimeService showtimeService;

    public ShowtimeHandler() {
        showtimeService = new ShowtimeService();
    }

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        addCorsHeaders(exchange);

        if ("OPTIONS".equalsIgnoreCase(
                exchange.getRequestMethod())) {

            exchange.sendResponseHeaders(
                    204,
                    -1
            );

            return;
        }

        if (!"GET".equalsIgnoreCase(
                exchange.getRequestMethod())) {

            sendResponse(
                    exchange,
                    405,
                    "{\"success\":false,\"message\":\"Method not allowed\"}"
            );

            return;
        }

        try {

            URI uri =
                    exchange.getRequestURI();

            String path =
                    uri.getPath();

            /*
             * GET /api/showtimes
             *
             * Lấy toàn bộ suất chiếu.
             */
            if ("/api/showtimes".equals(path)) {

                List<Showtime> showtimes =
                        showtimeService.getAllShowtimes();

                sendResponse(
                        exchange,
                        200,
                        showtimesToJson(showtimes)
                );

                return;
            }

            /*
             * GET /api/showtimes/active
             *
             * Lấy các suất chiếu đang hoạt động.
             */
            if ("/api/showtimes/active".equals(path)) {

                List<Showtime> showtimes =
                        showtimeService.getActiveShowtimes();

                sendResponse(
                        exchange,
                        200,
                        showtimesToJson(showtimes)
                );

                return;
            }

            /*
             * GET /api/showtimes/exists?id=SC01
             *
             * Kiểm tra suất chiếu tồn tại.
             */
            if ("/api/showtimes/exists".equals(path)) {

                String id =
                        getQueryParameter(
                                uri.getRawQuery(),
                                "id"
                        );

                if (id == null ||
                        id.trim().isEmpty()) {

                    sendResponse(
                            exchange,
                            400,
                            "{\"success\":false,\"message\":\"Thiếu ID suất chiếu\"}"
                    );

                    return;
                }

                boolean exists =
                        showtimeService.exists(id);

                sendResponse(
                        exchange,
                        200,
                        "{\"exists\":" + exists + "}"
                );

                return;
            }

            /*
             * GET /api/showtimes/check-active?id=SC01
             *
             * Kiểm tra suất chiếu có đang hoạt động.
             */
            if ("/api/showtimes/check-active"
                    .equals(path)) {

                String id =
                        getQueryParameter(
                                uri.getRawQuery(),
                                "id"
                        );

                if (id == null ||
                        id.trim().isEmpty()) {

                    sendResponse(
                            exchange,
                            400,
                            "{\"success\":false,\"message\":\"Thiếu ID suất chiếu\"}"
                    );

                    return;
                }

                boolean active =
                        showtimeService.isActive(id);

                sendResponse(
                        exchange,
                        200,
                        "{\"active\":" + active + "}"
                );

                return;
            }

            /*
             * GET /api/showtimes/movie?id=M01
             *
             * Lấy suất chiếu của một phim.
             */
            if ("/api/showtimes/movie".equals(path)) {

                String movieId =
                        getQueryParameter(
                                uri.getRawQuery(),
                                "id"
                        );

                if (movieId == null ||
                        movieId.trim().isEmpty()) {

                    sendResponse(
                            exchange,
                            400,
                            "{\"success\":false,\"message\":\"Thiếu ID phim\"}"
                    );

                    return;
                }

                List<Showtime> showtimes =
                        showtimeService
                                .getShowtimesByMovie(movieId);

                sendResponse(
                        exchange,
                        200,
                        showtimesToJson(showtimes)
                );

                return;
            }

            /*
             * GET /api/showtimes/{id}
             *
             * Tìm suất chiếu theo ID.
             */
            if (path.startsWith(
                    "/api/showtimes/")) {

                String id =
                        path.substring(
                                "/api/showtimes/"
                                        .length()
                        );

                if (!id.isEmpty()) {

                    Showtime showtime =
                            showtimeService.findById(id);

                    if (showtime == null) {

                        sendResponse(
                                exchange,
                                404,
                                "{\"success\":false,\"message\":\"Không tìm thấy suất chiếu\"}"
                        );

                        return;
                    }

                    sendResponse(
                            exchange,
                            200,
                            showtimeToJson(showtime)
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
     * Chuyển danh sách Showtime thành JSON.
     */
    private String showtimesToJson(
            List<Showtime> showtimes) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < showtimes.size();
             i++) {

            json.append(
                    showtimeToJson(
                            showtimes.get(i)
                    )
            );

            if (i < showtimes.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    /*
     * Chuyển Showtime thành JSON.
     */
    private String showtimeToJson(
            Showtime showtime) {

        return "{"
                + "\"id\":\""
                + escapeJson(showtime.getId())
                + "\","

                + "\"movieId\":\""
                + escapeJson(
                showtime.getMovie().getId()
        )
                + "\","

                + "\"movieTitle\":\""
                + escapeJson(
                showtime.getMovie().getTitle()
        )
                + "\","

                + "\"roomId\":\""
                + escapeJson(
                showtime
                        .getCinemaRoom()
                        .getId()
        )
                + "\","

                + "\"roomName\":\""
                + escapeJson(
                showtime
                        .getCinemaRoom()
                        .getName()
        )
                + "\","

                + "\"startTime\":\""
                + escapeJson(
                showtime
                        .getStartTime()
                        .toString()
        )
                + "\","

                + "\"endTime\":\""
                + escapeJson(
                showtime
                        .getEndTime()
                        .toString()
        )
                + "\""

                + "}";
    }

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

    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response)
            throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

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