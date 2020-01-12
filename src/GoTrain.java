public class GoTrain extends Vehicle {

    private String trainIdentification;
    private int trainCapacity;


    // Constructor for the GoTrain class.
    public GoTrain(int unitNumber, String trainIdentification, int trainCapacity ){
        super(unitNumber);
        this.trainIdentification = trainIdentification;
        this.trainCapacity = trainCapacity;


    }

    // Getters for the GoTrain attributes.
    public String getTrainIdentification() {
        return trainIdentification;
    }


    public int getTrainCapacity() {
        return trainCapacity;
    }

    // Required Attribute for general use of Capacity and Description.
    @Override
    public int getCapacity() {
        return trainCapacity;
    }

    @Override
    public String getDescription() {
        return (getUnitNumber() + "," +  getTrainIdentification() + ","+ getTrainCapacity());
    }

}
