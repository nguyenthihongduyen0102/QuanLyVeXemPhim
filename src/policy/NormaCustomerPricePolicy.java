package policy;

import model.Seat;

public class NormaCustomerPricePolicy implements TicketPricePolicy {
    @Override
    public double FinalTicketPrice(Seat seat, double baseFare){
        double Sum = baseFare + seat.getSurcharge();
        if(Sum <= 0){
            throw new IllegalArgumentException("Loi du lieu: Tong tien cuoi cung cua ban phai lon hon 0");
        }
        return Sum;
    }
}
//..//