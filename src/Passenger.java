public abstract class Passenger  {

    private String ageGroup;
    private String transportationModality;
    private int hourOfDay; // 24 hour clock
    private int date;



    // Constructor for Passenger objects.
    public Passenger(String[] data){
        transportationModality = data[1];
        ageGroup = data[2];
        hourOfDay = Integer.parseInt(data[3]);
        date = Integer.parseInt(data[4]);


    }


    // Getters for Passenger attributes.
    public String getAgeGroup() {
        return ageGroup;
    }


    public String getTransportationModality() {
        return transportationModality;
    }


    public int getHourOfDay() {
        return hourOfDay;
    }


    // Required attribute for all Passenger type objects.
    public abstract double getValue();

}
