public abstract class Vehicle {

    private int unitNumber;

    // Constructor for subway.
    public Vehicle(int UnitNumber) {
        this.unitNumber = UnitNumber;
    }

    public int getUnitNumber() {
        return unitNumber;
    }


    // Required Attribute for general use of Capacity and Description.
    public abstract int getCapacity();

    public abstract String getDescription();

}
