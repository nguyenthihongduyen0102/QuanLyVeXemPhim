package service;
import model.Seat;
import model.SeatStatus;
import java.util.ArrayList;
import java.util.List;
public class SeatService {
    private List<Seat> seatList;
    public SeatService() {
        this.seatList = new ArrayList<>();
    }
    public SeatService(List<Seat> seatList) {
        this.seatList = seatList;
    }
    public void addSeat(Seat seat) {
        if (seat != null) {
            this.seatList.add(seat);
        }
    }
    public Seat findSeatByCode(String seatCode) {
        for (Seat seat : seatList) {
            if (seat.getSeatCode().equalsIgnoreCase(seatCode)) {
                return seat;
            }
        }
        return null;
    }
    public boolean processBooking(String seatCode) {
        Seat seat = findSeatByCode(seatCode);
            if (seat == null) {
                System.out.println("Hệ thống: Mã ghế " + seatCode + " không tồn tại!");
                return false;
            }
            try {
                seat.bookASeat();
                return true;
            } catch (IllegalStateException e) {
                System.out.println("Hệ thống: Đặt ghế thất bại. " + e.getMessage());
                return false;
            }
    }
    public List<Seat> getSeatList() {
        return seatList;
    }
}
//..//