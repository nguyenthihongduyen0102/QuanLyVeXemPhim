package service;

import model.CinemaRoom;
import repository.CinemaRoomRepository;

import java.util.List;

public class CinemaRoomService {

    private final CinemaRoomRepository repository;

    public CinemaRoomService() {
        this.repository = new CinemaRoomRepository();
    }

    // =========================
    // LẤY TẤT CẢ PHÒNG
    // =========================

    public List<CinemaRoom> getAll() {
        return repository.findAll();
    }

    // =========================
    // TÌM PHÒNG THEO ID
    // =========================

    public CinemaRoom getById(String id) {

        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        return repository.findById(id);
    }

    // =========================
    // TÌM PHÒNG THEO TÊN
    // =========================

    public CinemaRoom getByName(String name) {

        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return repository.findByName(name);
    }

    // =========================
    // THÊM PHÒNG
    // =========================

    public String add(CinemaRoom room) {

        if (room == null) {
            return "Phòng không hợp lệ";
        }

        if (room.getId() == null ||
                room.getId().trim().isEmpty()) {

            return "ID phòng không được để trống";
        }

        if (room.getName() == null ||
                room.getName().trim().isEmpty()) {

            return "Tên phòng không được để trống";
        }

        if (room.getCapacity() <= 0) {
            return "Sức chứa phải lớn hơn 0";
        }

        // Kiểm tra trùng ID
        if (repository.findById(room.getId()) != null) {
            return "ID phòng đã tồn tại";
        }

        // Kiểm tra trùng tên
        if (repository.findByName(room.getName()) != null) {
            return "Tên phòng đã tồn tại";
        }

        repository.add(room);

        return "Thêm phòng thành công";
    }
}