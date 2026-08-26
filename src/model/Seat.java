package model;

public class Seat {
    private String seatCode;
    private String seatNumber;
    private SeatType typeOfSeat;
    private SeatStatus seatStatus;
    private String roomId;

    public Seat(String seatCode, String seatNumber, SeatType typeOfSeat, SeatStatus seatStatus,String roomId) {
        this.seatCode = seatCode;
        this.seatNumber = seatNumber;
        this.typeOfSeat = typeOfSeat;
        this.seatStatus = SeatStatus.Available;
        this.roomId = roomId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getTypeOfSeat() {
        return typeOfSeat;
    }

    public void setTypeOfSeat(SeatType typeOfSeat) {
        this.typeOfSeat = typeOfSeat;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public void bookASeat(){
        if(this.seatStatus == SeatStatus.Booked){
            throw new IllegalStateException("Loi du lieu: Ghe so: "+ seatNumber + "da co nguoi dat!!");
        }
        this.seatStatus = SeatStatus.Booked;
    }
    public double getSurcharge(){
        return this.typeOfSeat != null ? this.typeOfSeat.getSurcharge() : 0.0;
    }
}
