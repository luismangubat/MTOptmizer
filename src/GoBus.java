public class GoBus extends Vehicle {

    private String busIdentification;
    private int capacity;

    // Constructor for the GoBus class.
    public GoBus(int UnitNumber, String busIdentification, int capacity) {
        super(UnitNumber);
        this.busIdentification = busIdentification;
        this.capacity = capacity;

    }

    // Getter for GoBus attributes.
    public String getBusIdentification() {
        return busIdentification;
    }


    // Required Attribute for general use of Capacity and Description.
    @Override
    public int getCapacity() {
        return capacity;
    }


    @Override
    public String getDescription() {
        return (getUnitNumber() + "," + getBusIdentification() + "," + getCapacity());
    }
}
