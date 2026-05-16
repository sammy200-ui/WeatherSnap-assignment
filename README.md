# WeatherSnap

WeatherSnap is a minimal Android application that allows users to search for live weather, create custom weather reports with camera evidence, compress images, and store reports offline.

## Setup & Run Steps
1. Clone this repository to your local machine.
2. Open the project in Android Studio (Giraffe or newer recommended).
3. Let Gradle sync the dependencies. 
4. Connect an Android device or start an emulator (API 24+).
5. Click **Run** (`Shift + F10`) to build and launch the app.

*(Note: No API keys are required for this app since it uses the free Open-Meteo API)*

## Tech Stack
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM, Hilt (Dependency Injection)
- **Async & State:** Coroutines, StateFlow
- **Navigation:** Navigation Compose
- **Network:** Retrofit, OkHttp, Gson
- **Database:** Room
- **Camera:** CameraX

## Developer Judgment Challenge

**Problem:** How to protect the in-progress report flow (selected weather, captured photo, and entered notes) from being lost on device rotation or app backgrounding/death, while ensuring the final report saves the *exact* weather snapshot that was selected initially. Temporary images also must not leak indefinitely.

**My Approach & Tradeoffs:**
To achieve state restoration without over-engineering, I passed the selected weather data (city name, temperature, condition, etc.) through Compose Navigation arguments directly to the `CreateReportScreen`. The `CreateReportViewModel` then injects these arguments into its `SavedStateHandle`. 

I also saved the user-entered notes and the path of the captured photo directly into the `SavedStateHandle`.
- **Tradeoff:** Passing complex objects through navigation isn't natively supported, so I broke the weather snapshot down into primitive arguments (`Float`, `String`, `Int`). For a very large object, saving it to a temporary Room table might be better, but for this small snapshot, navigation arguments mapped into `SavedStateHandle` provide an elegant, native way to survive process death without hitting the database prematurely.

For **image cleanup**, CameraX saves a full-sized temporary file to `context.cacheDir`. When the user clicks "Save Report", the ViewModel compresses that photo into a new file in `context.filesDir` (to persist permanently) and immediately deletes the temporary cache file.
To handle cases where the user simply presses "Back" and discards the report without saving, I placed a small cleanup routine in `MainActivity`'s `onCreate` that deletes any orphaned temporary image files from the cache directory.
- **Tradeoff:** This defers the cleanup of discarded images until the next app launch. A more immediate approach would be hooking into the Navigation backstack or `onCleared` of the ViewModel, but placing it on startup guarantees no lingering leaks ever accumulate over time, keeping the logic very simple and bug-free.
