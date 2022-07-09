package com.company;

import java.io.Serializable;
import java.util.Date;

abstract public class Human implements Serializable {
   // Integer idHuman;
    private String name;
    private String dateG;
    private String polisOMS;
    private String address;

   public Human(){}

    public Human(String name, String dateG, String polisOMS, String address) {
        this.name = name;
        this.dateG = dateG;
        this.polisOMS = polisOMS;
        this.address = address;
    }

    public void infoHuman() {
        System.out.print(this.name + " | " + this.dateG + " | " +  polisOMS + " | " + address + " | \n");
    }

    public String getName() {
        return name;
    }

    public String getDateG() {
        return dateG;
    }

    public String getPolisOMS() {
        return polisOMS;
    }

    public String getAddress() {
        return address;
    }
}
