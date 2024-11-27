package com.example.s17.escopete.stevenerrol.arpeggeo.core.data;

import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_IMAGE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_LATITUDE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_LONGITUDE;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_TABLE_NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.PLAYLIST_URL;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_COLOR;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_PLAYLIST_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_TABLE_NAME;
import static com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper.TAG_TEXT_COLOR;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

/**
 * Manages the database
 */
public class DbManager {
    private SQLiteDatabase sqLiteDatabase;
    private DbHelper DbHelper;
    private final Context context;

    /**
     * Constructor for the DbManager
     * @param context The context of the DbManager
     */
    public DbManager(Context context) {
        this.context = context;
    }

    /**
     * Opens a writable database
     * @return This DbManager
     * @throws SQLException An SQL exception
     */
    public DbManager open() throws SQLException {
        DbHelper = new DbHelper(context);
        sqLiteDatabase = DbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the database connection
     */
    public void close() {
        DbHelper.close();
    }

    /**
     * Gets the highest-numbered id in the playlist table
     * @return The highest-numbered id, {@code -1} if there are no playlists
     */
    public long getHighestPlaylistId() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT MAX(" + PLAYLIST_ID + ") FROM " + PLAYLIST_TABLE_NAME, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(0);
            cursor.close();
            return _id;
        }

        return -1;
    }

    /**
     * Gets the highest-numbered id in the tag table
     * @return The highest-numbered id, {@code -1} if there are no tags
     */
    public long getHighestTagId() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT MAX(" + TAG_ID + ") FROM " + TAG_TABLE_NAME, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(0);
            cursor.close();
            return _id;
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
                PLAYLIST_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long _id = cursor.getLong(cursor.getColumnIndexOrThrow(PLAYLIST_ID));

                /* Get tags */
                ArrayList<Tag> tags = getAllTagsWithPlaylistId(_id);

                Playlist playlist = new Playlist(
                        _id,
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PLAYLIST_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PLAYLIST_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PLAYLIST_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PLAYLIST_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PLAYLIST_IMAGE)),
                        tags
                );

                playlists.add(playlist);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return playlists;
    }

    /**
     * Gets all the tags in the database
     * @return An {@link ArrayList} of {@link Tag}s
     */
    public ArrayList<Tag> getAllTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                TAG_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tag tag = new Tag(
                        cursor.getLong(cursor.getColumnIndexOrThrow(TAG_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_COLOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_TEXT_COLOR)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(TAG_PLAYLIST_ID))
                );

                tags.add(tag);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return tags;
    }

    /**
     * Gets all tags with the given playlist ID
     * @param _id The playlist ID
     * @return An {@link ArrayList} of {@link Tag}s
     */
    public ArrayList<Tag> getAllTagsWithPlaylistId(long _id) {
        ArrayList<Tag> tags = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                TAG_TABLE_NAME,
                null,
                TAG_PLAYLIST_ID + " = ?",
                new String[]{Long.toString(_id)},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tag tag = new Tag(
                        _id,
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_COLOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TAG_TEXT_COLOR)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(TAG_PLAYLIST_ID))
                );

                tags.add(tag);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return tags;
    }

    /**
     *  Gets a playlist
     * @param name The name of the playlist to be retrieved
     * @return A {@link Playlist}
     */
    public Playlist getPlaylistByName(String name) {
        Cursor cursor = sqLiteDatabase.query(
                PLAYLIST_TABLE_NAME,
                new String[]{PLAYLIST_ID, PLAYLIST_LATITUDE, PLAYLIST_LONGITUDE, PLAYLIST_URL, PLAYLIST_IMAGE}, /* Columns to retrieve */
                PLAYLIST_NAME + " = ? ", new String[]{name},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndexOrThrow(PLAYLIST_ID));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(PLAYLIST_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(PLAYLIST_LONGITUDE));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(PLAYLIST_URL));
            int image = cursor.getInt(cursor.getColumnIndexOrThrow(PLAYLIST_IMAGE));

            /* Get tags */
            ArrayList<Tag> tags = getAllTagsWithPlaylistId(_id);

            Playlist playlist = new Playlist(_id, latitude, longitude, url, name, image, tags);
            cursor.close();
            return playlist;
        }

        return null;
    }

    /**
     *  Gets a tag
     * @param name The name of the tag
     * @return A {@link Tag}
     */
    public Tag getTagByName(String name) {
        Cursor cursor = sqLiteDatabase.query(
                TAG_TABLE_NAME,
                new String[]{TAG_ID, TAG_NAME, TAG_COLOR, TAG_TEXT_COLOR, TAG_PLAYLIST_ID}, /* Columns to retrieve */
                TAG_NAME + " = ? ", new String[]{name},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Tag tag = new Tag(
                    cursor.getLong(cursor.getColumnIndexOrThrow(TAG_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TAG_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TAG_COLOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TAG_TEXT_COLOR)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(PLAYLIST_ID))
            );

            cursor.close();
            return tag;
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
    public void insertPlaylist(long _id, String name, String url, Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYLIST_ID, _id);
        contentValues.put(PLAYLIST_NAME, name);
        contentValues.put(PLAYLIST_URL, url);
        contentValues.put(PLAYLIST_IMAGE, image);
        contentValues.put(PLAYLIST_LATITUDE, latitude);
        contentValues.put(PLAYLIST_LONGITUDE, longitude);
        sqLiteDatabase.insert(PLAYLIST_TABLE_NAME, null, contentValues);
    }

    /**
     * Inserts a tag in the database
     * @param _id The id of the tag
     * @param name The name of the tag
     * @param color The color of the tag
     * @param textColor The text color of the tag
     * @param _playlistId The playlist ID where the tag is located
     */
    public void insertTag(long _id, String name, String color,  String textColor, long _playlistId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_ID, _id);
        contentValues.put(TAG_NAME, name);
        contentValues.put(TAG_COLOR, color);
        contentValues.put(TAG_TEXT_COLOR, textColor);
        contentValues.put(TAG_PLAYLIST_ID, _playlistId);
        sqLiteDatabase.insert(TAG_TABLE_NAME, null, contentValues);
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
    public int updatePlaylist(long _id, String name, String url, Integer image, double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYLIST_NAME, name);
        contentValues.put(PLAYLIST_URL, url);
        contentValues.put(PLAYLIST_IMAGE, image);
        contentValues.put(PLAYLIST_LATITUDE, latitude);
        contentValues.put(PLAYLIST_LONGITUDE, longitude);
        return sqLiteDatabase.update(PLAYLIST_TABLE_NAME, contentValues, PLAYLIST_ID + " = " + _id, null);
    }

    /**
     * Deletes playlists in the database
     * @param names The names of the playlists to be deleted
     */
    public void deletePlaylists(ArrayList<String> names) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            values.append("?");

            if (i < names.size() - 1) {
                values.append(",");
            }
        }

        String[] args = names.toArray(new String[0]);

        sqLiteDatabase.delete(PLAYLIST_TABLE_NAME, PLAYLIST_NAME + " IN (" + values + ")", args);
    }

    /**
     * Deletes tags in the database
     * @param names The names of the tags to be deleted
     */
    public void deleteTags(ArrayList<String> names) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            values.append("?");

            if (i < names.size() - 1) {
                values.append(",");
            }
        }

        String[] args = names.toArray(new String[0]);

        sqLiteDatabase.delete(TAG_TABLE_NAME, TAG_NAME + " IN (" + values + ")", args);
    }

    /**
     * Deletes tags with a specific playlist id in the database
     * @param _playlistId The playlist id of the tags to be deleted
     */
    public void deleteTagsWithPlaylistId(long _playlistId) {
        sqLiteDatabase.delete(TAG_TABLE_NAME, TAG_PLAYLIST_ID + " = ?", new String[]{String.valueOf(_playlistId)});
    }
}
