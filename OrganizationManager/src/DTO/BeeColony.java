/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author hoang
 */
public class BeeColony extends Colony implements Role{
    private String type;

    public BeeColony(String place, int size) {
        super(place, size);
    }

    public BeeColony(  int size, String place, String type) {
        super(place, size);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
      @Override
    public String toString() {
        return "the colony’s type is " + this.type + ", size is about " + this.size + ", and the place is " + this.place;
    }
    
    @Override
    public void createWorker() {
        System.out.println("Worker bees perform all the work of the bees");
    }
}
