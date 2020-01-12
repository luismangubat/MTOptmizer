public class Adult extends Passenger {

    private double value = 1.0;

    // Constructor for Adult objects.
    public Adult(String[] data) {
        super(data);


    }

    // Overrides the getValue for Passenger Class.
    @Override
    public double getValue() {
        return value;
    }



}
