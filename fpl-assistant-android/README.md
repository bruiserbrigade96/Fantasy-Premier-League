# FPL Assistant (Android)

An English Premier League Fantasy Football assistant for Android, built with Kotlin, Jetpack Compose, and Retrofit against the official Fantasy Premier League (FPL) API.

## Features
- Dashboard with quick insights (top scorers, form)
- Players browser with filters (position, team) and search
- Modern UI using Material 3 and Jetpack Compose

## Requirements
- JDK 17
- Android Studio (latest) with Android SDK 35

## Getting Started
1. Open the project in Android Studio: `File > Open` and select the `fpl-assistant-android` folder.
2. Ensure the SDK path is configured in `local.properties` (Android Studio will prompt if missing):
   ```
   sdk.dir=/path/to/Android/Sdk
   ```
3. Generate the Gradle wrapper in case the IDE prompts for it:
   ```bash
   gradle wrapper --gradle-version 8.9
   ```
4. Build and run on an emulator or device.

## FPL API
This app uses the public endpoints:
- `https://fantasy.premierleague.com/api/bootstrap-static/` (players, teams, positions)
- `https://fantasy.premierleague.com/api/fixtures/`
- `https://fantasy.premierleague.com/api/element-summary/{id}/`

API is read-only and does not require authentication for these endpoints.

## CI
Add a GitHub Actions workflow using `actions/setup-java` and `android-actions/setup-android` to build with the Android SDK.