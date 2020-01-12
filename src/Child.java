public class Child extends Passenger {

    private double value = 0.75;


    // Constructor for the Child objects.
    public Child(String[] data) {
        super(data);


    }

    // Overrides the getValue for Passenger Class.
    @Override
    public double getValue() {
        return value;
    }

}
