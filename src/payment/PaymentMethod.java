package payment;

import model.PaymentMethodType;
import model.Ticket;

public interface PaymentMethod {
    boolean pay(Ticket ticket);

    PaymentMethodType getType();
}