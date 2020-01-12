public class Subway extends Vehicle {



    private int trainIdentification;
    private int numberOfCars;
    private int passengerPerCar;
    private String operationalStatus;
    private int operationalDate;

    // Constructor for Subway
    public Subway(int unitNumber, int trainIdentification, int numberOfCars, int passengerPerCar, String operationalStatus, int operationalDate) {
        super(unitNumber);
        this.trainIdentification = trainIdentification;
        this.numberOfCars = numberOfCars;
        this.passengerPerCar = passengerPerCar;
        this.operationalStatus = operationalStatus;
        this.operationalDate = operationalDate;
    }

    // Contains setters and getters for its attributes.

    public int getTrainIdentification() {
        return trainIdentification;
    }


    public int getNumberOfCars() {
        return numberOfCars;
    }


    public int getPassengerPerCar() {
        return passengerPerCar;
    }


    public String getOperationalStatus() {
        return operationalStatus;
    }


    public int getOperationalDate() {
        return operationalDate;
    }


    // Required Attribute for general use of Capacity and Description.
    @Override
    public int getCapacity() {
        return this.numberOfCars * this.passengerPerCar;
    }


    @Override
    public String getDescription() {
        return (getUnitNumber() + "," + getTrainIdentification() + ","
        + getNumberOfCars() + "," + getPassengerPerCar() + "" + getOperationalStatus() +
        "," + getOperationalDate());
    }



}
