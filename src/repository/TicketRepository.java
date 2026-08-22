package repository;

import mapper.TicketDataMapper;
import model.Ticket;
import storage.TicketData;
import storage.TicketJsonStorage;

import java.util.ArrayList;

public class TicketRepository {

    private ArrayList<Ticket> tickets = new ArrayList<>();

    private TicketJsonStorage storage;
    private TicketDataMapper mapper;

    public TicketRepository(TicketJsonStorage storage,
                            TicketDataMapper mapper) {

        if (storage == null) {
            throw new IllegalArgumentException(
                    "TicketJsonStorage không hợp lệ"
            );
        }

        if (mapper == null) {
            throw new IllegalArgumentException(
                    "TicketDataMapper không hợp lệ"
            );
        }

        this.storage = storage;
        this.mapper = mapper;

        loadFromStorage();
    }

    // Đọc Ticket từ file JSON khi khởi động
    private void loadFromStorage() {

        ArrayList<TicketData> dataList =
                storage.load();

        for (TicketData data : dataList) {

            try {
                Ticket ticket =
                        mapper.toTicket(data);

                tickets.add(ticket);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Không thể tải vé "
                                + data.getTicketId()
                                + ": "
                                + e.getMessage()
                );
            }
        }
    }

    // Thêm Ticket
    public void addTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Vé không hợp lệ"
            );
        }

        if (ticket.getTicketId() == null ||
                ticket.getTicketId().isEmpty()) {

            throw new IllegalArgumentException(
                    "Mã vé không hợp lệ"
            );
        }

        if (findById(ticket.getTicketId()) != null) {

            throw new IllegalArgumentException(
                    "Mã vé đã tồn tại"
            );
        }

        tickets.add(ticket);

        // Lưu lại toàn bộ danh sách xuống JSON
        storage.save(tickets);
    }

    // Lấy tất cả Ticket
    public ArrayList<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    // Tìm Ticket theo ID
    public Ticket findById(String ticketId) {

        if (ticketId == null ||
                ticketId.isEmpty()) {

            return null;
        }

        for (Ticket ticket : tickets) {

            if (ticket.getTicketId()
                    .equalsIgnoreCase(ticketId)) {

                return ticket;
            }
        }

        return null;
    }

    // Xóa Ticket
    public boolean removeTicket(String ticketId) {

        Ticket ticket = findById(ticketId);

        if (ticket != null) {

            tickets.remove(ticket);

            // Cập nhật lại JSON
            storage.save(tickets);

            return true;
        }

        return false;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }

    public TicketJsonStorage getStorage() {
        return storage;
    }

    public TicketDataMapper getMapper() {
        return mapper;
    }
}