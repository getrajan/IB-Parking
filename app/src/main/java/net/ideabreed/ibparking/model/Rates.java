package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

public class Rates {

    private String rateId;
    private String stationStart;
    private String stationEnd;
    private int rate;

    public Rates(String rateId, String stationStart, String stationEnd, int rate) {
        this.rateId = rateId;
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.rate = rate;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public String getStationStart() {
        return stationStart;
    }

    public void setStationStart(String stationStart) {
        this.stationStart = stationStart;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(String stationEnd) {
        this.stationEnd = stationEnd;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}