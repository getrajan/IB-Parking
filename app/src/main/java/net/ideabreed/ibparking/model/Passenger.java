package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/9/21
 * Copyright @2021
 */

public class Passenger {

    private String type;
    private int icon;
    private int passengerId;
    private String code;
    private int discount;

    public Passenger(String type, int icon, int passengerId, String code, int discount) {
        this.type = type;
        this.icon = icon;
        this.passengerId = passengerId;
        this.code = code;
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}