// ==============================
// BOOKING.JS
// ==============================

// Giá một ghế
const PRICE_PER_SEAT = 80000;


// ==============================
// GIẢM GIÁ THEO LOẠI KHÁCH
// ==============================

const CUSTOMER_DISCOUNT = {
    NORMAL: 0,
    STUDENT: 0.10,
    VIP: 0.20
};


// ==============================
// DỮ LIỆU BOOKING ĐANG CHỌN
// ==============================

let selectedSeats = [];

let selectedShowtime = null;

let selectedCustomerType = null;

let selectedPaymentMethod = null;


// ==============================
// CHỌN GHẾ
// ==============================

// Hàm này được gọi từ seat.js
function setSelectedSeats(seats) {

    selectedSeats = [...seats];

    updateBookingInfo();
}


// ==============================
// CHỌN SUẤT CHIẾU
// ==============================

// booking.js chỉ nhận showtimeId
// Không quản lý thông tin suất chiếu

function setShowtime(showtimeId) {

    selectedShowtime = showtimeId;

    updateBookingInfo();
}


// ==============================
// CHỌN LOẠI KHÁCH
// ==============================

// booking.js chỉ nhận loại khách
// Thông tin khách hàng nằm ở customer.js

function setCustomerType(customerType) {

    selectedCustomerType = customerType;

    updateBookingInfo();
}


// ==============================
// CHỌN PHƯƠNG THỨC THANH TOÁN
// ==============================

function setPaymentMethod(paymentMethod) {

    selectedPaymentMethod = paymentMethod;
}


// ==============================
// LẤY TỶ LỆ GIẢM GIÁ
// ==============================

function getDiscountRate(customerType) {

    if (!customerType) {

        return 0;

    }

    return CUSTOMER_DISCOUNT[customerType] || 0;
}


// ==============================
// TÍNH GIÁ
// ==============================

function calculatePrice() {

    const seatCount =
        selectedSeats.length;


    const originalPrice =
        seatCount * PRICE_PER_SEAT;


    const discountRate =
        getDiscountRate(
            selectedCustomerType
        );


    const discount =
        originalPrice * discountRate;


    const finalPrice =
        originalPrice - discount;


    return {

        seatCount: seatCount,

        originalPrice: originalPrice,

        discountRate: discountRate,

        discount: discount,

        finalPrice: finalPrice

    };
}


// ==============================
// CẬP NHẬT THÔNG TIN BOOKING
// ==============================

function updateBookingInfo() {

    const price =
        calculatePrice();


    const seatElement =
        document.getElementById(
            "selected-seat"
        );


    const seatCountElement =
        document.getElementById(
            "seat-count"
        );


    const ticketPriceElement =
        document.getElementById(
            "ticket-price"
        );


    const discountElement =
        document.getElementById(
            "discount"
        );


    const totalPriceElement =
        document.getElementById(
            "total-price"
        );


    if (seatElement) {

        seatElement.textContent =
            selectedSeats.length > 0
                ? selectedSeats.join(", ")
                : "Chưa chọn";

    }


    if (seatCountElement) {

        seatCountElement.textContent =
            price.seatCount;

    }


    if (ticketPriceElement) {

        ticketPriceElement.textContent =
            PRICE_PER_SEAT.toLocaleString("vi-VN")
            + " VNĐ";

    }


    if (discountElement) {

        discountElement.textContent =
            price.discount.toLocaleString("vi-VN")
            + " VNĐ";

    }


    if (totalPriceElement) {

        totalPriceElement.textContent =
            price.finalPrice.toLocaleString("vi-VN")
            + " VNĐ";

    }

}


// ==============================
// TẠO BOOKING
// ==============================

function createBooking(
    customerId,
    showtimeId,
    seats,
    customerType,
    paymentMethod
) {

    // Kiểm tra customer

    if (!customerId) {

        alert(
            "Không tìm thấy khách hàng"
        );

        return null;
    }


    // Kiểm tra suất chiếu

    if (!showtimeId) {

        alert(
            "Vui lòng chọn suất chiếu"
        );

        return null;
    }


    // Kiểm tra ghế

    if (!seats || seats.length === 0) {

        alert(
            "Vui lòng chọn ít nhất một ghế"
        );

        return null;
    }


    // Kiểm tra loại khách

    if (!customerType) {

        alert(
            "Vui lòng chọn loại khách hàng"
        );

        return null;
    }


    // Kiểm tra thanh toán

    if (!paymentMethod) {

        alert(
            "Vui lòng chọn phương thức thanh toán"
        );

        return null;
    }


    // Tính tiền

    const originalPrice =
        seats.length * PRICE_PER_SEAT;


    const discountRate =
        getDiscountRate(customerType);


    const discount =
        originalPrice * discountRate;


    const finalPrice =
        originalPrice - discount;


    // Tạo booking

    const booking = {

        bookingId:
            "BK" +
            Date.now(),

        customerId:
        customerId,

        showtimeId:
        showtimeId,

        seatNumbers:
            [...seats],

        customerType:
        customerType,

        originalPrice:
        originalPrice,

        discount:
        discount,

        finalPrice:
        finalPrice,

        paymentMethod:
        paymentMethod

    };


    return booking;
}


// ==============================
// THANH TOÁN
// ==============================

function processPayment(
    paymentMethod,
    amount
) {

    switch (paymentMethod) {

        case "CASH":

            console.log(
                "Thanh toán tiền mặt"
            );

            break;


        case "BANK_TRANSFER":

            console.log(
                "Thanh toán chuyển khoản"
            );

            break;


        case "EWALLET":

            console.log(
                "Thanh toán ví điện tử"
            );

            break;


        case "CARD":

            console.log(
                "Thanh toán bằng thẻ"
            );

            break;


        default:

            alert(
                "Phương thức thanh toán không hợp lệ"
            );

            return false;

    }


    console.log(
        "Số tiền:",
        amount.toLocaleString("vi-VN"),
        "VNĐ"
    );


    return true;
}


// ==============================
// XÁC NHẬN ĐẶT VÉ
// ==============================

function confirmBooking(
    customerId,
    showtimeId,
    seats,
    customerType,
    paymentMethod
) {

    const booking =
        createBooking(
            customerId,
            showtimeId,
            seats,
            customerType,
            paymentMethod
        );


    if (!booking) {

        return false;

    }


    // Thanh toán

    const paymentSuccess =
        processPayment(
            booking.paymentMethod,
            booking.finalPrice
        );


    if (!paymentSuccess) {

        return false;

    }


    // In thông tin booking

    console.log(
        "========== BOOKING =========="
    );

    console.log(
        booking
    );


    alert(
        "Đặt vé thành công!" +

        "\n\nMã booking: " +
        booking.bookingId +

        "\nGhế: " +
        booking.seatNumbers.join(", ") +

        "\nSố lượng: " +
        booking.seatNumbers.length +

        "\nGiá gốc: " +
        booking.originalPrice
            .toLocaleString("vi-VN") +
        " VNĐ" +

        "\nGiảm giá: " +
        booking.discount
            .toLocaleString("vi-VN") +
        " VNĐ" +

        "\nThành tiền: " +
        booking.finalPrice
            .toLocaleString("vi-VN") +
        " VNĐ"
    );


    return true;
}
// ==============================
// ĐẶT VÉ + THANH TOÁN
// ==============================

function bookTicket(customerId) {

    // Kiểm tra khách hàng

    if (!customerId) {

        alert("Khách hàng không tồn tại");

        return;
    }


    // Kiểm tra suất chiếu

    if (!selectedShowtime) {

        alert("Vui lòng chọn suất chiếu");

        return;
    }


    // Kiểm tra ghế

    if (selectedSeats.length === 0) {

        alert("Vui lòng chọn ít nhất một ghế");

        return;
    }


    // ==============================
    // LẤY PHƯƠNG THỨC THANH TOÁN
    // ==============================

    const paymentMethod =
        document.getElementById(
            "payment-method"
        ).value;


    if (!paymentMethod) {

        alert(
            "Vui lòng chọn phương thức thanh toán"
        );

        return;
    }


    // ==============================
    // TÍNH TỔNG TIỀN
    // ==============================

    const totalPrice =
        selectedSeats.length *
        PRICE_PER_SEAT;


    // ==============================
    // DỮ LIỆU BOOKING
    // ==============================

    const bookingData = {

        customerId: customerId,

        showtimeId: selectedShowtime,

        seatNumbers: [...selectedSeats],

        totalPrice: totalPrice,

        paymentMethod: paymentMethod

    };


    console.log(
        "========== BOOKING =========="
    );

    console.log(bookingData);


    // ==============================
    // THANH TOÁN
    // ==============================

    processPayment(
        paymentMethod,
        totalPrice
    );

}


// ==============================
// XỬ LÝ THANH TOÁN
// ==============================

function processPayment(
    paymentMethod,
    totalPrice
) {

    let message = "";


    switch (paymentMethod) {

        case "CASH":

            message =
                "Thanh toán tiền mặt thành công!";

            break;


        case "BANK_TRANSFER":

            message =
                "Chuyển khoản thành công!";

            break;


        case "EWALLET":

            message =
                "Thanh toán ví điện tử thành công!";

            break;


        case "CARD":

            message =
                "Thanh toán bằng thẻ thành công!";

            break;


        default:

            alert(
                "Phương thức thanh toán không hợp lệ"
            );

            return;

    }


    // ==============================
    // TẠO MÃ VÉ
    // ==============================

    const ticketCode =
        "BK-" +
        Date.now().toString().slice(-6);


    // ==============================
    // HIỂN THỊ KẾT QUẢ
    // ==============================

    alert(

        message +

        "\n\nMã vé: " +
        ticketCode +

        "\nGhế: " +
        selectedSeats.join(", ") +

        "\nSố lượng: " +
        selectedSeats.length +

        "\nTổng tiền: " +
        totalPrice.toLocaleString("vi-VN") +
        " VNĐ"

    );

}