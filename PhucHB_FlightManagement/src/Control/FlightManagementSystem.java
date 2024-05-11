/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author hoang
 */
import Model.Crew;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import Model.Flight;
import Model.Passenger;
import Model.Reservations;
import Model.Utils;


public class FlightManagementSystem<E> {

    private final String FILE_FLIGHTS = "flights.txt";
    private final String FILE_PASSENGERS = "passengers.txt";
    private final String FILE_RESERVATIONS = "reservations.txt";
    private final String FILE_CREWS = "crewass.txt";

    Scanner sc = new Scanner(System.in);
    List<Flight> listFlight = new ArrayList<>();
    List<Reservations> listReservations = new ArrayList<>();
    List<Passenger> listPassenger = new ArrayList<>();
    List<Crew> listCrews = new ArrayList<>();

    private boolean askToBackToEnterAgain() {
        System.out.print("Do you want to enter again?(Y/N): ");
        String string = sc.nextLine().trim();
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    public boolean askToBackToMenu() {
        System.out.print("Do you want to back to Main Menu(Y/N): ");
        String string = sc.nextLine().trim();
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    public boolean chooseYesNo(String string) {
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    private boolean checkFlightNumberUniqueFromFileAndList(String flightID) {
        List<Flight> listFlightId = addElementToListFlightFromFile();
        for (Flight flight : listFlightId) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightID)) {
                return false;
            }
        }
        return true;
    }

    private boolean uniqueFlight(String code) {
        for (Flight flight : listFlight) {
            if (flight.getFlightNumber().equalsIgnoreCase(code)) {
                return false;
            }
        }
        return true;
    }

    private String createFlightNumber() {
        while (true) {
            System.out.print("Flight number: ");
            String flightNumber = sc.nextLine().trim();
            if (flightNumber.matches("^F\\d{4}$") && checkFlightNumberUniqueFromFileAndList(flightNumber) && uniqueFlight(flightNumber)) {
                return flightNumber;
            } else {
                System.out.println("Must unique and follow as: Fxyzt, with xyzt is a number and no spaces");
            }
        }
    }

    private String normalizeWord(String word) {
        String newString = "";
        String words[] = word.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            newString += words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            if (i != words.length - 1) {
                newString += " ";
            }
        }
        return newString;
    }

    public String createDepartureCity() {
        System.out.print("Departure city: ");
        String departureCity = sc.nextLine().trim();
        departureCity = normalizeWord(departureCity);
        return departureCity;
    }

    public String createDestinationCity() {
        System.out.print("Destination city: ");
        String destination = sc.nextLine().trim();
        destination = normalizeWord(destination);
        return destination;
    }

    private boolean isValidTime(String time) {
        if (time.matches("^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$")) {
            return true;
        }
        return false;
    }

    private boolean isValidDate(String date) {
        if (date.matches("^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        }
        return false;
    }

    public String createDepartureTime() {
        String time;
        while (true) {
            System.out.print("Departure time(yyyy-MM-dd HH:mm:ss): ");
            time = sc.nextLine().trim();
            if (isValidTime(time)) {
                break;
            } else {
                System.out.println("Not valid time. Do again");
            }
        }
        return time;
    }

    public boolean isArrivalAfterDeparture(String departureTime, String arrivalTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (departureTime == null) {
            return true;
        }
        try {
            Date departureDate = dateFormat.parse(departureTime);
            Date arrivalDate = dateFormat.parse(arrivalTime);
            return departureDate.before(arrivalDate);
        } catch (Exception e) {
            return false;
        }
    }

    public String createArrivalTime(String departureTime) {
        String time;
        while (true) {
            System.out.print("Arrival time(yyyy-MM-dd HH:mm:ss): ");
            time = sc.nextLine().trim();
            if (isValidTime(time) && isArrivalAfterDeparture(departureTime, time)) {
                break;
            } else {
                System.out.println("Not valid time. Do again");
            }
        }
        return time;
    }

    private int createMaxSeat() {
        int maxSeat = Utils.getInt("Max seat of flight: ");
        return maxSeat;
    }

    public void addNewFlight() {
        String createFlightNumber = createFlightNumber();
        String createDepatureCity = createDepartureCity();
        String createDestinationCity = createDestinationCity();
        String departureTime = createDepartureTime();
        String arrivalTime = createArrivalTime(departureTime);
        listFlight.add(new Flight(createFlightNumber, createDepatureCity, createDestinationCity, departureTime, arrivalTime, createMaxSeat()));
    }

    public String createValidDate() {
        while (true) {
            System.out.print("Start day(yyyy-MM-dd): ");
            String startDay = sc.nextLine().trim();
            if (!isValidDate(startDay)) {
                System.out.println("You input wrong type date. Input again");
            } else {
                return startDay;
            }
        }
    }

    private String getStartDayOfFlight(Flight flight) {
        String day[] = flight.getDepartureTime().split("\\s+");
        return day[0];
    }

    public List<Flight> availableFlightBaseOnDepartureAndArrival(String departureCity, String destinationCity, String startDay) {
        List<Flight> listAvailableFlight = addElementToListFlightFromFile();
        List<Flight> fullList = new ArrayList<>();
        for (Flight flight : listAvailableFlight) {
            if (flight.getDestinationCity().equalsIgnoreCase(destinationCity)
                    && flight.getDepartureCity().equalsIgnoreCase(departureCity)
                    && startDay.equalsIgnoreCase(getStartDayOfFlight(flight))) {
                fullList.add(flight);
            }
        }
        return fullList;
    }

    public void showList(List<Flight> list) {
        for (Flight flight : list) {
            flight.getInforOfFlightForPas();
        }
    }

    public boolean makeReservation(String passengerName, String contactDetails, String flightNumberToFound) {
        List<Reservations> listRersevationsWithUniqueId = addElementToListReservationsFromFile();
        System.out.println(listRersevationsWithUniqueId.size());
        List<Flight> listF = addElementToListFlightFromFile();
        if (!listF.isEmpty()) {
            for (Flight flight : listF) {
                if (flight.getAvailableSeats() > 0) {
                    if (flight.getFlightNumber().equalsIgnoreCase(flightNumberToFound)) {
                        int incCntSeat = flight.getCountSeat();
                        flight.setCountSeat(++incCntSeat);
                        int fightAvailableDecs = flight.getAvailableSeats();
                        flight.setAvailableSeats(--fightAvailableDecs);
                        Passenger passenger = new Passenger(passengerName, contactDetails);
                        listPassenger.add(passenger);
                        String subString;
                        String reservationID;
                        if (!listRersevationsWithUniqueId.isEmpty()) {
                            subString = listRersevationsWithUniqueId.get(listRersevationsWithUniqueId.size()).getReservationID().substring(2);
                            int lastRerseveID = Integer.parseInt(subString);
                            reservationID = "VN" + lastRerseveID;
                        } else {
                            reservationID = "VN0";
                        }
                        Reservations r = new Reservations(reservationID, passenger, flight);
                        listReservations.add(r);
                        System.out.println("Reservation ID: " + reservationID);
                        saveFlightsToFile();
                        savePassengersToFile();
                        saveRerservationsToFile();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private Reservations findReservationByID(String reservationString) {
        for (Reservations reservation : listReservations) {
            if (reservation.getReservationID().equalsIgnoreCase(reservationString)) {
                return reservation;
            }
        }
        return null;
    }

    public void displayAllSeat(Flight flight) {
        System.out.println("All Seat for Flight " + flight.getFlightNumber() + ":");
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
            int seatNumber = entry.getKey();
            boolean isAvailable = entry.getValue();
            if (isAvailable) {
                System.out.println("Seat " + seatNumber + " is available.");
            } else {
                System.out.println("Seat " + seatNumber + " is not available.");
            }
        }
    }

    public boolean selectSeatIfSeatAvailable(Flight flight, int seatNumber) {
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
            Integer seat = entry.getKey();
            Boolean isAvailable = entry.getValue();
            if (seatNumber == seat && isAvailable) {
                return true;
            }
        }
        return false;
    }

    public void setAllSeatNumberToDefault(Flight flight) {
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Integer i = 0; i <= flight.getMaxSeat(); i++) {
            seatMap.put(i, Boolean.TRUE);
        }
        flight.setSeatMap(seatMap);
    }

    public boolean checkInAndSeatAllocation(String providingID) {
        if (findReservationByID(providingID) == null) {
            return false;
        }
        Reservations reservationByID = findReservationByID(providingID);
        if (reservationByID.isIsCheckIn()) {
            return false;
        }
        Passenger passenger = reservationByID.getPassenger();
        Flight flight = reservationByID.getFlight();
        if (!flight.isIsAllocateAllSeat()) {
            setAllSeatNumberToDefault(flight);
            flight.setIsAllocateAllSeat(true);
        }
        reservationByID.setIsCheckIn(true);
        System.out.println("Boarding Pass:");
        System.out.println("Passenger Name: " + passenger.getName());
        System.out.println("Contact Details: " + passenger.getContactDetails());
        System.out.println("Flight Details:");
        flight.getInforOfFlightForPas();
        displayAllSeat(flight);
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        String choose;
        System.out.print("You want to choose seat(Y/N): ");
        choose = sc.nextLine().trim();
        if (chooseYesNo(choose)) {
            while (true) {
                System.out.print("Enter seat number you want to seat: ");
                int selectSeatNumber = Integer.parseInt(sc.nextLine());
                if (selectSeatIfSeatAvailable(flight, selectSeatNumber)) {
                    reservationByID.setSeatNumber(selectSeatNumber);
                    passenger.setSeatNumber(selectSeatNumber);
                    for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                        Integer key = entry.getKey();
                        if (key == selectSeatNumber) {
                            entry.setValue(false);
                            return true;
                        }
                    }
                } else {
                    System.out.println("Seat is not available. Choose another seat or random");
                    if (!askToBackToEnterAgain()) {
                        break;
                    }
                }
            }
        } else {
            for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                Integer key = entry.getKey();
                boolean isAvai = entry.getValue();
                if (isAvai) {
                    entry.setValue(false);
                    reservationByID.setSeatNumber(key);
                    passenger.setSeatNumber(key);
                    reservationByID.setIsCheckIn(true);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int saveToFileFlightAfterIndex() {
        int index = -1;
        try {
            FileReader file = new FileReader(FILE_FLIGHTS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < listFlight.size(); i++) {
                    if (listFlight.get(i).writeToFile().equals(line)) {
                        index = i + 1;
                    } else {
                        break;
                    }
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void saveFlightsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_FLIGHTS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listFlight.size(); i++) {
                if (i >= saveToFileFlightAfterIndex()) {
                    pw.println(listFlight.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int saveToFilePassengersAfterIndex() {
        int index = -1;
        try {
            FileReader file = new FileReader(FILE_PASSENGERS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < listPassenger.size(); i++) {
                    if (listPassenger.get(i).writeToFile().equals(line)) {
                        index = i + 1;
                    } else {
                        break;
                    }
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void savePassengersToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_PASSENGERS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listPassenger.size(); i++) {
                if (i >= saveToFilePassengersAfterIndex()) {
                    pw.println(listPassenger.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int saveToFileRerservationsAfterIndex() {
        int index = -1;
        try {
            FileReader file = new FileReader(FILE_RESERVATIONS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < listReservations.size(); i++) {
                    if (listReservations.get(i).writeToFile().equals(line)) {
                        index = i + 1;
                    } else {
                        break;
                    }
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void saveRerservationsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_RESERVATIONS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listReservations.size(); i++) {
                if (i >= saveToFileRerservationsAfterIndex()) {
                    pw.println(listReservations.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveCrewsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_CREWS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listCrews.size(); i++) {
                if (i == 0) {
                    pw.println(listCrews.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Flight> addElementToListFlightFromFile() {

        List<Flight> listFlightnew = new ArrayList<>();
        if (!listFlight.isEmpty()) {
            for (Flight flight : listFlight) {
                listFlightnew.add(flight);
            }
        }
        try {
            FileReader fr = new FileReader(FILE_FLIGHTS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                String words[] = line.split("_");
                Flight flight = new Flight(words[0], words[1], words[2], words[3], words[4], Integer.parseInt(words[5]), Integer.parseInt(words[6]));
                listFlightnew.add(flight);
            }
            br.close();
            fr.close();
        } catch (Exception a) {
        }
        return listFlightnew;
    }

    private List<Passenger> addElementToListPassengerFromFile() {
        List<Passenger> listPassenger = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_PASSENGERS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                String words[] = line.split("_");
                Passenger passenger = new Passenger(words[0], words[1], Integer.parseInt(words[3]));
                listPassenger.add(passenger);
            }
            br.close();
            fr.close();
        } catch (Exception a) {
        }
        return listPassenger;
    }

    public void test(String line) {
        String words[] = line.split("\\|");
        for (String word : words) {
            System.out.println(word);
        }
        String passenger[] = words[1].split("_");
        String flight[] = words[2].split("_");
        for (String string : flight) {
            System.out.println(string);
        }
        Passenger p = new Passenger(passenger[0], passenger[1], Integer.parseInt(passenger[2]));
        System.out.println(p.writeToFile());
        Flight f = new Flight(flight[0], flight[1], flight[2], flight[3], flight[4], Integer.parseInt(flight[5]), Integer.parseInt(flight[6]));
        
        System.out.println(f.writeToFile());
        Reservations r = new Reservations(words[0], p, f, Integer.parseInt(flight[3]), Boolean.parseBoolean(flight[4]));
        System.out.println(r.toString());
    }

    private List<Reservations> addElementToListReservationsFromFile() {
        List<Reservations> list = new ArrayList<>();
        if (!listReservations.isEmpty()) {
            for (Reservations re : listReservations) {
                list.add(re);
            }
        }
        try {
            FileReader fr = new FileReader(FILE_RESERVATIONS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                String words[] = line.split("\\|");
                String passenger[] = words[1].split("_");
                String flight[] = words[2].split("_");
                Passenger p = new Passenger(passenger[0], passenger[1], Integer.parseInt(passenger[2]));
                Flight f = new Flight(flight[0], flight[1], flight[2], flight[3], flight[4], Integer.parseInt(flight[5]), Integer.parseInt(flight[6]));
                Reservations r = new Reservations(words[0], p, f, Integer.parseInt(flight[3]), Boolean.parseBoolean(flight[4]));
                list.add(r);
            }
            br.close();
            fr.close();
        } catch (Exception a) {
            System.out.println(a.getCause());
        }
        return list;
    }

    private List<Crew> addElementToListCrewFromFile() {
        List<Crew> listCrew = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_RESERVATIONS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                //listCrew.add();
            }
            br.close();
            fr.close();
        } catch (Exception a) {
        }
        return listCrew;
    }

    public void printAllListFlightsFromFile() {
        List<Flight> list = new ArrayList<>();
        list = addElementToListFlightFromFile();
        for (Flight flight : list) {
            System.out.println(flight.toString());
        }
    }

    public void printAllListPassengersFromFile() {
        List<Passenger> list = new ArrayList<>();
        list = addElementToListPassengerFromFile();
        for (Passenger pas : list) {
            System.out.println(pas.toString());
        }
    }

    public void printAllListReserveFromFile() {
        List<Reservations> list = new ArrayList<>();
        list = addElementToListReservationsFromFile();
        for (Reservations re : list) {
            System.out.println(re.toString());
        }
    }
}

