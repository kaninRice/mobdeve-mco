package com.example.s17.escopete.stevenerrol.arpeggeo.core.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for SQLite Database operations
 */
public class DbHelper extends SQLiteOpenHelper {
    /* Table information */
    public static final String PLAYLIST_TABLE_NAME = "playlist";
    public static final String TAG_TABLE_NAME = "tag";

    /* Table Columns */
    public static final String PLAYLIST_ID = "_id";
    public static final String PLAYLIST_NAME = "name";
    public static final String PLAYLIST_LATITUDE = "latitude";
    public static final String PLAYLIST_LONGITUDE = "longitude";
    public static final String PLAYLIST_URL = "url";
    public static final String PLAYLIST_IMAGE = "image";

    public static final String TAG_ID = "_id";
    public static final String TAG_NAME = "name";
    public static final String TAG_COLOR = "color";
    public static final String TAG_TEXT_COLOR = "text_color";
    public static final String TAG_PLAYLIST_ID = "playlist_id";

    /* Database information */
    static final String DB_NAME = "arpeggeo";
    static int DB_VERSION = 1;

    /* Create table queries */
    private static final String CREATE_PLAYLIST_TABLE = "create table " + PLAYLIST_TABLE_NAME + " ("
            + PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLAYLIST_NAME + " TEXT NOT NULL UNIQUE, "
            + PLAYLIST_LATITUDE + " REAL NOT NULL, "
            + PLAYLIST_LONGITUDE + " REAL NOT NULL, "
            + PLAYLIST_URL + " TEXT NOT NULL, "
            + PLAYLIST_IMAGE + " INTEGER"
            + ");";

    private static final String CREATE_TAG_TABLE = "create table " + TAG_TABLE_NAME + " ("
            + TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TAG_NAME + " TEXT NOT NULL UNIQUE, "
            + TAG_COLOR + " TEXT NOT NULL, "
            + TAG_TEXT_COLOR + " TEXT NOT NULL, "
            + TAG_PLAYLIST_ID + " INTEGER NOT NULL"
            + ");";

    /**
     * Constructor for the DbHelper
     * @param context The context of the PlaylistDbHelper
     */
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when database is created
     * @param sqLiteDatabase The database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PLAYLIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_TAG_TABLE);
    }

    /**
     * Called when database is upgraded
     * @param sqLiteDatabase The database to be upgraded
     * @param i The old version of the database
     * @param i1 The new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAG_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
