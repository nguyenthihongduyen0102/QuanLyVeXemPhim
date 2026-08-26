package utils;

import java.util.regex.Pattern;

public class Validator {
    private static final String PHONE_REGEX = "^(0[3|5|7|8|9])+([0-9]{8})$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Kiểm tra chuỗi có bị rỗng không
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    // Kiểm tra số điện thoại hợp lệ
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) return false;
        return Pattern.matches(PHONE_REGEX, phone.trim());
    }

    // Kiểm tra email hợp lệ
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }

    // Kiểm tra giá tiền hợp lệ
    public static boolean isValidPrice(double price) {
        return price > 0;
    }
}
