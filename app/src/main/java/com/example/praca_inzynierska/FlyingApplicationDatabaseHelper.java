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
        String query_tableAirports="CREATE TABLE Airports (" +
                "Airport_Id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                "Code TEXT NOT NULL," +
                "Name TEXT NOT NULL," +
                "City TEXT NOT NULL," +
                "Country TEXT NOT NULL);";

        String query_tablePassengers="CREATE TABLE Passengers (" +
                "Passenger_Id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Last_name TEXT NOT NULL," +
                "Age INTEGER NOT NULL," +
                "Gender TEXT NOT NULL," +
                "Seat_number TEXT NOT NULL," +
                "IsAdult INTEGER NOT NULL," +
                "Ticket_Id INTEGER NOT NULL," +
                "FOREIGN KEY(Ticket_Id) REFERENCES Tickets(Ticket_Id));";

        String query_tableTickets="CREATE TABLE Tickets (" +
                "Ticket_Id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                "Departure_airport_Id INTEGER NOT NULL," +
                "Arrival_airport_Id INTEGER NOT NULL," +
                "Departure_date TEXT NOT NULL," +
                "Departure_time TEXT NOT NULL," +
                "Flight_duration TEXT NOT NULL," +
                "Flight_number TEXT NOT NULL," +
                "Travel_class_Id INTEGER NOT NULL," +
                "Price REAL NOT NULL," +
                "isConnecting INTEGER NOT NULL,"+
                "FOREIGN KEY(Travel_class_Id) REFERENCES Travel_Class(Class_Id)," +
                "FOREIGN KEY(Departure_airport_Id) REFERENCES Airports(Airport_Id)," +
                "FOREIGN KEY(Arrival_airport_Id) REFERENCES Airports(Airport_Id));";

        String query_tableTravel_Class="CREATE TABLE Travel_Class (" +
                "Class_Id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL);";

        String query_tableTravel_Values1= "INSERT INTO Travel_Class (\"Class_Id\", \"Name\") VALUES ('1', 'ekonomiczna');";
        String query_tableTravel_Values2 = "INSERT INTO Travel_Class (\"Class_Id\", \"Name\") VALUES ('2', 'biznesowa'); ";
        String query_tableTravel_Values3 = "INSERT INTO Travel_Class (\"Class_Id\", \"Name\") VALUES ('3', 'pierwsza');";


        db.execSQL(query_tableAirports);
        db.execSQL(query_tablePassengers);
        db.execSQL(query_tableTickets);
        db.execSQL(query_tableTravel_Class);
        db.execSQL(query_tableTravel_Values1);
        db.execSQL(query_tableTravel_Values2);
        db.execSQL(query_tableTravel_Values3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Airports");
        db.execSQL("DROP TABLE IF EXISTS Passengers");
        db.execSQL("DROP TABLE IF EXISTS Tickets");
        db.execSQL("DROP TABLE IF EXISTS Travel_Class");
        onCreate(db);
    }

    public Cursor getUserTickets(){
        String query = "SELECT t.Ticket_Id, a1.City AS DepartureCity, a1.Code AS DepartureCode, a2.City AS ArrivalCity, a2.Code AS ArrivalCode, Departure_date, Departure_time, Flight_duration, Flight_number, tc.Name, Price, isConnecting" +
        " FROM Tickets t"+
        " JOIN Airports a1 ON a1.Airport_Id = t.Departure_airport_Id" +
        " JOIN Airports a2 ON a2.Airport_Id = t.Arrival_airport_Id" +
        " JOIN Travel_Class tc ON tc.Class_Id = t.Travel_class_Id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getSelectedTickets(int ticketId){
        String query = "SELECT t.Departure_date, t.Departure_time, t.Flight_duration, t.Flight_number, t.Price, t.isConnecting," +
                "a1.Code, a1.Name, a1.City, a1.Country," +
                "a2.Code, a2.Name, a2.City, a2.Country," +
                "tc.Name" +
                " FROM Tickets t" +
                " JOIN Airports a1 ON a1.Airport_Id = t.Departure_airport_Id" +
                " JOIN Airports a2 ON a2.Airport_Id = t.Arrival_airport_Id" +
                " JOIN Travel_Class tc ON tc.Class_Id = t.Travel_class_Id" +
                " WHERE t.Ticket_Id="+ticketId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public int getNumberAdultPassengers(int TicketId){
        String query = "SELECT count(*) FROM Passengers WHERE IsAdult=1 AND Ticket_Id="+TicketId;
        int count = 0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getNumberChildrenPassengers(int TicketId){
        String query = "SELECT count(*) FROM Passengers WHERE IsAdult=0 AND Ticket_Id="+TicketId;
        int count = 0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int setAirport(String airportCode, String airportName, String airportCity, String airportCountry ){
        String query="SELECT Airport_Id FROM Airports WHERE Code='"+airportCode+"'";
        int airportId = 0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            airportId = cursor.getInt(0);
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("Code", airportCode);
            cv.put("Name", airportName);
            cv.put("City", airportCity);
            cv.put("Country", airportCountry);
            long result = db.insert("Airports", null, cv);
            if (result == -1) {
                Toast.makeText(context, "Nie udało się dodać lotniska.", Toast.LENGTH_SHORT).show();
            }else{
                cursor = getReadableDatabase().rawQuery(query, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    airportId = cursor.getInt(0);
                }
            }
        }
        cursor.close();
        return airportId;
    }

    public int setTravelClass(String travelClassName){
        String query="SELECT Class_Id FROM Travel_Class WHERE Name='"+travelClassName+"'";
        int travelClassId=0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            travelClassId = cursor.getInt(0);
        }
        cursor.close();
        return travelClassId;
    }

    public void addTicket(AirportModel departureAirport, AirportModel arrivalAirport, String departureDate,String departureTime, String flightDuration,  String flightNumber, String travelClass, double price, int TicketConnecting) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Departure_airport_Id",setAirport(departureAirport.getAirportCode(),departureAirport.getAirportName(),departureAirport.getAirportCity(),departureAirport.getAirportCountry()));
        cv.put("Arrival_airport_Id",setAirport(arrivalAirport.getAirportCode(),arrivalAirport.getAirportName(),arrivalAirport.getAirportCity(),arrivalAirport.getAirportCountry()));

        cv.put("Departure_date",departureDate);
        cv.put("Departure_time",departureTime);

        cv.put("Flight_duration",flightDuration);
        cv.put("Flight_number",flightNumber);

        cv.put("Travel_class_Id",setTravelClass(travelClass));

        cv.put("Price",price);
        cv.put("isConnecting",TicketConnecting);

        long result = db.insert("Tickets", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać biletu.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPassenger(String passengerName, String passengerLastName, int passengerAge, String passengerGender, String passengerSeat,int passengerIsAdult){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", passengerName);
        cv.put("Last_name", passengerLastName);
        cv.put("Age", passengerAge);
        cv.put("Gender", passengerGender);
        cv.put("Seat_number", passengerSeat);
        cv.put("IsAdult", passengerIsAdult);
        cv.put("Ticket_Id", getLastTicketId());
        long result = db.insert("Passengers", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się dodać pasażera.", Toast.LENGTH_SHORT).show();
        }
    }

    public int getLastTicketId(){
        String query="SELECT Count(Ticket_Id) from Tickets";
        int TicketId=0;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            TicketId = cursor.getInt(0);
        }else {
            TicketId=1;
        }
        cursor.close();
        return TicketId;
    }

    public Cursor getPassengersFromTicket(int ticketId){
        String query = "SELECT * FROM Passengers WHERE Ticket_Id=" + ticketId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void removeTicket(int ticketId ){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Tickets", "Ticket_Id="+ticketId, null);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się usunąć biletu.", Toast.LENGTH_SHORT).show();
        } else {
            db.execSQL("DELETE FROM Passengers WHERE Ticket_Id="+ ticketId+";");
        }
    }

    public void removePassenger(int passengerId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Passengers", "Passenger_Id="+passengerId, null);
        if (result == -1) {
            Toast.makeText(context, "Nie udało się usunąć pasażera.", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    public void updatePassenger(String passengerId, String passengerName, String passengerLastName, int passengerAge, String passengerGender, String passengerSeat,int passengerIsAdult, int passengerTicketId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", passengerName);
        cv.put("Last_name", passengerLastName);
        cv.put("Age", passengerAge);
        cv.put("Gender", passengerGender);
        cv.put("Seat_number", passengerSeat);
        cv.put("IsAdult", passengerIsAdult);
        cv.put("Ticket_Id", passengerTicketId);
        long result = db.update("Passengers", cv, "Passenger_Id=?", new String[]{passengerId});
        if(result == -1){
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

}
