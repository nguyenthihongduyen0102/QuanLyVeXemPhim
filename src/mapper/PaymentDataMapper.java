package mapper;

import model.Payment;
import model.Ticket;
import repository.TicketRepository;
import storage.PaymentData;

public class PaymentDataMapper {

    private TicketRepository ticketRepository;

    public PaymentDataMapper(TicketRepository ticketRepository) {

        if (ticketRepository == null) {
            throw new IllegalArgumentException(
                    "TicketRepository không hợp lệ"
            );
        }

        this.ticketRepository = ticketRepository;
    }

    public Payment toPayment(PaymentData data) {

        if (data == null) {
            throw new IllegalArgumentException(
                    "PaymentData không hợp lệ"
            );
        }

        Ticket ticket =
                ticketRepository.findById(
                        data.getTicketId()
                );

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy vé: "
                            + data.getTicketId()
            );
        }

        return new Payment(
                data.getPaymentId(),
                ticket,
                data.getAmount(),
                data.getPaymentMethod(),
                data.getTransactionCode(),
                data.getStatus()
        );
    }
}