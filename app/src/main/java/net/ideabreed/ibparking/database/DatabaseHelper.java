package net.ideabreed.ibparking.database; /*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import net.ideabreed.ibparking.model.Bus;
import net.ideabreed.ibparking.model.Passenger;
import net.ideabreed.ibparking.model.Rates;
import net.ideabreed.ibparking.model.Station;
import net.ideabreed.ibparking.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    //    USER TABLE DATA
    public static final String USER_TABLE = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_STATUS = "is_login";
    public static final String COLUMN_USER_ID = "user_id";

    //    BUS TABLE DATA
    public static final String BUS_TABLE = "buses";
    public static final String COLUMN_BUS_NUMBER = "bus_number";
    public static final String COLUMN_BUS_CODE = "bus_code";
    public static final String COLUMN_BUS_ID = "bus_id";

    //    STATION TABLE DATA
    public static final String STATION_TABLE = "stations";
    public static final String COLUMN_STATION_CODE = "station_code";
    public static final String COLUMN_STATION_NAME = "station_name";
    public static final String COLUMN_STATION_ID = "station_id";
    public static final String COLUMN_STATION_STATUS = "is_current";

    //    PASSENGER TABLE DATA
    public static final String PASSENGER_TABLE = "passengers";
    public static final String COLUMN_PASSENGER_TYPE = "type";
    public static final String COLUMN_PASSENGER_CODE = "code";
    public static final String COLUMN_PASSENGER_DISCOUNT = "discount";
    public static final String COLUMN_PASSENGER_ID = "passenger_id";

    //    RATES TABLE DATA
    public static final String RATE_TABLE = "rates";
    public static final String COLUMN_STATION_START = "station_start";
    public static final String COLUMN_STATION_END = "station_end";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_RATE_ID = "rate_id";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "kipu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createBusTable(db);
        createStationTable(db);
        createPassengerTable(db);
        crateRateTable(db);
    }

    private void crateRateTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + RATE_TABLE + "(" + COLUMN_RATE_ID + " INTEGER PRIMARY KEY, " + COLUMN_STATION_START + " TEXT NOT NULL," + COLUMN_STATION_END + " TEXT NOT NULL," + COLUMN_RATE + " INTEGER NOT NULL)";
        db.execSQL(createTableStatement);
    }

    private void createPassengerTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PASSENGER_TABLE + "(" + COLUMN_PASSENGER_ID + " INTEGER PRIMARY KEY, " + COLUMN_PASSENGER_TYPE + " TEXT NOT NULL," + COLUMN_PASSENGER_CODE + " TEXT NOT NULL," + COLUMN_PASSENGER_DISCOUNT + " INTEGER NOT NULL)";
        db.execSQL(createTableStatement);
    }

    private void createStationTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STATION_TABLE + "(" + COLUMN_STATION_ID + " INTEGER PRIMARY KEY, " + COLUMN_STATION_CODE + " TEXT NOT NULL," + COLUMN_STATION_NAME + " TEXT NOT NULL," + COLUMN_STATION_STATUS + " INTEGER DEFAULT 0 NOT NULL)";
        db.execSQL(createTableStatement);
    }

    private void createBusTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BUS_TABLE + "(" + COLUMN_BUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BUS_NUMBER + " TEXT NOT NULL," + COLUMN_BUS_CODE + " TEXT NOT NULL)";
        db.execSQL(createTableStatement);
    }

    private void createUserTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT NOT NULL," + COLUMN_PASSWORD + " TEXT NOT NULL," + COLUMN_STATUS + " INTEGER DEFAULT 0 NOT NULL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //    ADD USER TO USER_TABLE
    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //ADD BUS TO BUS_TABLE
    public boolean addBus(Bus bus) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BUS_NUMBER, bus.getBusNumber());
        cv.put(COLUMN_BUS_CODE, bus.getBusCode());

        long insert = db.insert(BUS_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    ADD STATION TO STATION_TABLE
    public boolean addStation(Station station) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATION_ID, station.getStationId());
        cv.put(COLUMN_STATION_NAME, station.getStationName());
        cv.put(COLUMN_STATION_CODE, station.getStationCode());

        long insert = db.insert(STATION_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    ADD PASSENGER TO PASSENGER_TABLE
    public boolean addPassenger(Passenger passenger) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSENGER_ID, passenger.getPassengerId());
        cv.put(COLUMN_PASSENGER_TYPE, passenger.getType());
        cv.put(COLUMN_PASSENGER_CODE, passenger.getCode());
        cv.put(COLUMN_PASSENGER_DISCOUNT, passenger.getDiscount());

        long insert = db.insert(PASSENGER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    ADD RATE TO RATE_TABLE
    public boolean addRate(Rates rate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RATE_ID, rate.getRateId());
        cv.put(COLUMN_STATION_START, rate.getStationStart());
        cv.put(COLUMN_STATION_END, rate.getStationEnd());
        cv.put(COLUMN_RATE, rate.getRate());

        long insert = db.insert(RATE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
}