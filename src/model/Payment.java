package model;

public class Payment {
    private String paymentId; // mã thanh toán
    private Ticket ticket; // vé được thanh toán
    private double amount; // số tiền
    private PaymentMethodType paymentMethod; // CASH, BANK, EWALLET...
    private String transactionCode; // mã giao dịch
    private PaymentStatus status;

    public Payment(){}

    public Payment(String paymentId,
                   Ticket ticket,
                   double amount,
                   PaymentMethodType paymentMethod,
                   String transactionCode,
                   PaymentStatus status) {
        this.paymentId = paymentId;
        setTicket(ticket);
        setAmount(amount);
        setPaymentMethod(paymentMethod);
        setTransactionCode(transactionCode);
        setStatus(status);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket){
        if(ticket == null){
            throw new IllegalArgumentException("Vé không tồn tại");
        }
        this.ticket = ticket;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount > 0) {
            this.amount = amount;
        }else {
            throw new IllegalArgumentException("Số tiền phải lớn hơn 0");
        }
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
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

    public void setStatus(PaymentStatus status){
        if(status == null){
            throw new IllegalArgumentException("Trạng thái thanh toán không hợp lệ");
        }
        this.status = status;
    }
    public boolean isPaid(){
        return status == PaymentStatus.PAID;
    }
    public void markAsPaid() {
        status = PaymentStatus.PAID;
    }
    public void markAsUnpaid() {
        status = PaymentStatus.UNPAID;
    }
}

