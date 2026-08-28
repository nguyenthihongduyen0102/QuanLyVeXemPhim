package model;

public class Ticket {

    private String ticketId;
    private Customer customer;
    private Movie movie;
    private Showtime showtime;
    private Seat seat;
    private double finalPrice;
    private PaymentStatus paymentStatus;

    // Constructor rỗng
    public Ticket() {
    }

    // Constructor đầy đủ
    public Ticket(String ticketId,
                  Customer customer,
                  Movie movie,
                  Showtime showtime,
                  Seat seat,
                  double finalPrice,
                  PaymentStatus paymentStatus) {

        setTicketId(ticketId);
        setCustomer(customer);
        setMovie(movie);
        setShowtime(showtime);
        setSeat(seat);
        setFinalPrice(finalPrice);
        setPaymentStatus(paymentStatus);
    }

    // =========================
    // GETTER / SETTER
    // =========================

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ma ve khong duoc de trong!"
            );
        }

        this.ticketId = ticketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Khach hang khong ton tai!"
            );
        }

        this.customer = customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException(
                    "Phim khong ton tai!"
            );
        }

        this.movie = movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        if (showtime == null) {
            throw new IllegalArgumentException(
                    "Suat chieu khong ton tai!"
            );
        }

        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException(
                    "Ghe khong ton tai!"
            );
        }

        this.seat = seat;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        if (finalPrice <= 0) {
            throw new IllegalArgumentException(
                    "Gia ve phai lon hon 0!"
            );
        }

        this.finalPrice = finalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            throw new IllegalArgumentException(
                    "Trang thai thanh toan khong hop le!"
            );
        }

        this.paymentStatus = paymentStatus;
    }

    // =========================
    // THANH TOAN
    // =========================

    public boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }

    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    // =========================
    // HIEN THI
    // =========================

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", customer=" + customer.getFullName() +
                ", movie=" + movie.getTitle() +
                ", showtime=" + showtime.getId() +
                ", seat=" + seat.getSeatNumber() +
                ", finalPrice=" + finalPrice +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}