package qlxephim.model;

public class CinemaRoom {
    private String id;
    private String name;
    private int capacity;

    public CinemaRoom(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

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
                "%s - %s (%d ghế)",
                id, name, capacity
        );
    }
}