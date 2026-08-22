package payment;

import model.PaymentMethodType;
import model.Ticket;

public class EWalletPayment implements PaymentMethod {

    @Override
    public boolean pay(Ticket ticket) {

        System.out.println("========== THANH TOÁN VÍ ĐIỆN TỬ ==========");
        System.out.println("Ví: MoMo");
        System.out.println("Mã vé: " + ticket.getTicketId());
        System.out.println("Số tiền: " + ticket.getFinalPrice());
        System.out.println("Thanh toán thành công.");

        return true;
    }

    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.EWALLET;
    }
}