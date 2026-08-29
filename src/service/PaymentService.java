package service;

import model.Payment;
import model.PaymentStatus;
import model.Ticket;
import payment.PaymentMethod;
import repository.PaymentRepository;
import utils.IdGenerator;

public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        if (paymentRepository == null) {
            throw new IllegalArgumentException(
                    "PaymentRepository không hợp lệ"
            );
        }

        this.paymentRepository = paymentRepository;
    }

    public void payTicket(Ticket ticket,
                          PaymentMethod paymentMethod) {

        if (ticket == null) {
            throw new IllegalArgumentException("Vé không tồn tại");
        }

        if (ticket.isPaid()) {
            throw new IllegalArgumentException(
                    "Vé đã được thanh toán"
            );
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException(
                    "Phương thức thanh toán không hợp lệ"
            );
        }

        boolean result = paymentMethod.pay(ticket);

        if (!result) {
            throw new IllegalArgumentException(
                    "Thanh toán thất bại"
            );
        }

        String paymentId =
                IdGenerator.generatePaymentId();

        String transactionCode =
                IdGenerator.generateTransactionCode();

        Payment payment = new Payment(
                paymentId,
                ticket,
                ticket.getFinalPrice(),
                paymentMethod.getType(),
                transactionCode,
                PaymentStatus.PAID
        );

        ticket.markAsPaid();

        paymentRepository.addPayment(payment);
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }
}