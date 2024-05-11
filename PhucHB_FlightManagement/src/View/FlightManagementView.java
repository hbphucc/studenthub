/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author hoang
 */
import Control.FlightManagementSystem;
import java.util.List;
import java.util.Scanner;
import Model.Flight;

public class FlightManagementView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FlightManagementSystem f = new FlightManagementSystem();
        int check = 0;
        while (true) {
            try {
                System.out.println("\n1. Add New Flight\n"
                        + "2. Search available flight on departure and arrival city and time and Make reservation.\n"
                        + "3. Check-in and allocate seat for passenger:\n"
                        + "4. Crew Management and Administrator Access\n"
                        + "5. Save to file.\n"
                        + "6. Print all lists from file.\n"
                        + "Others- Quit");
                System.out.print("Your choice: ");
                check = Integer.parseInt(sc.nextLine());
                if (check == 1) {
                    while (true) {
                        f.addNewFlight();
                        System.out.println("Add successed");
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 2) {
                    while (true) {
                        String departureCity = f.createDepartureCity();
                        String destinationCity = f.createDestinationCity();
                        String startFlying = f.createValidDate();
                        if (f.availableFlightBaseOnDepartureAndArrival(departureCity, destinationCity, startFlying).isEmpty()) {
                            System.out.println("There are no planes based on the information you provided");
                        } else {
                            f.showList(f.availableFlightBaseOnDepartureAndArrival(departureCity, destinationCity, startFlying));
                            System.out.print("Are you want to give infor and choose flight to make reservation(Y/N): ");
                            String choose = sc.nextLine().trim();
                            if (f.chooseYesNo(choose)) {
                                System.out.print("Enter your name: ");
                                String passengerName = sc.nextLine().trim();
                                System.out.print("Enter contact details: ");
                                String contactDetails = sc.nextLine().trim();
                                System.out.print("Enter flight number to reserve: ");
                                String flightNumberToFound = sc.nextLine().trim();
                                if (f.makeReservation(passengerName, contactDetails, flightNumberToFound)) {
                                    System.out.println("Reservation Successful!");
                                } else {
                                    System.out.println("Flight full seat or not found flight.");
                                }
                            }
                        }
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 3) {
                    while (true) {
                        System.out.print("Reservation ID to allocate seat: ");
                        String reservationIDToAllocateSeat = sc.nextLine().trim();
                        if (f.checkInAndSeatAllocation(reservationIDToAllocateSeat)) {
                            System.out.println("Check-in Successful!");
                        } else {
                            System.out.println("Not found reservationID or already check-in");
                        }
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 4) {
                    while (true) {

                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 5) {
                    while (true) {
                        f.saveFlightsToFile();
                        f.savePassengersToFile();
                        f.saveRerservationsToFile();
                        f.saveCrewsToFile();
                        System.out.println("Save success");
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 6) {
                    while (true) {
                        f.printAllListFlightsFromFile();
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 7) {
                    while (true) {
                        f.test("VN0|thinh_2123_-1|F1234_Tphcm_Ha Noi_2022-12-2 10:33:33_2022-12-2 10:34:34_5_4|-1|false");
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else {
                    f.saveFlightsToFile();
                    f.savePassengersToFile();
                    f.saveRerservationsToFile();
                    f.saveCrewsToFile();
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

