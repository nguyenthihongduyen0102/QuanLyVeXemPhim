package storage;

import model.PaymentStatus;

public class TicketData {

    private String ticketId;
    private String customerId;
    private String movieId;
    private String showtimeId;
    private String seatNumber;
    private double finalPrice;
    private PaymentStatus paymentStatus;

    public TicketData() {
    }

    public TicketData(String ticketId,
                      String customerId,
                      String movieId,
                      String showtimeId,
                      String seatNumber,
                      double finalPrice,
                      PaymentStatus paymentStatus) {

        this.ticketId = ticketId;
        this.customerId = customerId;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}