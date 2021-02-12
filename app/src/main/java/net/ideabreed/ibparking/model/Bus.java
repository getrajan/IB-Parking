package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

public class Bus {
    private String busNumber;
    private String busCode;
    private int id;

    public Bus(String busNumber, String busCode,int id) {
        this.busNumber = busNumber;
        this.busCode = busCode;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }
}