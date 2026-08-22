package service;

import model.*;
import policy.NormalCustomerPricePolicy;
import policy.StudentPricePolicy;
import policy.TicketPricePolicy;
import policy.VipPricePolicy;
import repository.TicketRepository;
import utils.IdGenerator;

public class BookingService {

    private TicketRepository ticketRepository;

    public BookingService(TicketRepository ticketRepository) {
        if (ticketRepository == null) {
            throw new IllegalArgumentException(
                    "TicketRepository không hợp lệ"
            );
        }

        this.ticketRepository = ticketRepository;
    }

    public Ticket bookTicket(Customer customer,
                             Showtime showtime,
                             String seatNumber) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Khách hàng không tồn tại"
            );
        }

        if (showtime == null) {
            throw new IllegalArgumentException(
                    "Suất chiếu không tồn tại"
            );
        }

        if (!showtime.isAvailableForBooking()) {
            throw new IllegalArgumentException(
                    "Phim hiện tại không mở bán"
            );
        }

        if (!showtime.hasAvailableSeat()) {
            throw new IllegalArgumentException(
                    "Suất chiếu đã hết ghế"
            );
        }

        if (seatNumber == null || seatNumber.isEmpty()) {
            throw new IllegalArgumentException(
                    "Số ghế không được để trống"
            );
        }

        Seat seat = showtime.findSeatByNumber(seatNumber);

        if (seat == null) {
            throw new IllegalArgumentException(
                    "Ghế không tồn tại"
            );
        }

        if (!seat.isAvailable()) {
            throw new IllegalArgumentException(
                    "Ghế đã được đặt"
            );
        }

        // Chọn chính sách giá
        TicketPricePolicy policy;

        if (customer.isStudent()) {
            policy = new StudentPricePolicy();

        } else if (customer.isVip()) {
            policy = new VipPricePolicy();

        } else {
            policy = new NormalCustomerPricePolicy();
        }

        // Tính giá vé
        double finalPrice =
                policy.calculatePrice(showtime, seat);

        // Tạo mã vé
        String ticketId =
                IdGenerator.generateTicketId();

        // Tạo Ticket
        Ticket ticket = new Ticket(
                ticketId,
                customer,
                showtime.getMovie(),
                showtime,
                seat,
                finalPrice,
                PaymentStatus.UNPAID
        );

        // Đặt ghế
        seat.setSeatStatus(SeatStatus.BOOKED);

        // Lưu Ticket thông qua Repository
        ticketRepository.addTicket(ticket);

        return ticket;
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }
}