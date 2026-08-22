package mapper;

import model.Customer;
import model.Movie;
import model.Seat;
import model.Showtime;
import model.Ticket;
import repository.CustomerRepository;
import repository.MovieRepository;
import repository.ShowtimeRepository;
import storage.TicketData;

public class TicketDataMapper {

    private CustomerRepository customerRepository;
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;

    public TicketDataMapper(CustomerRepository customerRepository,
                            MovieRepository movieRepository,
                            ShowtimeRepository showtimeRepository) {

        if (customerRepository == null) {
            throw new IllegalArgumentException(
                    "CustomerRepository không hợp lệ"
            );
        }

        if (movieRepository == null) {
            throw new IllegalArgumentException(
                    "MovieRepository không hợp lệ"
            );
        }

        if (showtimeRepository == null) {
            throw new IllegalArgumentException(
                    "ShowtimeRepository không hợp lệ"
            );
        }

        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
    }

    public Ticket toTicket(TicketData data) {

        if (data == null) {
            throw new IllegalArgumentException(
                    "TicketData không hợp lệ"
            );
        }

        // Tìm Customer
        Customer customer =
                customerRepository.findById(
                        data.getCustomerId()
                );

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy khách hàng: "
                            + data.getCustomerId()
            );
        }

        // Tìm Movie
        Movie movie =
                movieRepository.findById(
                        data.getMovieId()
                );

        if (movie == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy phim: "
                            + data.getMovieId()
            );
        }

        // Tìm Showtime
        Showtime showtime =
                showtimeRepository.findById(
                        data.getShowtimeId()
                );

        if (showtime == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy suất chiếu: "
                            + data.getShowtimeId()
            );
        }

        // Tìm Seat trong Showtime
        Seat seat =
                showtime.findSeatByNumber(
                        data.getSeatNumber()
                );

        if (seat == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy ghế: "
                            + data.getSeatNumber()
            );
        }

        // Tạo lại Ticket
        return new Ticket(
                data.getTicketId(),
                customer,
                movie,
                showtime,
                seat,
                data.getFinalPrice(),
                data.getPaymentStatus()
        );
    }
}