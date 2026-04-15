# Family Finance (MVP)

Offline-first Android app for family expense tracking built with Kotlin, Jetpack Compose, MVVM, Room, and Navigation Compose.

## Tech stack
- Kotlin + Coroutines + StateFlow
- Jetpack Compose + Material 3
- Navigation Compose
- Room (local database)
- MVVM

## Run locally
1. Open the project in Android Studio (latest stable).
2. Let Gradle sync complete.
3. Run `app` on an emulator or device.

## CLI checks
```bash
./gradlew --stop
./gradlew --refresh-dependencies
./gradlew :app:assembleDebug
```

## Troubleshooting Gradle compatibility
If you hit errors like:
- `Unable to find method ... Configuration.fileCollection(...)`

Use this recovery order:
1. Stop daemons:
   ```bash
   ./gradlew --stop
   ```
2. Clear and re-download dependencies:
   ```bash
   ./gradlew --refresh-dependencies
   ```
3. In Android Studio: **File > Invalidate Caches / Restart**.
4. Ensure Android Studio uses the Gradle wrapper and JDK 17.
5. Re-sync project.

This project is pinned to modern Android Gradle Plugin/Kotlin versions in root `build.gradle.kts` for compatibility with recent Android Studio versions.
