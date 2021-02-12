package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
    private String type;
    private String checkInStation;
    private String checkOutStation;
    private String checkInTime;
    private Date checkOutTime;
    private Date date;
    private double fare;
    private String receiptId;

    public Ticket(String type, String checkInStation) {
        this.type = type;
        this.checkInStation = checkInStation;

    }

    public Ticket(String type, String checkInStation, String checkOutStation, String checkInTime, Date checkOutTime, Date date, double fare) {
        this.type = type;
        this.checkInStation = checkInStation;
        this.checkOutStation = checkOutStation;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.date = date;
        this.fare = fare;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckInStation() {
        return checkInStation;
    }

    public void setCheckInStation(String checkInStation) {
        this.checkInStation = checkInStation;
    }

    public String getCheckOutStation() {
        return checkOutStation;
    }

    public void setCheckOutStation(String checkOutStation) {
        this.checkOutStation = checkOutStation;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}