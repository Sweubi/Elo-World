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
    private static String TABLE_USER="user";
    SQLiteDatabase db;

    private static final String KEY_ID = "id";
    private static final String IDUSER ="idUser";
    private static final String PSEUDO ="pseudo";
    private static final String EMAIL ="email";
    private static final String LEVEL ="level";
    private static final String SERVER ="server";
    private static final String RANK ="rank";
    private static final String IDSUMMONER ="IdSummoner";
    private static final String ICON ="ProfileIconId";


    public SQLiteHandler(Context context) {
        super(context,DATABASE_NAME,null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE "+ TABLE_USER +"("
                + KEY_ID+ " INTEGER PRIMARY KEY,"
                + IDUSER+ " INTEGER ,"
                + PSEUDO+" TEXT,"
                + EMAIL+ " TEXT,"
                + LEVEL+ " INTEGER,"
                + SERVER +" TEXT,"
                + RANK+" TEXT,"
                + IDSUMMONER+" INTEGER,"
                + ICON+" INTEGER"+")";
        db.execSQL(CREATE_USER_TABLE);
        Log.d(TABLE_USER,CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER+";");

        // Create tables again
        onCreate(db);
    }

    public void addUser(int idUser,String pseudo,String email,int level,String server,String rank,int idSummoner,int idIcon){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDUSER,idUser);
        values.put(EMAIL,email);
        values.put(PSEUDO,pseudo);
        values.put(LEVEL,level);
        values.put(SERVER,server);
        values.put(RANK,rank);
        values.put(IDSUMMONER,idSummoner);
        values.put(ICON,idIcon);
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        Log.d(TABLE_USER, "New user inserted into sqlite: "+db);
    }


    public HashMap<String, Object> getUser(){
        HashMap<String,Object> user = new HashMap();
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER+";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() >0) {
            user.put(IDUSER,cursor.getInt(1));
            user.put(PSEUDO,cursor.getString(2));
            user.put(EMAIL,cursor.getString(3));
            user.put(LEVEL,cursor.getInt(4));
            user.put(SERVER,cursor.getString(5));
            user.put(RANK,cursor.getString(6));
            user.put(IDSUMMONER,cursor.getInt(7));
            user.put(ICON, cursor.getInt(8));
        }
        Log.d("test", "Fetching user from Sqlite: " + user.toString());
        return user;


    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
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
        db.delete(TABLE_USER, null, null);

        db.close();

        Log.d(TABLE_USER, "Deleted all user info from sqlite");
    }
}
