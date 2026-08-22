package model;

import java.util.ArrayList;

public class Showtime {
    private String showtimeId; // mã suất chiếu
    private Movie movie; // phim
    private CinemaRoom cinemaRoom; // phòng chiếu
    private String startTime; // thời gian bắt đầu
    private String endTime; // thời gian kết thúc
    private double basePrice; // giá vé cơ bản
    private ArrayList<Seat> seats; //danh sách ghế riêng của mỗi suất chiếu

    public Showtime(){
        this.seats = new ArrayList<>();
    }

    public Showtime(String showtimeId,
                    Movie movie,
                    CinemaRoom cinemaRoom,
                    String startTime,
                    String endTime,
                    double basePrice,
                    ArrayList<Seat> seats) {
        this.showtimeId= showtimeId;
        this.movie = movie;
        this.cinemaRoom = cinemaRoom;
        this.startTime = startTime;
        this.endTime = endTime;
        setBasePrice(basePrice);
        setSeats(seats);
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice){
        if(basePrice > 0) {
            this.basePrice = basePrice;
        }else throw new IllegalArgumentException("Giá vé phải lớn hơn 0");
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        if(seats == null) {
            this.seats = new ArrayList<>();
        } else {
            this.seats = seats;
        }
    }

    public void addSeat(Seat seat){
        if(seat != null) {
            seats.add(seat);
        }
    }

    // kiểm tra xem ghế có tồn tại không nếu có tồn tại trả về object seat không thì trả về null
    public Seat findSeatByNumber(String seatNumber){
        for(int i = 0; i < seats.size(); i++){
            Seat seat = seats.get(i);
            if(seat.getSeatNumber().equalsIgnoreCase(seatNumber)){
                return seat;
            }
        }
        return null;
    }

    // kiểm tra xem suất chiếu có còn ghế hay không
    public boolean hasAvailableSeat(){
        for (int i = 0; i < seats.size(); i++){
            Seat seat = seats.get(i);
            if(seat.isAvailable()){
                return true;
            }
        }
        return false;
    }

    // kiểm tra xem phim đó có tồn tại hay không
    public boolean isAvailableForBooking(){
        return movie != null && movie.isNowShowing();
    }

}
