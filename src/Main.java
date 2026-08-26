import model.Movie;
import model.Showtime;
import service.MovieService;
import service.ShowtimeService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ========================================
        // KHỞI TẠO SERVICE
        // ========================================

        MovieService movieService =
                new MovieService();

        ShowtimeService showtimeService =
                new ShowtimeService();


        // ========================================
        // 1. HIỂN THỊ TẤT CẢ PHIM
        // ========================================

        System.out.println("========================================");
        System.out.println("1. DANH SÁCH TẤT CẢ PHIM");
        System.out.println("========================================");

        List<Movie> movies =
                movieService.getAllMovies();

        for (Movie movie : movies) {
            System.out.println(movie);
        }


        // ========================================
        // 2. TÌM PHIM THEO ID
        // ========================================

        System.out.println("\n========================================");
        System.out.println("2. TÌM PHIM THEO ID");
        System.out.println("========================================");

        Movie movie =
                movieService.findById("M01");

        if (movie != null) {
            System.out.println(movie);
        } else {
            System.out.println("Không tìm thấy phim.");
        }


        // ========================================
        // 3. TÌM PHIM THEO TÊN
        // ========================================

        System.out.println("\n========================================");
        System.out.println("3. TÌM PHIM THEO TÊN");
        System.out.println("========================================");

        List<Movie> searchResult =
                movieService.searchMovieByName("avengers");

        if (searchResult.isEmpty()) {

            System.out.println("Không tìm thấy phim.");

        } else {

            for (Movie m : searchResult) {
                System.out.println(m);
            }
        }


        // ========================================
        // 4. HIỂN THỊ PHIM ĐANG CHIẾU
        // ========================================

        System.out.println("\n========================================");
        System.out.println("4. PHIM ĐANG CHIẾU");
        System.out.println("========================================");

        List<Movie> nowShowing =
                movieService.getNowShowingMovies();

        if (nowShowing.isEmpty()) {

            System.out.println("Hiện không có phim đang chiếu.");

        } else {

            for (Movie m : nowShowing) {
                System.out.println(m);
            }
        }


        // ========================================
        // 5. KIỂM TRA PHIM CÓ ĐANG CHIẾU
        // ========================================

        System.out.println("\n========================================");
        System.out.println("5. KIỂM TRA PHIM ĐANG CHIẾU");
        System.out.println("========================================");

        String movieId = "M01";

        boolean showing =
                movieService.isMovieShowing(movieId);

        System.out.println(
                "Phim " + movieId +
                        " đang chiếu: " + showing
        );


        // ========================================
        // 6. LẤY SUẤT CHIẾU CỦA PHIM
        // ========================================

        System.out.println("\n========================================");
        System.out.println("6. SUẤT CHIẾU CỦA PHIM M01");
        System.out.println("========================================");

        List<Showtime> movieShowtimes =
                movieService.getShowtimesOfMovie("M01");

        if (movieShowtimes.isEmpty()) {

            System.out.println(
                    "Phim không có suất chiếu."
            );

        } else {

            for (Showtime showtime : movieShowtimes) {
                System.out.println(showtime);
            }
        }


        // ========================================
        // 7. HIỂN THỊ TẤT CẢ SUẤT CHIẾU
        // ========================================

        System.out.println("\n========================================");
        System.out.println("7. TẤT CẢ SUẤT CHIẾU");
        System.out.println("========================================");

        List<Showtime> showtimes =
                showtimeService.getAllShowtimes();

        for (Showtime showtime : showtimes) {
            System.out.println(showtime);
        }


        // ========================================
        // 8. SUẤT CHIẾU ĐANG HOẠT ĐỘNG
        // ========================================

        System.out.println("\n========================================");
        System.out.println("8. SUẤT CHIẾU ĐANG HOẠT ĐỘNG");
        System.out.println("========================================");

        List<Showtime> activeShowtimes =
                showtimeService.getActiveShowtimes();

        if (activeShowtimes.isEmpty()) {

            System.out.println(
                    "Không có suất chiếu đang hoạt động."
            );

        } else {

            for (Showtime showtime : activeShowtimes) {
                System.out.println(showtime);
            }
        }


        // ========================================
        // 9. TÌM SUẤT CHIẾU THEO ID
        // ========================================

        System.out.println("\n========================================");
        System.out.println("9. TÌM SUẤT CHIẾU ST01");
        System.out.println("========================================");

        Showtime showtime =
                showtimeService.findById("ST01");

        if (showtime != null) {
            System.out.println(showtime);
        } else {
            System.out.println(
                    "Không tìm thấy suất chiếu."
            );
        }


        // ========================================
        // 10. KIỂM TRA SUẤT CHIẾU ĐANG HOẠT ĐỘNG
        // ========================================

        System.out.println("\n========================================");
        System.out.println("10. KIỂM TRA ST01");
        System.out.println("========================================");

        boolean active =
                showtimeService.isActive("ST01");

        System.out.println(
                "ST01 đang hoạt động: " + active
        );


        // ========================================
        // 11. KIỂM TRA SUẤT CHIẾU CÓ TỒN TẠI
        // ========================================

        System.out.println("\n========================================");
        System.out.println("11. KIỂM TRA TỒN TẠI");
        System.out.println("========================================");

        System.out.println(
                "ST01 tồn tại: " +
                        showtimeService.exists("ST01")
        );

        System.out.println(
                "ST99 tồn tại: " +
                        showtimeService.exists("ST99")
        );


        // ========================================
        // 12. LẤY SUẤT CHIẾU THEO PHIM
        // ========================================

        System.out.println("\n========================================");
        System.out.println("12. SUẤT CHIẾU CỦA PHIM M01");
        System.out.println("========================================");

        List<Showtime> showtimesByMovie =
                showtimeService
                        .getShowtimesByMovie("M01");

        for (Showtime st : showtimesByMovie) {
            System.out.println(st);
        }


        // ========================================
        // KẾT THÚC
        // ========================================

        System.out.println("\n========================================");
        System.out.println("CHƯƠNG TRÌNH KẾT THÚC");
        System.out.println("========================================");
    }
}