package com.example.s17.escopete.stevenerrol.arpeggeo.spotify;

import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_CLIENT_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_REDIRECT_URI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * The activity for Spotify authorization
 */
@AndroidEntryPoint
public class SpotifyLoginActivity extends AppCompatActivity {
    private static final int SPOTIFY_REQUEST_CODE = 1;

    @Inject
    SpotifyManager spotifyManager;

    /**
     * Initializes the activity
     * @param savedInstanceState Previous saved state to reconstruct if not null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticateSpotify();
        connectSpotify();
    }

    /**
     * Initiates Spotify user authentication
     */
    private void authenticateSpotify() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(
                        SPOTIFY_CLIENT_ID,
                        AuthorizationResponse.Type.TOKEN,
                        SPOTIFY_REDIRECT_URI
                );

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, SPOTIFY_REQUEST_CODE, request);
    }

    /**
     * Connects to local Spotify app
     */
    private void connectSpotify() {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(SPOTIFY_CLIENT_ID)
                        .setRedirectUri(SPOTIFY_REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    /**
     * Handles user authentication result
     * @param requestCode The code passed when the activity is started
     * @param resultCode The code to determine the result
     * @param intent The intent containing the result data from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        /* Check if result comes from the correct activity */
        if (requestCode == SPOTIFY_REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                /* Successful */
                case TOKEN:
                    String token = response.getAccessToken();
                    spotifyManager.setAccessToken(token);
                    finish();
                    break;

                /* Unsuccessful */
                case ERROR:
                    // Handle error response
                    break;

                default:
                    break;
            }
        }
    }
}
