# Planet Fitness Android App

A modern Android application for managing fitness programs and workouts, built with Jetpack Compose and following modern Android development best practices.

## 🚀 Features

- **Home Dashboard:** Browse featured fitness programs with category filtering (Abs & Core, Full Body, Upper Body).
- **Program Overview:** Detailed view of fitness programs, including trainer info, focus areas, and a list of workouts.
- **Workout Details:** Deep dive into specific workouts with descriptions, duration, and difficulty levels.
- **Workout Timer:** Integrated timer to help users track their exercise duration.
- **Offline Support:** Powered by Room database to ensure programs are accessible even without a connection.

## 🛠 Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material Design 3.
- **Architecture:** MVVM (Model-View-ViewModel) with a Repository pattern.
- **Asynchronous Flow:** Kotlin Coroutines and StateFlow for reactive UI updates.
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room) for persistent storage.
- **Networking:** [Retrofit](https://square.github.io/retrofit/) with Gson for API integration.
- **Navigation:** [Compose Navigation](https://developer.android.com/jetpack/compose/navigation).
- **Dependency Management:** Gradle Version Catalog (`libs.versions.toml`).

## 📁 Project Structure

The project is divided into two primary modules:

- **`:app`**: Contains the UI layer (Compose screens, ViewModels, Theme) and navigation logic.
- **`:data`**: Handles the data layer, including network clients, database entities, DAOs, and repositories.

## ⚙️ Setup & Installation

### Prerequisites

- [Android Studio Ladybug](https://developer.android.com/studio) or newer.
- JDK 17 or higher.

### Building the Project

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle and build the project:
   ```bash
   ./gradlew assembleDebug
   ```

## 🧪 Testing

The project includes unit and instrumentation tests:
- Run unit tests: `./gradlew test`
- Run Android instrumented tests: `./gradlew connectedAndroidTest`

## 📝 License

This project is for demonstration purposes.
