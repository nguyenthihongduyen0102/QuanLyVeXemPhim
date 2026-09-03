package handler;

import model.CinemaRoom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CinemaRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/cinemarooms")
public class CinemaRoomHandler {

    private final CinemaRoomService service;

    public CinemaRoomHandler() {
        this.service = new CinemaRoomService();
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public ResponseEntity<List<CinemaRoom>> getAll() {

        List<CinemaRoom> rooms =
                service.getAll();

        return ResponseEntity.ok(rooms);
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String id) {

        CinemaRoom room =
                service.getById(id);

        if (room == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phòng");
        }

        return ResponseEntity.ok(room);
    }

    // =========================
    // SEARCH BY NAME
    // =========================

    @GetMapping("/search")
    public ResponseEntity<?> getByName(
            @RequestParam String name) {

        CinemaRoom room =
                service.getByName(name);

        if (room == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phòng");
        }

        return ResponseEntity.ok(room);
    }

    // =========================
    // ADD
    // =========================

    @PostMapping
    public ResponseEntity<String> add(
            @RequestBody CinemaRoom room) {

        String result =
                service.add(room);

        if (result.equals("Thêm phòng thành công")) {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }
}