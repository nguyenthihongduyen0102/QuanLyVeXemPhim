package model;

public class Ticket {
    private String ticketId; // mã vé
    private Customer customer; // thông tin khách hàng
    private Movie movie; // thông tin phim
    private Showtime showtime; // thông tin suất chiếu
    private Seat seat; // thông tin ghế
    private double finalPrice; // giá vé cuối cùng
    private PaymentStatus paymentStatus; // trạng thái thanh toán

    public Ticket(){}

    public Ticket(String ticketId,
                  Customer customer,
                  Movie movie,
                  Showtime showtime,
                  Seat seat,
                  double finalPrice,
                  PaymentStatus paymentStatus) {
        this.ticketId = ticketId;
        this.customer = customer;
        this.movie = movie;
        this.showtime = showtime;
        this.seat = seat;
        setFinalPrice(finalPrice);
        this.paymentStatus = paymentStatus;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        if (finalPrice > 0) {
            this.finalPrice = finalPrice;
        } else throw new IllegalArgumentException("Giá vé phải lớn hơn 0");
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // sau này kiểm tra không cần dùng ticket.getPayment
    public boolean isPaid(){
        return paymentStatus == PaymentStatus.PAID;
    }

    // khi thanh toán thành công thì có thể đánh dấu trạng thái ticket.markPaid thay vì phải dùng set thay đổi tình trạng thanh toán
    public void markAsPaid(){
        this.paymentStatus = PaymentStatus.PAID;
    }
}
