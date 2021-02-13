package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

public class Rates {

    private int rateId;
    private int stationStart;
    private int stationEnd;
    private int rate;

    public Rates(int rateId, int stationStart, int stationEnd, int rate) {
        this.rateId = rateId;
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.rate = rate;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getStationStart() {
        return stationStart;
    }

    public void setStationStart(int stationStart) {
        this.stationStart = stationStart;
    }

    public int getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(int stationEnd) {
        this.stationEnd = stationEnd;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}