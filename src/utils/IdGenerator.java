package utils;

import java.util.UUID;

public class IdGenerator {
    // Tự động sinh ID vé ngắn gọn
    public static String generateTicketId() {
        return "TK-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    // Tự động sinh ID khách hàng
    public static String generateCustomerId() {
        return "C-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    // Tự động sinh ID chung theo tiền tố
    public static String generateWithPrefix(String prefix, long sequence) {
        return String.format("%s%03d", prefix.toUpperCase(), sequence);
    }
}
