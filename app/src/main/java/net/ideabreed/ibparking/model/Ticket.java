package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
    private Passenger type;
    private Station checkInStation;
    private Station checkOutStation;
    private String checkInTime;
    private String checkOutTime;
    private Date date;
    private User checkInUser;
    private int fare;
    private String receiptId;

    public Ticket(Passenger type, Station checkInStation) {
        this.type = type;
        this.checkInStation = checkInStation;
    }

    public Ticket(Station checkOutStation, String checkOutTime, int fare, String receiptId
    ) {
        this.checkOutStation = checkOutStation;
        this.checkOutTime = checkOutTime;
        this.fare = fare;
        this.receiptId = receiptId;
    }

    public Ticket(Passenger type, Station checkInStation, String checkInTime, User checkInUser, String receiptId) {
        this.type = type;
        this.checkInStation = checkInStation;
        this.checkInTime = checkInTime;
        this.checkInUser = checkInUser;
        this.receiptId = receiptId;
    }

    public Ticket(Passenger type, Station checkInStation, Station checkOutStation, String checkInTime, String checkOutTime, Date date, int fare) {
        this.type = type;
        this.checkInStation = checkInStation;
        this.checkOutStation = checkOutStation;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.date = date;
        this.fare = fare;
    }

    public User getCheckInUser() {
        return checkInUser;
    }

    public void setCheckInUser(User checkInUser) {
        this.checkInUser = checkInUser;
    }

    public Passenger getType() {
        return type;
    }

    public void setType(Passenger type) {
        this.type = type;
    }

    public Station getCheckInStation() {
        return checkInStation;
    }

    public void setCheckInStation(Station checkInStation) {
        this.checkInStation = checkInStation;
    }

    public Station getCheckOutStation() {
        return checkOutStation;
    }

    public void setCheckOutStation(Station checkOutStation) {
        this.checkOutStation = checkOutStation;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}