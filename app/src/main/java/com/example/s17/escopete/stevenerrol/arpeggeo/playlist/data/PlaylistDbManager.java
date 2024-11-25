package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.IMAGE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.LATITUDE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.LONGITUDE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.TABLE_NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper.URL;
import static com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper._ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

public class PlaylistDbManager {
    private SQLiteDatabase sqLiteDatabase;
    private PlaylistDbHelper playlistDbHelper;
    private final Context context;

    public PlaylistDbManager(Context context) {
        this.context = context;
    }

    public PlaylistDbManager open() throws SQLException {
        playlistDbHelper = new PlaylistDbHelper(context);
        sqLiteDatabase = playlistDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        playlistDbHelper.close();
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                _ID,
                NAME,
                URL,
                IMAGE,
                LATITUDE,
                LONGITUDE,
        };

        Cursor cursor = sqLiteDatabase.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }
        
        return cursor;
    }

    public long getHighestId() {
        Cursor cursor =sqLiteDatabase.rawQuery(
                "SELECT MAX(" + _ID + ") FROM " + TABLE_NAME, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getLong(0);
        }

        return -1;
    }

    public ArrayList<Playlist> getAllPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                "playlist",
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long _id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));

                // TODO: get tags from sqlitedatabase
                ArrayList<Tag> tags = new ArrayList<Tag>();

                Playlist playlist = new Playlist(
                        _id,
                        cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("url")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("image")),
                        tags
                );

                playlists.add(playlist);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return playlists;
    }

    public Playlist getByName(String name) {
        Cursor cursor = sqLiteDatabase.query(
                TABLE_NAME,
                new String[]{_ID, LATITUDE, LONGITUDE, URL, IMAGE}, /* Columns to retrieve */
                NAME + " = ? ", new String[]{name},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(URL));
            int image = cursor.getInt(cursor.getColumnIndexOrThrow(IMAGE));

            // TODO: get tags for sqlitedatabase
            ArrayList<Tag> tags = new ArrayList<Tag>();

            Playlist playlist = new Playlist(_id, latitude, longitude, url, name, image, tags);
            cursor.close();
            return playlist;
        }

        return null;
    }

    public void insert(long _id, String name, String url,  Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, _id);
        contentValues.put(NAME, name);
        contentValues.put(URL, url);
        contentValues.put(IMAGE, image);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public int update(long _id, String name, String url,  Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(URL, url);
        contentValues.put(IMAGE, image);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);
        int i = sqLiteDatabase.update(TABLE_NAME, contentValues, _ID + " = " + _id, null);
        return i;
    }

    public void delete(String name) {
        sqLiteDatabase.delete(TABLE_NAME, NAME + " = ?", new String[]{name});
    }
}
