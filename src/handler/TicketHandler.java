package handler;

import model.Ticket;
import repository.TicketRepository;

import java.util.ArrayList;

public class TicketHandler {

    private TicketRepository ticketRepository;

    public TicketHandler(TicketRepository ticketRepository) {
        if (ticketRepository == null) {
            throw new IllegalArgumentException(
                    "TicketRepository không hợp lệ"
            );
        }

        this.ticketRepository = ticketRepository;
    }

    // Thêm vé
    public void addTicket(Ticket ticket) {
        ticketRepository.addTicket(ticket);
    }

    // Lấy tất cả vé
    public ArrayList<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    // Tìm vé theo mã
    public Ticket findTicketById(String ticketId) {
        return ticketRepository.findById(ticketId);
    }

    // Xóa vé
    public boolean removeTicket(String ticketId) {
        return ticketRepository.removeTicket(ticketId);
    }

    // Đếm số vé
    public int getTicketCount() {
        return ticketRepository.getTicketCount();
    }

    // Kiểm tra danh sách vé có rỗng không
    public boolean isEmpty() {
        return ticketRepository.isEmpty();
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }
}