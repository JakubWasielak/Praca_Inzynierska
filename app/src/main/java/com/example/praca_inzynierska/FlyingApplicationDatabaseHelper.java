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

    public FlyingApplicationDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_tickets = "CREATE TABLE Tickets (Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "Departure_airport_code TEXT, \n" +
                "Departure_airport_name TEXT, \n" +
                "Arrival_airport_code TEXT, \n" +
                "Arrival_airport_name TEXT,\n" +
                "Flight_duration TEXT, \n" +
                "Departure_date DATE, \n" +
                "Departure_time TIME, \n" +
                "Flight_number TEXT,\n" +
                "Class_travel TEXT,\n" +
                "Price DOUBLE,\n" +
                "Number_adults_passengers INTEGER,\n" +
                "Number_children_passengers INTEGER,\n" +
                "Ticket_connecting BOOLEAN);";


        String query_passengers = "CREATE TABLE Passenger ( Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "Name TEXT,\n" +
                "Last_name TEXT, \n" +
                "Age  INTEGER,\n" +
                "Gender TEXT,\n" +
                "Seat TEXT,\n" +
                "Ticked_id  INTEGER,\n" +
                "FOREIGN KEY(Ticked_id) REFERENCES Tickets(Id));";

        db.execSQL(query_tickets);
        db.execSQL(query_passengers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Tickets");
        db.execSQL("DROP TABLE IF EXISTS Passenger");
        onCreate(db);
    }

    Cursor readAllTickets() {
        String query = "SELECT * FROM Tickets";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void addNewTicket(String departureCode, String departureName, String arrivalCode, String arrivalName, String flightDuration, String departureDate, String departureTime, String flightNumber, String travelClass, double price, int NumberPassengersAdults, int NumberPassengersChildren, int TicketConnecting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Departure_airport_code", departureCode);
        cv.put("Departure_airport_name", departureName);
        cv.put("Arrival_airport_code", arrivalCode);
        cv.put("Arrival_airport_name", arrivalName);
        cv.put("Flight_duration", flightDuration);
        cv.put("Departure_date", departureDate);
        cv.put("Departure_time", departureTime);
        cv.put("Flight_number", flightNumber);
        cv.put("Class_travel", travelClass);
        cv.put("Price", price);
        cv.put("Number_adults_passengers", NumberPassengersAdults);
        cv.put("Number_children_passengers", NumberPassengersChildren);
        cv.put("Ticket_connecting", TicketConnecting);

        long result = db.insert("Tickets", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać biletu.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteSelectedTicket(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Tickets", "ID="+Id, null);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się usunąć biletu.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Usunęto bilet.", Toast.LENGTH_SHORT).show();
            deleteSelectedPassenger(Id);
        }
    }


    Cursor readAllPassengers(int Ticked_id) {
        String query = "SELECT * FROM Passenger WHERE Ticked_id=" + Ticked_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void addNewPassager(String name, String lastName, int age, String gender, String seat, int tickedID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", name);
        cv.put("Last_name", lastName);
        cv.put("Age", age);
        cv.put("Gender", gender);
        cv.put("Seat", seat);
        cv.put("Ticked_id", tickedID);
        long result = db.insert("Passenger", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać pasażera.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteSelectedPassenger(int tickedID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Passenger WHERE Id="+ tickedID+";");

    }

    void deleteAllPassengers(int tickedID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Passenger WHERE Ticked_id="+ tickedID+";");
    }

    public int getTickedIDFromPassenger(int passengerID){
        int tikedID=0;
        String query = "SELECT Ticked_id FROM Passenger WHERE Id="+passengerID;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tikedID = cursor.getInt(0);
        }
        cursor.close();
        System.out.println("klasa baza");
        System.out.println(tikedID);
        return tikedID;

    }

    public int getNextTickedID() {
        String query = "SELECT Count(Id) from Tickets";
        int tikedID = 0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tikedID = cursor.getInt(0);
        }
        cursor.close();
        return tikedID;
    }

    void updateSelectedTicket(int tickedID, int numberAdultsPassengers) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Tickets SET Number_adults_passengers ="+ numberAdultsPassengers +" WHERE Id = "+tickedID+";");

    }
    void updatePassenger(String passengerID, String passengerName, String passengerLastName,int passengerAge, String passengerGender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", passengerName);
        cv.put("Last_name", passengerLastName);
        cv.put("Age", passengerAge);
        cv.put("Gender", passengerGender);

        long result = db.update("Passenger", cv, "id=?", new String[]{passengerID});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE Passenger SET Name ="+passengerName+", Last_name="+passengerLastName+", Age="+passengerAge+", Gender="+passengerGender+"  WHERE Id ="+passengerID+";");
    }

}
