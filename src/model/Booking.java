package model;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    private String bookingId;
    private Customer customer;
    private Showtime showtime;
    private List<Seat> seats;
    private double totalPrice;

    public Booking(String bookingId,
                   Customer customer,
                   Showtime showtime,
                   List<Seat> seats) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma dat ve khong duoc de trong!");
        }

        if (customer == null) {
            throw new IllegalArgumentException("Khach hang khong ton tai!");
        }

        if (showtime == null) {
            throw new IllegalArgumentException("Suat chieu khong ton tai!");
        }

        if (seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Phai chon it nhat mot ghe!");
        }

        this.bookingId = bookingId;
        this.customer = customer;
        this.showtime = showtime;
        this.seats = new ArrayList<>(seats);

        calculateTotalPrice();
    }

    // =========================
    // GETTER
    // =========================

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // =========================
    // TINH GIA VE
    // =========================

    private void calculateTotalPrice() {

        Movie movie = showtime.getMovie();

        if (movie == null) {
            throw new IllegalStateException(
                    "Suat chieu khong co phim!"
            );
        }

        // Khong cho dat phim da ngung chieu
        String status = movie.getStatus();

        if (!"Đang chiếu".equalsIgnoreCase(status)
                && !"Dang chieu".equalsIgnoreCase(status)) {

            throw new IllegalStateException(
                    "Khong the dat ve phim da ngung chieu!"
            );
        }

        // Gia ve co ban tam thoi
        double basePrice = 100000;

        double total = 0;

        for (Seat seat : seats) {

            if (seat == null) {
                throw new IllegalArgumentException(
                        "Ghe khong ton tai!"
                );
            }

            // Khong cho dat ghe da dat
            if (seat.getSeatStatus() == SeatStatus.Booked) {
                throw new IllegalStateException(
                        "Ghe " + seat.getSeatNumber()
                                + " da duoc dat!"
                );
            }

            // Gia ve = gia co ban + phu phi ghe
            total += basePrice + seat.getSurcharge();
        }

        // =========================
        // GIAM GIA
        // =========================

        if (customer.getCustomerType() == CustomerType.STUDENT) {

            // Sinh vien giam 10%
            total *= 0.90;

        } else if (customer.getCustomerType() == CustomerType.VIP) {

            // VIP giam 20%
            total *= 0.80;
        }

        this.totalPrice = total;
    }

    // =========================
    // XAC NHAN DAT VE
    // =========================

    public void confirmBooking() {

        // Kiem tra tat ca ghe truoc
        for (Seat seat : seats) {

            if (seat == null) {
                throw new IllegalArgumentException(
                        "Ghe khong ton tai!"
                );
            }

            if (seat.getSeatStatus() == SeatStatus.Booked) {
                throw new IllegalStateException(
                        "Ghe " + seat.getSeatNumber()
                                + " da duoc dat!"
                );
            }
        }

        // Tat ca ghe hop le -> dat ghe
        for (Seat seat : seats) {
            seat.bookASeat();
        }
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {

        StringBuilder seatInfo = new StringBuilder();

        for (Seat seat : seats) {
            seatInfo.append(seat.getSeatNumber())
                    .append(" ");
        }

        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", customer=" + customer.getFullName() +
                ", showtime=" + showtime.getId() +
                ", seats=" + seatInfo +
                ", totalPrice=" + totalPrice +
                '}';
    }
}