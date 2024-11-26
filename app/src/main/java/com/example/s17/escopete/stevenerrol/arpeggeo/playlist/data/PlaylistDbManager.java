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

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

/**
 * Manages the database
 */
public class PlaylistDbManager {
    private SQLiteDatabase sqLiteDatabase;
    private PlaylistDbHelper playlistDbHelper;
    private final Context context;

    /**
     * Constructor for the PlaylistDbManager
     * @param context The context of the PlaylistDbManager
     */
    public PlaylistDbManager(Context context) {
        this.context = context;
    }

    /**
     * Opens a writable database
     * @return This playlistDbManager
     * @throws SQLException An SQL exception
     */
    public PlaylistDbManager open() throws SQLException {
        playlistDbHelper = new PlaylistDbHelper(context);
        sqLiteDatabase = playlistDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the database connection
     */
    public void close() {
        playlistDbHelper.close();
    }

    /**
     * Gets the highest-numbered id in the database
     * @return The highest-numbered id
     */
    public long getHighestId() {
        Cursor cursor =sqLiteDatabase.rawQuery(
                "SELECT MAX(" + _ID + ") FROM " + TABLE_NAME, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getLong(0);
        }

        return -1;
    }

    /**
     * Gets all the playlist in the database
     * @return An {@link ArrayList} of {@link Playlist}s
     */
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
                        cursor.getString(cursor.getColumnIndexOrThrow("url")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("image")),
                        tags
                );

                playlists.add(playlist);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return playlists;
    }

    /**
     *  Gets a playlist
     * @param name The name of the playlist
     * @return A {@link Playlist}
     */
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

    /**
     * Inserts a playlist in the database
     * @param _id The id of the playlist
     * @param name The name of the playlist
     * @param url The url of the playlist
     * @param image The image of the playlist
     * @param latitude The latitude of the playlist
     * @param longitude The longitude of the playlist
     */
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

    /**
     * Updates a playlist in the database
     * @param _id The new id of the playlist
     * @param name The new name of the playlist
     * @param url The new url of the playlist
     * @param image The new image of the playlist
     * @param latitude The new latitude of the playlist
     * @param longitude The new longitude of the playlist
     * @return The number of rows affected
     */
    public int update(long _id, String name, String url, Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(URL, url);
        contentValues.put(IMAGE, image);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);
        int i = sqLiteDatabase.update(TABLE_NAME, contentValues, _ID + " = " + _id, null);
        return i;
    }

    /**
     * Deletes playlists in the database
     * @param names The names of the playlists to be deleted
     */
    public void delete(ArrayList<String> names) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            values.append("?");

            if (i < names.size() - 1) {
                values.append(",");
            }
        }

        String[] args = names.toArray(new String[0]);

        sqLiteDatabase.delete(TABLE_NAME, NAME + " IN (" + values + ")", args);
    }
}
