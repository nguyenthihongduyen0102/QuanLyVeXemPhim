package policy;

import model.Seat;
import model.SeatType;
import model.Showtime;

public class StudentPricePolicy implements  TicketPricePolicy{
    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        // tính giá như normal customer
        NormalCustomerPricePolicy normal = new NormalCustomerPricePolicy();
        double price = normal.calculatePrice(showtime,seat);
        // giảm 20% cho sinh viên
        price *= 0.8;
        return price;
    }
}
