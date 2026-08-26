package model;

public class CinemaRoom {

    private String id;
    private String name;
    private int capacity;

    public CinemaRoom(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format(
                "Phòng: %s | Tên phòng: %s | Sức chứa: %d",
                id,
                name,
                capacity
        );
    }
}