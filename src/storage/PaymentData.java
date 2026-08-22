package storage;

import model.PaymentMethodType;
import model.PaymentStatus;

public class PaymentData {

    private String paymentId;
    private String ticketId;
    private double amount;
    private PaymentMethodType paymentMethod;
    private String transactionCode;
    private PaymentStatus status;

    public PaymentData() {
    }

    public PaymentData(String paymentId,
                       String ticketId,
                       double amount,
                       PaymentMethodType paymentMethod,
                       String transactionCode,
                       PaymentStatus status) {

        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionCode = transactionCode;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}