package model;

import java.util.ArrayList;

public class CinemaRoom {
    private String roomId;
    private String roomName;
    private ArrayList<Seat> seats; // danh sách ghế gốc của phòng

    public CinemaRoom(){
        seats = new ArrayList<>();
    }

    public CinemaRoom(String roomId,
                      String roomName,
                      ArrayList<Seat> seats) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.seats = seats;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        if (seats == null) {
            this.seats = new ArrayList<>();
        } else {
            this.seats = seats;
        }
    }

    public void addSeat(Seat seat){
        if(seat != null){
            seats.add(seat);
        }
    }

    //hàm trả về 1 object Seat để kiểm tra xem ghế đó có tồn tại không
    public Seat findSeatByNumber(String seatNumber){//là số ghế người ngồi
        for(int i = 0; i < seats.size(); i++){ // nhập 1 giá trị thì nó sẽ duyệt toàn bộ danh sách ghi
            Seat seat = seats.get(i);
            if(seat.getSeatNumber().equalsIgnoreCase(seatNumber)){ // so sánh ghế vừa nhập với các ghế trong danh sách nếu phù hợp thì trả về ghế vừa nhập
                return seat;
            }
        }
        return null;
    }
}
