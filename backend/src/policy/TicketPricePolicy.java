package policy;

import model.Seat;

public interface TicketPricePolicy {
    public double FinalTicketPrice(Seat seat, double baseFare);
}
