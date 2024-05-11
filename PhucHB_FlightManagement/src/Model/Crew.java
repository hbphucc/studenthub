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
public class Crew {

    Flight flight;
    String pilot;
    String flightAttendant;
    String groundStaff;

    public Crew(Flight flight, String pilot, String flightAttendant, String groundStaff) {
        this.flight = flight;
        this.pilot = pilot;
        this.flightAttendant = flightAttendant;
        this.groundStaff = groundStaff;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public String getFlightAttendant() {
        return flightAttendant;
    }

    public void setFlightAttendant(String flightAttendant) {
        this.flightAttendant = flightAttendant;
    }

    public String getStaff() {
        return groundStaff;
    }

    public void setStaff(String groundStaff) {
        this.groundStaff = groundStaff;
    }
    
    public String writeToFile() {
        return null;
    }
}
