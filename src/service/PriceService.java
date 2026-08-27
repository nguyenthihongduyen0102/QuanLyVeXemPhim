package service;
import model.Seat;
import policy.TicketPricePolicy;
public class PriceService {
        public double calculateTicketPrice(TicketPricePolicy policy, Seat seat, double baseFare) {
            if (policy == null) {
                throw new IllegalArgumentException("Loi he thong: Chinh sach gia khong duoc de trong!");
            }
            if (seat == null) {
                throw new IllegalArgumentException("Loi he thong: Thong tin ghe khong duoc de trong!");
            }
            return policy.FinalTicketPrice(seat, baseFare);
        }
    }
