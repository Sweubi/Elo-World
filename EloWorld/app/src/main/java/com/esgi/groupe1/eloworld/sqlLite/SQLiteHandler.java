package com.esgi.groupe1.eloworld.sqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Christopher on 27/04/2015.
 */
public class SQLiteHandler extends SQLiteOpenHelper {
    private static int version =1;
    private static String DATABASE_NAME ="eloworld";
    private static String DATABASE_TABLE_NAME="user";
    SQLiteDatabase db;

    private String C_IDUSER ="idUser";
    private String C_PSEUDO ="pseudo";
    private String C_EMAIL ="email";
    private String C_LEVEL ="level";
    private String C_SERVER ="server";
    private String C_RANK ="rank";
    private String C_IDSUMMONER ="IdSummoner";
    private String C_ICON ="ProfileIconId";
    public SQLiteHandler(Context context) {
        super(context,DATABASE_NAME,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TAB ="CREATE TABLE"+DATABASE_TABLE_NAME+"("+
                C_IDUSER+"INTEGER PRIMARY KEY,"+C_PSEUDO+"TEXT,"+
                C_EMAIL+"TEXT,"+C_LEVEL+"INTEGER,"+C_SERVER+"TEXT,"+
                C_RANK+"TEXT,"+C_IDSUMMONER+"INTEGER"+C_ICON+"INTEGER"+")";
        db.execSQL(CREATE_LOGIN_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void createUser(int iduser,String email,String pseudo,String server, String rank,int level,int idSummoner,int idicone){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_IDUSER,iduser);
        values.put(C_PSEUDO,pseudo);
        values.put(C_EMAIL,email);
        values.put(C_LEVEL,level);
        values.put(C_RANK,rank);
        values.put(C_SERVER,server);
        values.put(C_IDSUMMONER,idSummoner);
        values.put(C_ICON,idicone);
        db.insert(DATABASE_TABLE_NAME, null, values);
        db.close(); // Closing database connection
        Log.d("Creation", String.valueOf(values));
    }

    public HashMap<String,Object> getUser(){
        HashMap<String, Object> user = new HashMap();
        db =this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("iduser", cursor.getInt(1));
            user.put("email",cursor.getString(2));
            user.put("pseudo",cursor.getString(3));
            user.put("server",cursor.getString(4));
            user.put("rank",cursor.getString(5));
            user.put("level",cursor.getInt(6));
            user.put("idSummoner",cursor.getInt(7));
            user.put("idicone",cursor.getInt(7));
        }
        Log.d("test", "Fetching user from Sqlite: " + user.toString());
        return user;


    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(DATABASE_TABLE_NAME, null, null);
        db.close();

        Log.d(DATABASE_TABLE_NAME, "Deleted all user info from sqlite");
    }
}
