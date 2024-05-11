/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author hoang
 */

public class Reservations {

    public static int countReservationAndSeat = 0;
    public static int incReservationID = 0;
    private String reservationID;
    private Passenger passenger;
    private Flight flight;
    private int seatNumber;
    private boolean isCheckIn;

    public Reservations(String reservationID, Passenger passenger, Flight flight) {
        this.reservationID = reservationID;
        this.passenger = passenger;
        this.flight = flight;
        this.isCheckIn = false;
        this.seatNumber = -1;
    }

    public Reservations(String reservationID, Passenger passenger, Flight flight, int seatNumber, boolean isCheckIn) {
        this.reservationID = reservationID;
        this.passenger = passenger;
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.isCheckIn = isCheckIn;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getReservationID() {
        return reservationID;
    }

    public boolean isIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(boolean isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String writeToFile() {
        return reservationID + "|" + passenger.writeToFile() + "|" + flight.writeToFile() + "|" + seatNumber + "|" +isCheckIn;
    }

    @Override
    public String toString() {
        return reservationID + "_" + passenger + "_" + flight;
    }

}

