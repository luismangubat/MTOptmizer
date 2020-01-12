import java.io.*;

import java.util.ArrayList;


/* MTOptimizer is the main class of the  program. It reads in all the files and uses this information
to build them into an objects. For example, creating different types of Vehicles into multiple arrayList.
It also optimizes the demand of a large amount passengers based on the ticket information. Finally it writes in an error Log
and Optimization fleet file. */
public class MTOptimizer {


    // ArrayList of strings of each Vehicle file.
    private static ArrayList<String> subwayFile = new ArrayList<>();
    private static ArrayList<String> goTrainFile = new ArrayList<>();
    private static ArrayList<String> streetcarFile = new ArrayList<>();
    private static ArrayList<String> busFile = new ArrayList<>();
    private static ArrayList<String> goBusFile = new ArrayList<>();

    // ArrayList of String of ridership file.
    private static ArrayList<String> ridershipFile = new ArrayList<>();

    // ArrayLists of different types of vehicles.
    private static ArrayList<Subway> subways = new ArrayList<>();
    private static ArrayList<GoTrain> goTrains = new ArrayList<>();
    private static ArrayList<Streetcar> streetCars = new ArrayList<>();
    private static ArrayList<Bus> busses = new ArrayList<>();
    private static ArrayList<GoBus> goBusses = new ArrayList<>();

    // ArrayList of riders type Passenger.
    private static ArrayList<Passenger> riders = new ArrayList<>();

    public static void main(String[] args) {


        char character = '3';
        int number = (int) character - 48;
        double var = number/4;
        System.out.println(var);


        // Reads and builds an arrayList of type Subway.
        readSubwayFile();
        buildSubwayObjects(subwayFile);


        // Reads and builds an arrayList of type GoTrain.
        readGoTrainFile();
        buildGoTrainObject(goTrainFile);

        // Reads and builds an arrayList of type StreetCar.
        readStreetCarFile();
        buildStreetcarObject(streetcarFile);

        // Reads and builds an arrayList of type Bus.
        readBusFile();
        buildBusObject(busFile);

        // Reads and builds an arrayList of type GoBus.
        readGoBusFile();
        buildGoBusObject(goBusFile);

        // Reads the Ridership file
        readRidershipFile();

        // Builds an arrayList of Passenger, and creates an errorLog.
        buildRider(ridershipFile);

        // Sorts all the arrayList that are required for Optimization.
        sortArrayList();

        // Writes out the InOperationFleet.txt
        writeOptimalFile();



    }

    // Reads through the Ridership file.
    public static void readRidershipFile() {

        // Creates a file from subway.txt
        File file = new File("ridership.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the Subway.txt to an ArrayList.

            while ((currentLine = br.readLine()) != null) {
                //  System.out.println(currentLine);
                ridershipFile.add(currentLine);


            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Check rider file, 
    public static void buildRider(ArrayList<String> ridershipFile) {


        String ageGroup;
        int count = 0;
        PrintWriter fileStream = null;

        // Creates a file Errorlog.txt
        try {
            fileStream = new PrintWriter(new FileOutputStream("Errorlog.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("There is an error in opening the file");
        }

        // Iterates through riderShipFile building and verifying if a passenger ticket is valid.
        while (count < ridershipFile.size()) {

            if (!verifyRider(ridershipFile.get(count), fileStream)) {
                count++;

            } else {
                String[] aPassenger = ridershipFile.get(count).split(",");
                ageGroup = aPassenger[2];


                if (ageGroup.equals("C")) {
                    Passenger newChild = new Child(aPassenger);
                    riders.add(newChild);

                }

                if (ageGroup.equals("A")) {
                    Passenger newAdult = new Adult(aPassenger);
                    riders.add(newAdult);

                }

                if (ageGroup.equals("S")) {
                    Passenger newSenior = new Child(aPassenger);
                    riders.add(newSenior);


                }


                count++;
            }

        }
        fileStream.close();


    }


    // Sorts the ArrayList by the hour.
    public static void sortArrayList() {

        // sorts riders by the hour.
        riders.sort(
                (Passenger rider1, Passenger rider2) -> Integer.compare(rider1.getHourOfDay(), rider2.getHourOfDay()));

        // Sorts subway by largest capacity.
        subways.sort(
                (Subway subway1, Subway subway2)
                        -> Integer.compare(subway2.getPassengerPerCar(), subway1.getPassengerPerCar()));

        // Sorts goTrain by largest capacity.
        goTrains.sort(
                (GoTrain goTrain1, GoTrain goTrain2)
                        -> Integer.compare(goTrain2.getTrainCapacity(), goTrain1.getTrainCapacity()));

        // Sorts streetCars by largest capacity.
        streetCars.sort(
                (Streetcar streetcar1, Streetcar streetcar2)
                        -> (streetcar2.getType().compareTo(streetcar1.getType())));

        // Sorts Busses by largest capacity.
        busses.sort(
                (Bus bus1, Bus bus2)
                        -> Integer.compare(bus2.getCapacity(), bus1.getCapacity()));

        // Sorts goBusses by largest capacity.
        goBusses.sort(
                (GoBus goBus1, GoBus goBus2)
                        -> Integer.compare(goBus2.getCapacity(), goBus1.getCapacity()));


    }


    // Optimizes each hour based on the transport demand of a specific vehicle.
    // Return strings of data of an entire day.
    public static String optimizer(ArrayList vehicles, ArrayList<Passenger> passengers) {


        String transport = "";

        // Classify which type of vehicle.
        if (vehicles.get(0) instanceof Bus) {
            transport = "C:Bus";
        } else if (vehicles.get(0) instanceof Streetcar) {
            transport = "X:Streetcar";
        } else if(vehicles.get(0) instanceof  Subway){
            transport = "S:Subway";
        } else if(vehicles.get(0) instanceof  GoBus) {
            transport = "D:GoBus";
        } else if(vehicles.get(0) instanceof GoTrain){
            transport = "G:GoTrain";
        }

        String data = "";


        // Loops through every hour, and finds the optimal fleet for that designated time period.
        for (int hour = 1; hour < 25; hour++) {
            double riderCount = 0;
            for (Passenger p : passengers) {
                if (p.getHourOfDay() == hour && p.getTransportationModality().equalsIgnoreCase(transport.substring(0, transport.indexOf(":")))) {
                    switch (p.getAgeGroup()) {
                        case "S":
                            riderCount += 1.25;
                            break;
                        case "A":
                            riderCount += 1;
                            break;
                        case "C":
                            riderCount += 0.75;
                            break;
                    }
                }
            }

            data += "---------------\n";
            data += "[Hour = " + hour + "]\n";

            int index = 0;
            while (riderCount > 0) {
                data += "" + transport.substring(transport.indexOf(":") + 1) + ": " + ((Vehicle) vehicles.get(index)).getDescription() + "\n";
                index++;
                riderCount -= ((Vehicle) vehicles.get(index)).getCapacity();
            }

            data += "[Count = " +  index + "]\n";
        }

        return data;

    }


    // Writes Optimal Fleet size.
    public static void writeOptimalFile(){

        PrintWriter fileStream = null;

        // Writes into the fileStream of the required Information.
        try {

            fileStream = new PrintWriter(new FileOutputStream("InOperationFleets.txt"));


            fileStream.println("------ MTOptimizer ------" + "\n");


            fileStream.println("[Subway]");
            fileStream.println(optimizer(subways, riders));

            fileStream.println("[Bus]");
            fileStream.println(optimizer(busses, riders));

            fileStream.println("[GoTrain]");
            fileStream.println(optimizer(goTrains, riders));

            fileStream.println("[Streetcar]");
            fileStream.println(optimizer(streetCars, riders));

            fileStream.println("[GoBus]");
            fileStream.println(optimizer(goBusses,riders));




        } catch (FileNotFoundException e) {
            System.out.println("There is an error in opening the file");
        }

        fileStream.close();

    }


    // Verifies if the rider ticket has the right information.
    public static boolean verifyRider(String ticket, PrintWriter fileStream) {

        // Checks each part of a rider ticket, and adds invalid Rider ticket information into the error log.
        String dataLine = ticket;
        String[] aPassenger = dataLine.split(",");


        // Checks ID length.
        if (aPassenger.length != 5) {
            writeErrorLog("Data entry", "Wrong data entry length ", dataLine, fileStream);
            return false;

        }

        // Check if ID is length 7 and are all integers.
        if (aPassenger[0].length() == 7)
            try {
                Integer.parseInt(aPassenger[0]);
            } catch (NumberFormatException e) {
                writeErrorLog("ID", "Passenger ID is not a integer", dataLine, fileStream);
                return false;
            }


        // Check if ID is not length 7 or 16.
        if (aPassenger[0].length() != 7 && aPassenger[0].length() != 16
                && !aPassenger[0].equalsIgnoreCase("*")) {

            writeErrorLog("ID", "Passenger ID is not the right length", dataLine, fileStream);
            return false;

        }


        // Checks if it has a valid vehicle type.
        if (!(aPassenger[1].equals("S") || aPassenger[1].equals("G")
                || aPassenger[1].equals("X") || aPassenger[1].equals("C")
                || aPassenger[1].equals("D"))) {

            writeErrorLog("Vehicle", "Invalid vehicle type", dataLine, fileStream);
            return false;

        }

        // Checks if it has a valid age type.
        if (!(aPassenger[2].equals("C") || aPassenger[2].equals("A")
                || aPassenger[2].equals("S"))) {

            writeErrorLog("Age", "Invalid age type", dataLine, fileStream);
            return false;

        }

        try {
            Integer.parseInt(aPassenger[3]);

            // Checks if time is within a 24 hour boundry.
            if (Integer.parseInt(aPassenger[3]) > 24 || Integer.parseInt(aPassenger[3]) < 1) {

                writeErrorLog("Time", "Out of Range", dataLine, fileStream);
                return false;

            }

        } catch (NumberFormatException e) {

            // Check if its an integer.
            writeErrorLog("Time", "Time entry is not an integer", dataLine, fileStream);
            return false;

        }

        try {
            Integer.parseInt(aPassenger[4]);


            // Checks if Date is the right length.
            if (aPassenger[4].length() != 8) {

                writeErrorLog("Date", "Date is not the right length", dataLine, fileStream);
                return false;

            }
        } catch (NumberFormatException e) {

            // Checks if Date is an integer.
            writeErrorLog("Date", "Date is not an integer", dataLine, fileStream);
            return false;

        }

        return true;


    }


    // Writes into a text with Passenger tickets with the wrong information.
    public static void writeErrorLog(String type, String issue, String currentLine, PrintWriter fileStream) {

        fileStream.println("[" + type + " Error] " + " Issue: " + issue + "\n Rider Ticket -> " + currentLine + "\n");


    }


    // Reads through the subwayFile.
    public static void readSubwayFile() {


        // Creates a file from subway.txt
        File file = new File("subways.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the Subway.txt to an ArrayList.

            while ((currentLine = br.readLine()) != null) {
                subwayFile.add(currentLine);


            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Iterates through the subwayFile and parses it and adds these attributes to the a new object type Subway.
    public static ArrayList<Subway> buildSubwayObjects(ArrayList<String> subwayFile) {

        String line;


        int count = 0;
        int unitNumber;
        int trainIdentfication;
        int numberOfCars;
        int passengerPerCar;
        String operationalStatus;
        int operationalDate;


        while (count < subwayFile.size()) {

            line = subwayFile.get(count);

            // Parse each substring to its designated attribute.
            unitNumber = Integer.parseInt(line.substring(0, 4));
            trainIdentfication = Integer.parseInt(line.substring(5, 8));
            numberOfCars = Integer.parseInt(line.substring(9, 10));
            passengerPerCar = Integer.parseInt(line.substring(11, 13));
            operationalStatus = line.substring(14);
            operationalDate = Integer.parseInt(line.substring(16, 24));

            // Creates a new subWay Object.
            Subway newSubway = new Subway(unitNumber, trainIdentfication, numberOfCars,
                    passengerPerCar, operationalStatus, operationalDate);

            // Adds the current subway object into the ArrayList.
            subways.add(newSubway);


            count++;

        }

        return subways;

    }


    //Checks if it the arrayList has the right properties.
    public static void readSubwayList(ArrayList<Subway> subways) {

        int count = 0;
        while (count < subways.size()) {
            System.out.println(subways.get(count).getOperationalDate());
            count++;

        }

    }


    // Reads through the goTrainFile.
    public static void readGoTrainFile() {


        // Creates a file from subway.txt
        File file = new File("gotrains.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the Subway.txt to an ArrayList.
            while ((currentLine = br.readLine()) != null) {
                goTrainFile.add(currentLine);

            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Iterates through the goTrainFile creating goTrain Objects and adding it to an ArrayList.
    public static ArrayList<GoTrain> buildGoTrainObject(ArrayList<String> goTrainFile) {

        String line;

        int count = 0;
        int unitNumber;
        String trainIdentification;
        int trainCapacity;

        // Loops through the goTrainFile and gets each designated attribute, and add it to the arrayList.
        while (count < goTrainFile.size()) {
            line = goTrainFile.get(count);

            unitNumber = Integer.parseInt(line.substring(0, 4));
            trainIdentification = line.substring(5, 9);
            trainCapacity = Integer.parseInt(line.substring(10, 13));

            GoTrain newGoTrain = new GoTrain(unitNumber, trainIdentification, trainCapacity);

            goTrains.add(newGoTrain);

            count++;

        }

        return goTrains;


    }


    // Reads through the streetcarFile;.
    public static void readStreetCarFile() {


        // Creates a file from streetcar.txt
        File file = new File("streetcars.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the streetcar.txt to an ArrayList.

            while ((currentLine = br.readLine()) != null) {
                streetcarFile.add(currentLine);

            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Iterates through the streetcarFile creating Objects and adding it to an ArrayList.
    public static ArrayList<Streetcar> buildStreetcarObject(ArrayList<String> streetcarFile) {

        String line;
        int count = 0;
        int unitNumber;
        String streetcarIdentification;
        String type;

        // Iterates and parses the designated information for type Streetcar.
        while (count < streetcarFile.size()) {

            line = streetcarFile.get(count);

            unitNumber = Integer.parseInt(line.substring(0, 4));
            streetcarIdentification = line.substring(5, 10);
            type = line.substring(11);


            Streetcar newStreetCar = new Streetcar(unitNumber, streetcarIdentification, type);
            streetCars.add(newStreetCar);


            count++;


        }


        return streetCars;


    }


    // Reads through the busFile.
    public static void readBusFile() {


        // Creates a file from buses.txt
        File file = new File("buses.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the buses.txt to an ArrayList.

            while ((currentLine = br.readLine()) != null) {
                busFile.add(currentLine);

            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Iterates through the busFile creating objects and adding it to an ArrayList.
    public static ArrayList<Bus> buildBusObject(ArrayList<String> busFile) {

        // Creates the required attributes for the class.
        String line;


        int count = 0;
        int unitNumber;
        String trainIdentfication;
        int trainCapacity;


        // Iterates through busFile, parsing information and add it to an Object arrayList.
        while (count < busFile.size()) {
            line = busFile.get(count);

            unitNumber = Integer.parseInt(line.substring(0, 4));
            trainIdentfication = line.substring(5, 10);
            trainCapacity = Integer.parseInt(line.substring(11, 13));

            Bus newBus = new Bus(unitNumber, trainIdentfication, trainCapacity);

            busses.add(newBus);


            count++;

        }

        return busses;


    }


    // Reads through goBusFile.
    public static void readGoBusFile() {


        // Creates a file from gobuses.txt
        File file = new File("gobuses.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            // Add every line from the gobuses.txt to an ArrayList.
            while ((currentLine = br.readLine()) != null) {
                goBusFile.add(currentLine);

            }

        } catch (Exception e) {

            System.out.println("There is an error");

        }


    }


    // Iterates through the goBusFile creating objects and adding it to an ArrayList.
    public static ArrayList<GoBus> buildGoBusObject(ArrayList<String> busFile) {

        // Creates the required attributes for the class.
        String line;


        int count = 0;
        int unitNumber;
        String trainIdentification;
        int trainCapacity;

        // Iterates through busFile, parsing Information and add it to an arrayList.
        while (count < busFile.size()) {
            line = busFile.get(count);

            unitNumber = Integer.parseInt(line.substring(0, 4));
            trainIdentification = line.substring(5, 10);
            trainCapacity = Integer.parseInt(line.substring(11, 13));

            GoBus newGoBus = new GoBus(unitNumber, trainIdentification, trainCapacity);

            goBusses.add(newGoBus);


            count++;

        }

        return goBusses;


    }


}
