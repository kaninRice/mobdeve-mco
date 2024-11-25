package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
                PlaylistDbHelper._ID,
                PlaylistDbHelper.NAME,
                PlaylistDbHelper.URL,
                PlaylistDbHelper .IMAGE,
                PlaylistDbHelper.LATITUDE,
                PlaylistDbHelper.LONGITUDE,
        };

        Cursor cursor = sqLiteDatabase.query(
                PlaylistDbHelper.TABLE_NAME,
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

    public ArrayList<Playlist> getAllPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("playlist", null, null, null, null, null, null);

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

    public void insert(String name, String url,  Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlaylistDbHelper.NAME, name);
        contentValues.put(PlaylistDbHelper.URL, url);
        contentValues.put(PlaylistDbHelper.IMAGE, image);
        contentValues.put(PlaylistDbHelper.LATITUDE, latitude);
        contentValues.put(PlaylistDbHelper.LONGITUDE, longitude);
        sqLiteDatabase.insert(PlaylistDbHelper.TABLE_NAME, null, contentValues);
    }

    public int update(long _id, String name, String url,  Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlaylistDbHelper.NAME, name);
        contentValues.put(PlaylistDbHelper.URL, url);
        contentValues.put(PlaylistDbHelper.IMAGE, image);
        contentValues.put(PlaylistDbHelper.LATITUDE, latitude);
        contentValues.put(PlaylistDbHelper.LONGITUDE, longitude);
        int i = sqLiteDatabase.update(PlaylistDbHelper.TABLE_NAME, contentValues, PlaylistDbHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        sqLiteDatabase.delete(PlaylistDbHelper.TABLE_NAME, PlaylistDbHelper._ID + " = " + _id, null);
    }

    public void deleteTable(long _id) {
        sqLiteDatabase.delete(PlaylistDbHelper.TABLE_NAME, PlaylistDbHelper._ID + "=" + _id, null);
    }
}
