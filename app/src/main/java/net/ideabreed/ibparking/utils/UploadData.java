package net.ideabreed.ibparking.utils; /*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.Bus;
import net.ideabreed.ibparking.model.Passenger;
import net.ideabreed.ibparking.model.Rates;
import net.ideabreed.ibparking.model.Station;

import java.util.ArrayList;

public class UploadData {
    public UploadData() {
    }

    public ArrayList<Station> allStations() {
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(new Station("Kirtipur Round", "01", false, 1));
        stations.add(new Station("Kirtipur Gate", "02", false, 2));
        stations.add(new Station("Tinkune", "03", false, 3));
        stations.add(new Station("Naya Bato", "04", false, 4));
        stations.add(new Station("Tu Gate", "05", false, 5));
        stations.add(new Station("Kumari Club", "05", false, 5));
        return stations;
    }

    public ArrayList<Passenger> allPassenger() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger("Normal", R.drawable.ic_normal_person_34, 1, "01", 0));
        passengers.add(new Passenger("Elderly", R.drawable.ic_elderly_34, 1, "02", 0.45));
        passengers.add(new Passenger("Student", R.drawable.ic_student_34, 1, "03", 0.4));
        return passengers;
    }

    public ArrayList<Rates> allRates() {
        ArrayList<Rates> rates = new ArrayList<>();
        rates.add(new Rates(1, 1, 2, 14));
        rates.add(new Rates(2, 1, 3, 14));
        rates.add(new Rates(3, 1, 4, 15));
        rates.add(new Rates(4, 1, 5, 15));
        rates.add(new Rates(5, 1, 6, 16));
        return rates;
    }

    public ArrayList<Bus> allBuses(){
        ArrayList<Bus> buses = new ArrayList<>();
        buses.add(new Bus("BA 2 KHA 2222","01",1));
        buses.add(new Bus("GA 3 KHA 2242","02",2));
        return buses;
    }

} 