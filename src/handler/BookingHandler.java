package handler;

import model.Customer;
import model.Ticket;
import model.Showtime;
import service.BookingService;

public class BookingHandler {

    private BookingService bookingService;

    public BookingHandler(BookingService bookingService) {
        if (bookingService == null) {
            throw new IllegalArgumentException(
                    "BookingService không hợp lệ"
            );
        }

        this.bookingService = bookingService;
    }

    public Ticket bookTicket(Customer customer,
                             Showtime showtime,
                             String seatNumber) {

        return bookingService.bookTicket(
                customer,
                showtime,
                seatNumber
        );
    }
}