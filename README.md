# ArpegGeo

This application manages Spotify playback controls based on current user location. This is developed as a MOBDEVE Machine Project.

## Setup

A `gradle.properties` file is needed in root to run the project. The app is developed having the following values in the file:
```
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.nonTransitiveRClass=true

SPOTIFY_CLIENT_ID=<PROVIDE SPOTIFY CLIENT ID>
SPOTIFY_REDIRECT_URI=<PROVIDE REDIRECT URI>
AUTH_SCOPES = "user-read-private user-read-email streaming";
```

## Features
## Screenshots