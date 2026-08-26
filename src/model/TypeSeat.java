package model;

public enum TypeSeat {
    Regularchair(0.0),
    Cuplechair(30.000),
    VIPchair(60.000);
    private final double surcharge;

    TypeSeat(double surcharge) {
        this.surcharge = surcharge;
    }

    public double getSurcharge() {
        return surcharge;
    }
}
