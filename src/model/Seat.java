package model;

public class Seat {
    private String seatId; // mã ghế
    private String seatNumber; // số ghế
    private SeatType seatType; // loại ghế
    private SeatStatus seatStatus; // trạng thái ghế

    public Seat(){

    }

    public Seat(String seatId,
                String seatNumber,
                SeatType seatType,
                SeatStatus seatStatus) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatStatus = seatStatus;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public boolean isAvailable(){
        return seatStatus == SeatStatus.AVAILABLE;
    }

}
