package handler;

import model.Ticket;
import payment.PaymentMethod;
import service.PaymentService;

public class PaymentHandler {

    private PaymentService paymentService;

    public PaymentHandler(PaymentService paymentService) {
        if (paymentService == null) {
            throw new IllegalArgumentException(
                    "PaymentService không hợp lệ"
            );
        }

        this.paymentService = paymentService;
    }

    public void payTicket(Ticket ticket,
                          PaymentMethod paymentMethod) {

        paymentService.payTicket(
                ticket,
                paymentMethod
        );
    }
}