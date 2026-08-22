package policy;

import model.Seat;
import model.Showtime;

public class VipPricePolicy implements TicketPricePolicy{
    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        NormalCustomerPricePolicy normal = new NormalCustomerPricePolicy();
        double price = normal.calculatePrice(showtime,seat);
        //giảm 10% khách vip
        price = price * 0.9;
        return price;
    }
}
