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
                    "TicketRepository khong hop le!"
            );
        }

        this.ticketRepository = ticketRepository;
    }

    // =========================
    // DAT VE
    // =========================

    public Ticket bookTicket(Customer customer,
                             Showtime showtime,
                             Seat seat) {

        // -------------------------
        // KIEM TRA KHACH HANG
        // -------------------------

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Khach hang khong ton tai!"
            );
        }

        // -------------------------
        // KIEM TRA SUAT CHIEU
        // -------------------------

        if (showtime == null) {
            throw new IllegalArgumentException(
                    "Suat chieu khong ton tai!"
            );
        }

        // -------------------------
        // KIEM TRA PHIM
        // -------------------------

        Movie movie = showtime.getMovie();

        if (movie == null) {
            throw new IllegalArgumentException(
                    "Suat chieu khong co phim!"
            );
        }

        String movieStatus = movie.getStatus();

        if (!"Đang chiếu".equalsIgnoreCase(movieStatus)
                && !"Dang chieu".equalsIgnoreCase(movieStatus)) {

            throw new IllegalArgumentException(
                    "Khong the dat ve phim da ngung chieu!"
            );
        }

        // -------------------------
        // KIEM TRA GHE
        // -------------------------

        if (seat == null) {
            throw new IllegalArgumentException(
                    "Ghe khong ton tai!"
            );
        }

        // Kiem tra ghe da dat chua
        if (seat.getSeatStatus() == SeatStatus.Booked) {
            throw new IllegalArgumentException(
                    "Ghe " + seat.getSeatNumber()
                            + " da duoc dat!"
            );
        }

        // -------------------------
        // CHON CHINH SACH GIA
        // -------------------------

        TicketPricePolicy policy;

        if (customer.getCustomerType() == CustomerType.STUDENT) {

            policy = new StudentPricePolicy();

        } else if (customer.getCustomerType() == CustomerType.VIP) {

            policy = new VipPricePolicy();

        } else {

            policy = new NormalCustomerPricePolicy();
        }

        // -------------------------
        // TINH GIA VE
        // -------------------------

        double finalPrice =
                policy.calculatePrice(showtime, seat);

        if (finalPrice <= 0) {
            throw new IllegalArgumentException(
                    "Gia ve phai lon hon 0!"
            );
        }

        // -------------------------
        // SINH MA VE
        // -------------------------

        String ticketId =
                IdGenerator.generateTicketId();

        // -------------------------
        // TAO TICKET
        // -------------------------

        Ticket ticket = new Ticket(
                ticketId,
                customer,
                movie,
                showtime,
                seat,
                finalPrice,
                PaymentStatus.UNPAID
        );

        // -------------------------
        // DAT GHE
        // -------------------------

        seat.bookASeat();

        // -------------------------
        // LUU VE
        // -------------------------

        ticketRepository.addTicket(ticket);

        return ticket;
    }

    // =========================
    // GETTER
    // =========================

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }
}