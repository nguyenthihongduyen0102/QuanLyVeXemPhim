package policy;

import model.Seat;
import model.Showtime;
// có 3 loại khác và mỗi giá tính khác nhau
public interface TicketPricePolicy {
    double calculatePrice(Showtime showtime,
                          Seat seat);
}
