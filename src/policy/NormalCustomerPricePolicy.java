package policy;

import model.Seat;
import model.SeatType;
import model.Showtime;

public class NormalCustomerPricePolicy implements TicketPricePolicy{
    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        double price = showtime.getBasePrice();

        if(seat.getSeatType() == SeatType.DOUBLE){
            price += 20000;
        }else if(seat.getSeatType() == SeatType.VIP) {
            price += 30000;
        }
        return price;
    }
}
