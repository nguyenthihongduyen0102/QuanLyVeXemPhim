// 1. Cấu hình đường dẫn Base URL kết nối tới Java Spring Boot Backend
const API_BASE_URL = "http://localhost:8080/api";

// 2. Định nghĩa các Endpoint dùng chung cho các file JS khác
const API_ENDPOINTS = {
    MOVIES: `${API_BASE_URL}/movies`,
    SHOWTIMES: `${API_BASE_URL}/showtimes`,
    SEATS: `${API_BASE_URL}/seats`,
    CUSTOMERS: `${API_BASE_URL}/customers`,
    BOOKINGS: `${API_BASE_URL}/bookings`
};

// 3. Hàm gọi API tổng quát
async function fetchAPI(endpoint, options = {}) {
    try {
        const response = await fetch(endpoint, {
            headers: {
                "Content-Type": "application/json",
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage || `Lỗi HTTP: ${response.status}`);
        }

        // Tự động parse JSON nếu có dữ liệu trả về
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return await response.json();
        }
        return await response.text();
    } catch (error) {
        console.error("Lỗi API:", error);
        throw error;
    }
}

// 4. Khởi chạy toàn bộ ứng dụng khi trang Web tải xong
document.addEventListener("DOMContentLoaded", () => {
    console.log("🚀 Hệ thống Cinestar Frontend đã sẵn sàng!");

    const btnCloseModal = document.getElementById("btn-close-modal");
    const bookingModal = document.getElementById("booking-modal");

    if (btnCloseModal && bookingModal) {
        btnCloseModal.addEventListener("click", () => {
            bookingModal.style.display = "none";
        });
    }
});