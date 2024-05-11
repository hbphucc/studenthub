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

public class Passenger {
    private int seatNumber;
    private String name;
    private String contactDetails;

    public Passenger(String name, String contactDetails) {
        this.name = name;
        this.contactDetails = contactDetails;
        this.seatNumber = -1;
    }

    public Passenger(String name, String contactDetails, int seatNumber) {
        this.seatNumber = seatNumber;
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String writeToFile(){
        return name + "_" + contactDetails + "_" + seatNumber;
    }
    
    @Override
    public String toString() {
        return name + "_" + contactDetails;
    }

}
