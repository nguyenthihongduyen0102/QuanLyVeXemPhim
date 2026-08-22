package payment;

import model.PaymentMethodType;
import model.Ticket;

public class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean pay(Ticket ticket) {

        System.out.println("========== THÔNG TIN CHUYỂN KHOẢN ==========");
        System.out.println("Ngân hàng: MB Bank");
        System.out.println("STK: 0123456789");
        System.out.println("Tên TK: RAP CHIEU PHIM ABC");
        System.out.println("Số tiền: " + ticket.getFinalPrice());
        System.out.println("Nội dung: " + ticket.getTicketId());

        return true;
    }

    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.BANK_TRANSFER;
    }
}