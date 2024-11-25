package com.example.s17.escopete.stevenerrol.arpeggeo.spotify;

import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_CLIENT_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_REDIRECT_URI;

import android.content.Context;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages Spotify connection and playback controls
 */
@Singleton
public class SpotifyManager {
    private SpotifyAppRemote spotifyAppRemote;
    private String accessToken;

    @Inject
    public SpotifyManager() {}

    /**
     * Sets the access token
     * @param token The access token to be set
     */
    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    /**
     * Connects to Spotify App Remote
     * @param context The context used to connect
     */
    public void connect(Context context) {
        ConnectionParams connectionParams = new ConnectionParams.Builder(SPOTIFY_CLIENT_ID)
                .setRedirectUri(SPOTIFY_REDIRECT_URI)
                .showAuthView(true) // Ensures the user is prompted if access is not authorized
                .build();

        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote remote) {
                spotifyAppRemote = remote;
                Log.d("spot", "connected");
            }

            @Override
            public void onFailure(Throwable error) {
            }
        });
    }

    /**
     * Plays a playlist
     * @param shareLink The link of the playlist to be played
     */
    public void play(String shareLink) {
        /* Convert "share link" to uri */

        /* Link is invalid */
        if (shareLink == null || !shareLink.contains("open.spotify.com")) {
            return;
        }

        /* Remove query parameters */
        String cleanedLink = shareLink.split("\\?")[0];

        /*Extract the entity type and ID */
        String[] parts = cleanedLink.split("/");
        if (parts.length < 5) {
            return; // Not enough parts in the URL
        }

        String entityType = parts[3]; // e.g., track, album, playlist, artist
        String id = parts[4]; // Unique Spotify ID

        // Construct the Spotify URI
        String uri = "spotify:" + entityType + ":" + id;
        Log.d("uri", uri);

        if (spotifyAppRemote != null && spotifyAppRemote.isConnected()) {
            Log.d("play?", "yes");
            spotifyAppRemote.getPlayerApi().play(uri);
        }
    }
}
