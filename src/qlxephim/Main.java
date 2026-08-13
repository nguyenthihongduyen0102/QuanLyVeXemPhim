package qlxephim;

import qlxephim.model.Movie;
import qlxephim.model.Showtime;
import qlxephim.service.MovieService;
import qlxephim.service.ShowtimeService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final MovieService movieService =
            new MovieService();

    private static final ShowtimeService showtimeService =
            new ShowtimeService();

    public static void main(String[] args) {

        while (true) {

            showMenu();

            System.out.print("Chọn chức năng: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    showAllMovies();
                    break;

                case "2":
                    showNowShowingMovies();
                    break;

                case "3":
                    searchMovie();
                    break;

                case "4":
                    checkMovieShowing();
                    break;

                case "5":
                    showAllShowtimes();
                    break;

                case "6":
                    checkShowtimeExists();
                    break;

                case "7":
                    checkShowtimeActive();
                    break;

                case "8":
                    showShowtimesOfMovie();
                    break;

                case "0":
                    System.out.println(
                            "Đã thoát chương trình."
                    );
                    return;

                default:
                    System.out.println(
                            "Lựa chọn không hợp lệ!"
                    );
            }

            System.out.println();
            System.out.println(
                    "Nhấn Enter để tiếp tục..."
            );

            scanner.nextLine();
        }
    }

    private static void showMenu() {

        System.out.println();
        System.out.println(
                "=========================================="
        );
        System.out.println(
                "       QUẢN LÝ PHIM + SUẤT CHIẾU"
        );
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "1. Hiển thị danh sách phim"
        );

        System.out.println(
                "2. Hiển thị phim đang chiếu"
        );

        System.out.println(
                "3. Tìm phim theo tên"
        );

        System.out.println(
                "4. Kiểm tra phim còn đang chiếu"
        );

        System.out.println(
                "5. Hiển thị danh sách suất chiếu"
        );

        System.out.println(
                "6. Kiểm tra suất chiếu tồn tại"
        );

        System.out.println(
                "7. Kiểm tra suất chiếu còn hoạt động"
        );

        System.out.println(
                "8. Xem suất chiếu của một phim"
        );

        System.out.println(
                "0. Thoát"
        );

        System.out.println(
                "=========================================="
        );
    }

    /*
     * 1. Hiển thị tất cả phim.
     */
    private static void showAllMovies() {

        List<Movie> movies =
                movieService.getAllMovies();

        System.out.println();
        System.out.println(
                "========== DANH SÁCH PHIM =========="
        );

        if (movies.isEmpty()) {

            System.out.println(
                    "Không có phim nào."
            );

            return;
        }

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /*
     * 2. Hiển thị phim đang chiếu.
     */
    private static void showNowShowingMovies() {

        List<Movie> movies =
                movieService.getNowShowingMovies();

        System.out.println();
        System.out.println(
                "========== PHIM ĐANG CHIẾU =========="
        );

        if (movies.isEmpty()) {

            System.out.println(
                    "Hiện không có phim nào đang chiếu."
            );

            return;
        }

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /*
     * 3. Tìm phim theo tên.
     */
    private static void searchMovie() {

        System.out.print(
                "Nhập tên phim cần tìm: "
        );

        String keyword =
                scanner.nextLine();

        List<Movie> movies =
                movieService.searchMovieByName(keyword);

        System.out.println();
        System.out.println(
                "========== KẾT QUẢ TÌM KIẾM =========="
        );

        if (movies.isEmpty()) {

            System.out.println(
                    "Không tìm thấy phim phù hợp."
            );

            return;
        }

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /*
     * 4. Kiểm tra phim còn đang chiếu.
     */
    private static void checkMovieShowing() {

        System.out.print(
                "Nhập ID phim: "
        );

        String movieId =
                scanner.nextLine();

        Movie movie =
                movieService.findById(movieId);

        if (movie == null) {

            System.out.println(
                    "Không tìm thấy phim."
            );

            return;
        }

        boolean showing =
                movieService.isMovieShowing(movieId);

        if (showing) {

            System.out.println(
                    "Phim \"" + movie.getTitle()
                            + "\" ĐANG CHIẾU."
            );

        } else {

            System.out.println(
                    "Phim \"" + movie.getTitle()
                            + "\" KHÔNG ĐANG CHIẾU."
            );
        }

    }

    /*
     * 5. Hiển thị tất cả suất chiếu.
     */
    private static void showAllShowtimes() {

        List<Showtime> showtimes =
                showtimeService.getAllShowtimes();

        System.out.println();
        System.out.println(
                "========== DANH SÁCH SUẤT CHIẾU =========="
        );

        if (showtimes.isEmpty()) {

            System.out.println(
                    "Không có suất chiếu."
            );

            return;
        }

        for (Showtime showtime : showtimes) {

            System.out.println(showtime);

            if (showtime.isActive()) {

                System.out.println(
                        "   Trạng thái: ĐANG HOẠT ĐỘNG"
                );

            } else if (showtime.isUpcoming()) {

                System.out.println(
                        "   Trạng thái: SẮP CHIẾU"
                );

            } else {

                System.out.println(
                        "   Trạng thái: ĐÃ KẾT THÚC"
                );
            }
        }
    }

    /*
     * 6. Kiểm tra suất chiếu tồn tại.
     */
    private static void checkShowtimeExists() {

        System.out.print(
                "Nhập ID suất chiếu: "
        );

        String showtimeId =
                scanner.nextLine();

        boolean exists =
                showtimeService.exists(showtimeId);

        if (exists) {

            System.out.println(
                    "Suất chiếu " + showtimeId
                            + " CÓ TỒN TẠI."
            );

        } else {

            System.out.println(
                    "Suất chiếu " + showtimeId
                            + " KHÔNG TỒN TẠI."
            );
        }
    }

    /*
     * 7. Kiểm tra suất chiếu còn hoạt động.
     */
    private static void checkShowtimeActive() {

        System.out.print(
                "Nhập ID suất chiếu: "
        );

        String showtimeId =
                scanner.nextLine();

        Showtime showtime =
                showtimeService.findById(showtimeId);

        if (showtime == null) {

            System.out.println(
                    "Suất chiếu không tồn tại."
            );

            return;
        }

        if (showtime.isActive()) {

            System.out.println(
                    "Suất chiếu "
                            + showtimeId
                            + " ĐANG HOẠT ĐỘNG."
            );

        } else if (showtime.isUpcoming()) {

            System.out.println(
                    "Suất chiếu "
                            + showtimeId
                            + " CHƯA BẮT ĐẦU."
            );

        } else {

            System.out.println(
                    "Suất chiếu "
                            + showtimeId
                            + " ĐÃ KẾT THÚC."
            );
        }
    }

    /*
     * 8. Xem các suất chiếu của một phim.
     */
    private static void showShowtimesOfMovie() {

        System.out.print(
                "Nhập ID phim: "
        );

        String movieId =
                scanner.nextLine();

        Movie movie =
                movieService.findById(movieId);

        if (movie == null) {

            System.out.println(
                    "Không tìm thấy phim."
            );

            return;
        }

        List<Showtime> showtimes =
                movieService.getShowtimesOfMovie(movieId);

        System.out.println();
        System.out.println(
                "Các suất chiếu của phim: "
                        + movie.getTitle()
        );

        if (showtimes.isEmpty()) {

            System.out.println(
                    "Phim chưa có suất chiếu."
            );

            return;
        }

        for (Showtime showtime : showtimes) {
            System.out.println(showtime);
        }
    }
}