package policy;

import model.Seat;

public class StudentCustomerPricePolicy implements TicketPricePolicy {
    @Override
    public double FinalTicketPrice(Seat seat, double baseFare) {
        double Sum = baseFare + seat.getSurcharge();
        double FinalSum = Sum * 0.4;
        if(FinalSum <= 0){
            throw new IllegalArgumentException("Loi du lieu: Tong tien cuoi cung cua ban phai lon hon 0");}
        return FinalSum;
    }
}
//.//