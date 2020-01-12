public class Senior extends Passenger {

    private double value = 1.25;

    // Constructor for the senior class.
    public Senior(String[] data) {
        super(data);


    }

    // Overrides the getValue for Passenger Class.
    @Override
    public double getValue() {
        return value;
    }

}
