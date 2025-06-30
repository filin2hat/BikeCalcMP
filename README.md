# BikeCalc Pro

**BikeCalc Pro** is a multiplatform application for cyclists, developed with Kotlin Multiplatform and Compose Multiplatform. It is designed for Android, iOS, Desktop, and Web.

## Key Features

1.  **Tire Pressure Calculator:**
    *   Allows users to calculate the optimal tire pressure for front and rear wheels.
    *   Takes into account parameters such as rider weight, bike weight, wheel size, and tire type (tubes or tubeless).
    *   Results can be displayed in various units of measurement (bar, psi, kPa).
    *   The application saves a history of previous calculations, which can be cleared.

2.  **Development Calculator:**
    *   This feature helps calculate the gear ratio or the distance the bike travels in one turn of the pedals.

## Tech Stack

*   **Programming Language:** [Kotlin](https://kotlinlang.org/)
*   **Framework:** [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)
*   **UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Dependency Injection:** [Koin](https://insert-koin.io/)
*   **Database:** [Room](https://developer.android.com/training/data-storage/room)
*   **Navigation:** [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
*   **Networking:** [Ktor](https://ktor.io/)
*   **Image Loading:** [Coil](https://coil-kt.github.io/coil/)
*   **Analytics:** [Kotzilla](https://kotzilla.io/)
*   **Charts:** [Vico](https://github.com/patrykandpatrick/vico)
*   **Coroutines:** [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
*   **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

## Screenshots

| Light Theme | Dark Theme |
| :---: | :---: |
| <img src="screenshots/Screenshot_20250602_193008.png" width="250"> | <img src="screenshots/Screenshot_20250602_193027.png" width="250"> |
| <img src="screenshots/Screenshot_20250602_193059.png" width="250"> | <img src="screenshots/Screenshot_20250602_193109.png" width="250"> |

## How to run

You can open the desktop application by running the  `:composeApp:run` Gradle task.
