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
    private final static String TABLE_NAME = "user_tickets";

    private final static String COLUMN_ID = "_id";
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

    public FlyingApplicationDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTicket(String departure_airport_code, String departure_airport_name, String arrival_airport_code, String arrival_airport_name,
                          String flight_duration, String departure_date, String departure_time, String travel_class, String flight_number,
                          double price, int number_of_passengers){
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

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Nie udało się dodać biletu.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Dodano nowy bilet!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void deleteTicket(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Nie udało się usunąć biletu.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Usunięto bilet.", Toast.LENGTH_SHORT).show();
        }
    }
}
