package model;

public class Payment {

    private String paymentId;
    private Ticket ticket;
    private double amount;
    private PaymentMethodType paymentMethod;
    private String transactionCode;
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(String paymentId,
                   Ticket ticket,
                   double amount,
                   PaymentMethodType paymentMethod,
                   String transactionCode,
                   PaymentStatus status) {

        setPaymentId(paymentId);
        setTicket(ticket);
        setAmount(amount);
        setPaymentMethod(paymentMethod);
        setTransactionCode(transactionCode);
        setStatus(status);
    }

    // =========================
    // GETTER / SETTER
    // =========================

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ma thanh toan khong duoc de trong!"
            );
        }

        this.paymentId = paymentId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ve khong ton tai!"
            );
        }

        this.ticket = ticket;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "So tien phai lon hon 0!"
            );
        }

        this.amount = amount;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {

        if (paymentMethod == null) {
            throw new IllegalArgumentException(
                    "Phuong thuc thanh toan khong hop le!"
            );
        }

        this.paymentMethod = paymentMethod;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {

        if (status == null) {
            throw new IllegalArgumentException(
                    "Trang thai thanh toan khong hop le!"
            );
        }

        this.status = status;
    }

    // =========================
    // THANH TOAN
    // =========================

    public boolean isPaid() {
        return status == PaymentStatus.PAID;
    }

    public void markAsPaid() {
        this.status = PaymentStatus.PAID;

        // Thanh toan thanh cong -> cap nhat trang thai ve
        if (ticket != null) {
            ticket.markAsPaid();
        }
    }

    public void markAsUnpaid() {
        this.status = PaymentStatus.UNPAID;
    }

    // =========================
    // HIEN THI
    // =========================

    @Override
    public String toString() {

        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", ticket=" + ticket.getTicketId() +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", transactionCode='" + transactionCode + '\'' +
                ", status=" + status +
                '}';
    }
}