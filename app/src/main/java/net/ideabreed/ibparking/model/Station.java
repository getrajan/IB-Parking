package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import java.io.Serializable;

public class Station implements Serializable {
    private String stationName;
    private String stationCode;
    private boolean isCurrent;
    private int stationId;

    public Station(String stationName, String stationCode) {
        this.stationName = stationName;
        this.stationCode = stationCode;
    }

    public Station(String stationName, String stationCode, boolean isCurrent, int stationId) {
        this.stationName = stationName;
        this.stationCode = stationCode;
        this.isCurrent = isCurrent;
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getStationId() {

        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
}