public class Streetcar extends Vehicle {



    private String streetcarIdentification;
    private String type;

    // Constructor for Streetcar.
    public Streetcar(int unitNumber, String streetcarIdentification, String type ){
        super(unitNumber);
        this.streetcarIdentification = streetcarIdentification;
        this.type = type;



    }
    // getters for Streetcar Attributes.

    public String getStreetcarIdentification() {
        return streetcarIdentification;
    }



    public String getType() {
        return type;
    }


    // Required Attribute for general use of Capacity and Description.
    @Override
    public int getCapacity() {
        if(type == "S"){
            return 40;
        }else{
            return 80;
        }
    }

    public String getDescription() {
        return (getUnitNumber() + "," + getStreetcarIdentification() + "," + getType());
    }
}
