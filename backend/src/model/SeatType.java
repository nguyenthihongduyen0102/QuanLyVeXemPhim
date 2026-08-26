package model;

public enum SeatType {
    Regularchair(0.0),
    Cuplechair(30.000),
    VIPchair(60.000);
    private final double surcharge;

    SeatType(double surcharge) {
        this.surcharge = surcharge;
    }

    public double getSurcharge() {
        return surcharge;
    }
}
