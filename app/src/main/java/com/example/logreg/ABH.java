package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ABH extends SQLiteOpenHelper {

    private static final int DBversion = 1;

    private static final String DBname = "felhasznalok.db";

    private static final String TABLE_NAME = "felhasznalo ";

    private static final String COL_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_FELHNEV = "felhnev";
    private static final String COL_JELSZO = "jelszo";
    private static final String COL_JTELJESNEV = "teljesnev";

    public ABH(Context context) {
        super(context, DBname, null, DBversion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTables = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + " VARCHAR(30)," +
                COL_FELHNEV + " VARCHAR(30)," +
                COL_JELSZO + " VARCHAR(30)," +
                COL_JTELJESNEV + " VARCHAR(30))";
        db.execSQL(createTables);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean adatFelvetel(String email, String felhNev, String jelszo, String jteljesNev){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL,email);
        values.put(COL_FELHNEV, felhNev);
        values.put(COL_JELSZO, passwordHash(jelszo));
        values.put(COL_JTELJESNEV, jteljesNev);

        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public Cursor userOk(String felhnevEmail, String jelszo) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor eredmeny = database.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                    " WHERE (" +
                        COL_FELHNEV + " LIKE '" + felhnevEmail +
                        "' OR " +
                        COL_EMAIL + " LIKE '" + felhnevEmail +
                        "') AND " +
                        COL_JELSZO + " = '" + jelszo + "'"
                , null);
        return eredmeny;
    }

    public String passwordHash(String jelszo) {
        String hash = "";
        for (char c : jelszo.toCharArray()){
            hash += (byte)c;
        }
        return hash;
    }

    public boolean emailIsValid(String email) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Boolean response = email.matches(EMAIL_REGEX);
        return response;
    }

}
