# KMP - Multimodule + Clean Architecture with MVI

A professional-grade **Kotlin Multiplatform (KMP)** mobile application built with **Clean Architecture** and the **MVI (Model-View-Intent)** pattern. This project showcases modern cross-platform development, demonstrating a highly modularized structure, platform-independent business logic, and advanced UI patterns.

## 🚀 Key Highlights

- **Kotlin Multiplatform (KMP):** Shared business logic, networking, and data layers across Android and iOS.
- **Compose Multiplatform:** 100% shared UI using Jetpack Compose, ensuring a consistent experience across platforms.
- **MVI Pattern:** Robust state management using `State`, `Intent`, and `Effect` for predictable UI behavior and side-effect handling.
- **Clean Architecture:** Strict separation of concerns (Domain, Data, Feature, UI) to ensure maintainability and testability.
- **Multi-Module Architecture:** Fine-grained module strategy to optimize build times and enforce strict architectural boundaries.
- **Localization & Theme Support:** Full support for RTL (Arabic) and LTR (English) languages, with theme-aware assets (Adaptive Logo).

---

## 🏗 Architecture & Modularization

The project follows a "Feature-First" modularization strategy:

### 🧩 Module Breakdown

| Module | Level | Description |
| :--- | :--- | :--- |
| `:androidApp` | Platform | The Android entry point, handling Hilt setup and platform-specific Activity configurations. |
| `:shared` | Orchestration | Coordinates features and shared UI. Contains the `KmpDI` bridge for cross-platform dependency access. |
| `:feature:login` | Feature | Self-contained module for Login functionality, including MVI ViewModels and UI. |
| `:feature:dashboard` | Feature | Dashboard feature module with localization toggles and session management. |
| `:domain` | Core | The heart of the app: contains Business Models, Repository Interfaces, and Use Cases. Pure Kotlin. |
| `:data` | Infrastructure | Implementation of repositories, Ktor networking, and data mapping logic. |
| `:core` | Utility | Shared UI components, Design System (`Dimens`), Test Tags, and networking helpers. |

---

## 🛠 Tech Stack

- **UI:** Compose Multiplatform (Material 3)
- **Networking:** Ktor Client (Content Negotiation, Logging, Serialization)
- **Serialization:** Kotlinx.Serialization
- **DI:** Dagger Hilt (Android) & Manual DI Bridge (KMP/iOS)
- **Concurrency:** Kotlin Coroutines & Flows
- **Resource Management:** MOKO Resources / Compose Resources (Adaptive Drawables)

---

## 🎨 MVI & UI Implementation

This project implements a clean MVI pattern to manage UI state:
- **State:** A single source of truth for the UI (e.g., `LoginState`).
- **Intent:** User actions or system events (e.g., `LoginIntent.Login`).
- **Effect:** One-time side effects like navigation or showing snackbars (e.g., `LoginEffect.NavigateToHome`).

**Localization:** Dynamic switching between English (LTR) and Arabic (RTL) is handled at the core level, affecting both layout and resources.

---

## 💉 Dependency Injection Strategy

A sophisticated hybrid DI approach is used:
- **Android:** Uses **Hilt** for compile-time safety and automatic lifecycle management in the Android app.
- **Common/iOS:** Implements a **Manual DI Container (`KmpDI`)** to expose dependencies to iOS (Swift) without overhead, ensuring that shared logic is easily consumable.

---

## 🧪 Testing Excellence

The project maintains a high standard of quality through comprehensive testing:
- **Unit Testing:** Business logic in `:domain` and `:feature` ViewModels is verified using `kotlin.test`.
- **Flow/State Testing:** Uses **Turbine** for assertive testing of `StateFlow` and `SharedFlow` emissions.
- **UI Testing:** Compose UI tests (on Android host and devices) verify user interactions using a unified `TestTags` system.

---

## 🛠 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- Xcode 15+ (for iOS development)
- Kotlin Multiplatform plugin

### Running the App
- **Android:** Select `androidApp` and click **Run**.
- **iOS:** Run via Android Studio's `iosApp` configuration or open the `iosApp` folder in Xcode.

### Running Tests
```bash
# Run all unit tests
./gradlew test

# Run UI tests (Android)
./gradlew connectedAndroidTest
```

---

## 👨‍💻 Skills Showcased
- **Advanced KMP:** Shared UI and Logic across Android & iOS.
- **MVI Architecture:** Scalable state management.
- **Clean Architecture:** Enterprise-grade code organization.
- **Resource Handling:** Adaptive icons, multi-language support (RTL).
- **Quality Assurance:** Unit and UI testing with modern tools.
