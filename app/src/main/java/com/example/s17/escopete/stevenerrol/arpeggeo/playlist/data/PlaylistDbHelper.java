package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for SQLite Database operations
 */
public class PlaylistDbHelper extends SQLiteOpenHelper {
    /* Table information */
    public static final String TABLE_NAME = "playlist";

    /* Table Columns */
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String URL = "url";
    public static final String IMAGE = "image";

    /* Database information */
    static final String DB_NAME = "arpeggeo";
    static int DB_VERSION = 1;

    /* Create table query */
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL UNIQUE, "
            + LATITUDE + " REAL NOT NULL, "
            + LONGITUDE + " REAL NOT NULL, "
            + URL + " TEXT NOT NULL, "
            + IMAGE + " INTEGER"
            + ");";

    /**
     * Constructor for the PlaylistDbHelper
     * @param context The context of the PlaylistDbHelper
     */
    public PlaylistDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when database is created
     * @param sqLiteDatabase The database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    /**
     * Called when database is upgraded
     * @param sqLiteDatabase The database to be upgraded
     * @param i The old version of the database
     * @param i1 The new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
