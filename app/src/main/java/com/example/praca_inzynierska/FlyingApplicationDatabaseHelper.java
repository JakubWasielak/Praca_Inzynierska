package com.example.praca_inzynierska;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FlyingApplicationDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private final static String DATABASE_NAME = "FlyingApplication.db";
    private final static int DATABASE_VERSION = 1;

    private final static String TABLE_NAME1 = "user_tickets";
    private final static String COLUMN_ID_USER_TICKETS = "_id";
    private final static String COLUMN_DEPARTUREAIRPORTCODE = "departure_airport_code";
    private final static String COLUMN_DEPARTUREAIRPORTCITYNAME = "departure_airport_name";
    private final static String COLUMN_ARRIVALAIRPORTCODE = "arrival_airport_code";
    private final static String COLUMN_ARRIVALAIRPORTCITYNAME = "arrival_airport_name";
    private final static String COLUMN_FLIGHTDURATION = "flight_duration";
    private final static String COLUMN_DEPARTUREDATE = "departure_date";
    private final static String COLUMN_DEPARTURETIME = "departure_time";
    private final static String COLUMN_TRAVELCLASS = "travel_class";
    private final static String COLUMN_FLIGHTNUMBER = "flight_number";
    private final static String COLUMN_TICKETPRICE = "price";
    private final static String COLUMN_NUMBERPASSENGERS = "number_of_passengers";

    private final static String TABLE_NAME2 = "passengers";
    private final static String COLUMN_ID_PASSENGERS = "_id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_LAST_NAME = "last_name";
    private final static String COLUMN_AGE = "age";
    private final static String COLUMN_GENDER = "gender";
    private final static String COLUMN_ID_TICKET = "ticked_id";

    public FlyingApplicationDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_tickets = "CREATE TABLE " + TABLE_NAME1 +
                " (" + COLUMN_ID_USER_TICKETS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DEPARTUREAIRPORTCODE + " TEXT, " +
                COLUMN_DEPARTUREAIRPORTCITYNAME + " TEXT, " +
                COLUMN_ARRIVALAIRPORTCODE + " TEXT, " +
                COLUMN_ARRIVALAIRPORTCITYNAME + " TEXT, " +
                COLUMN_FLIGHTDURATION + " TEXT, " +
                COLUMN_DEPARTUREDATE + " TEXT, " +
                COLUMN_DEPARTURETIME + " TEXT, " +
                COLUMN_TRAVELCLASS + " TEXT, " +
                COLUMN_FLIGHTNUMBER + " TEXT, " +
                COLUMN_TICKETPRICE + " DOUBLE, " +
                COLUMN_NUMBERPASSENGERS + " INTEGER);";

        String passengers = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID_PASSENGERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_GENDER + " INTEGER, " +
                COLUMN_ID_TICKET + " INTEGER, " +
                "FOREIGN KEY(ticked_id) REFERENCES user_tickets(_id));";

        db.execSQL(user_tickets);
        db.execSQL(passengers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public void addTicket(String departure_airport_code, String departure_airport_name, String arrival_airport_code, String arrival_airport_name, String flight_duration, String departure_date, String departure_time, String travel_class, String flight_number, double price, int number_of_passengers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DEPARTUREAIRPORTCODE, departure_airport_code);
        cv.put(COLUMN_DEPARTUREAIRPORTCITYNAME, departure_airport_name);
        cv.put(COLUMN_ARRIVALAIRPORTCODE, arrival_airport_code);
        cv.put(COLUMN_ARRIVALAIRPORTCITYNAME, arrival_airport_name);
        cv.put(COLUMN_FLIGHTDURATION, flight_duration);
        cv.put(COLUMN_DEPARTUREDATE, departure_date);
        cv.put(COLUMN_DEPARTURETIME, departure_time);
        cv.put(COLUMN_TRAVELCLASS, travel_class);
        cv.put(COLUMN_FLIGHTNUMBER, flight_number);
        cv.put(COLUMN_TICKETPRICE, price);
        cv.put(COLUMN_NUMBERPASSENGERS, number_of_passengers);

        long result = db.insert(TABLE_NAME1, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać biletu.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteTicket(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME1, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Nie udało się usunąć biletu.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Usunięto bilet.", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllTickets() {
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void addPassager(String name, String lastName, int age, String gender, int tickedID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_LAST_NAME,lastName);
        cv.put(COLUMN_AGE,age);
        cv.put(COLUMN_GENDER,gender);
        cv.put(COLUMN_ID_TICKET,tickedID);
        long result = db.insert(TABLE_NAME2, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać pasażera.", Toast.LENGTH_SHORT).show();
        }
    }

    public int getNextTickedID(){
        String query="SELECT Count(_id) from user_tickets";
        int tikedID=0;
        Cursor cursor = getReadableDatabase().rawQuery(query,null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            tikedID =cursor.getInt(0);
        }
        cursor.close();

        return tikedID;
    }


}
