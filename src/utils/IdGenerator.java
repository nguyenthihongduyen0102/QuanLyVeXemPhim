package utils;

public class IdGenerator {

    private static int customerCount = 1;
    private static int movieCount = 1;
    private static int showtimeCount = 1;
    private static int roomCount = 1;
    private static int seatCount = 1;
    private static int ticketCount = 1;
    private static int paymentCount = 1;
    private static int transactionCount = 1;

    // Mã khách hàng: C001
    public static String generateCustomerId() {
        return String.format("C%03d", customerCount++);
    }

    // Mã phim: M001
    public static String generateMovieId() {
        return String.format("M%03d", movieCount++);
    }

    // Mã suất chiếu: ST001
    public static String generateShowtimeId() {
        return String.format("ST%03d", showtimeCount++);
    }

    // Mã phòng: R001
    public static String generateRoomId() {
        return String.format("R%03d", roomCount++);
    }

    // Mã ghế: S001
    public static String generateSeatId() {
        return String.format("S%03d", seatCount++);
    }

    // Mã vé: T001
    public static String generateTicketId() {
        return String.format("T%03d", ticketCount++);
    }

    // Mã thanh toán: P001
    public static String generatePaymentId() {
        return String.format("P%03d", paymentCount++);
    }

    // Mã giao dịch: GD00001
    public static String generateTransactionCode() {
        return String.format("GD%05d", transactionCount++);
    }
}