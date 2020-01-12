public class Bus extends Vehicle {

    private String busIdentification;
    private int capacity;

    // Constructor for the Bus objects.
    public Bus(int unitNumber, String busIdentification, int capacity) {
        super(unitNumber);
        this.busIdentification = busIdentification;
        this.capacity = capacity;

    }

    // Getter for its attributes.
    public String getBusIdentification() {
        return busIdentification;
    }


    // Override for getCapacity for the Passenger Class.
    @Override
    public int getCapacity() {
        return capacity;
    }


    public String getDescription() {
        return (getUnitNumber() + "," + getBusIdentification() + "," + getCapacity());
    }
}
