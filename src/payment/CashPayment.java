package payment;

import model.PaymentMethodType;
import model.Ticket;

public class CashPayment implements PaymentMethod {

    @Override
    public boolean pay(Ticket ticket) {
        System.out.println("Thanh toán tiền mặt thành công");
        System.out.println("Mã vé: " + ticket.getTicketId());
        return true;
    }

    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.CASH;
    }
}