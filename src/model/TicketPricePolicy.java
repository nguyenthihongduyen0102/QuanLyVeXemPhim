package model;

public interface TicketPricePolicy {
    public double FinalTicketPrice(Seat seat,double baseFare);
}
