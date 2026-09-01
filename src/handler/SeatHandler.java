package handler;
import model.Seat;
import policy.TicketPricePolicy;
import service.PriceService;
import service.SeatService;

import java.util.Scanner;
public class SeatHandler {
    private  final SeatService seatService;
    private  final PriceService priceService;
    private final Scanner scanner;
    public SeatHandler(SeatService seatService, PriceService priceService ){
        this.seatService = seatService;
        this.priceService = priceService;
        this.scanner = new Scanner(System.in);
    }
    public void handleSeatBookingProcess(TicketPricePolicy policy, double baseFare) {
        System.out.println("\n=== HỆ THỐNG ĐẶT VÉ XEM PHIM ===");
        System.out.print("Vui lòng nhập mã ghế bạn muốn chọn: ");
        String seatCodeInput = scanner.nextLine().trim();
        boolean bookingResult = seatService.processBooking(seatCodeInput);
        if (bookingResult) {
            Seat bookedSeat = seatService.findSeatByCode(seatCodeInput);
            try {
                double finalPrice = priceService.calculateTicketPrice(policy, bookedSeat, baseFare);

                System.out.println("\n--------------------------------");
                System.out.println("      HÓA ĐƠN ĐẶT VÉ THÀNH CÔNG   ");
                System.out.println("Mã ghế: " + bookedSeat.getSeatCode());
                System.out.println("Loại ghế: " + bookedSeat.getTypeOfSeat());
                System.out.println("Phụ thu loại ghế: " + bookedSeat.getSurcharge() + " VND");
                System.out.println("Tổng số tiền thanh toán: " + finalPrice + " VND");
                System.out.println("--------------------------------");

            } catch (IllegalArgumentException e) {
                System.out.println("Hệ thống tính giá gặp lỗi: " + e.getMessage());
            }
        } else {
            System.out.println("Hệ thống: Tiến trình đặt vé thất bại. Vui lòng thử lại!");
        }
    }
}

