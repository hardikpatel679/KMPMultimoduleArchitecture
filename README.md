<div align="center">
  <table>
    <tr>
      <th align="center">🤖 Android (Product List)</th>
      <th align="center">🍎 iOS (Product List)</th>
    </tr>
    <tr>
      <td align="center">
      <img width="1344" height="2992" src="https://github.com/user-attachments/assets/9c2bdebb-2727-4c17-92e3-79bef6942952" width="300"/>
      </td>
      <td align="center">
        <img width="1206" height="2622" alt="Simulator Screenshot - iPhone 17 - 2026-05-22 at 13 15 36" src="https://github.com/user-attachments/assets/fa26093a-92ee-4ec6-b394-f3bcf6db6ef8" width="300 />
      </td>
    </tr>
  </table>
</div>


# KMP - Multimodule + Clean Architecture with MVI

A professional-grade **Kotlin Multiplatform (KMP)** mobile application built with **Clean Architecture** and the **MVI (Model-View-Intent)** pattern. This project showcases modern cross-platform development, demonstrating a highly modularized structure, platform-independent business logic, and advanced UI patterns.

## 🚀 Key Highlights

- **Kotlin Multiplatform (KMP):** Shared business logic, networking, and data layers across Android and iOS.
- **Compose Multiplatform:** 100% shared UI using Jetpack Compose, ensuring a consistent experience across platforms.
- **MVI Pattern:** Robust state management using `State`, `Intent`, and `Effect` for predictable UI behavior and side-effect handling.
- **Clean Architecture:** Strict separation of concerns (Domain, Data, Feature, UI) to ensure maintainability and testability.
- **Multi-Module Architecture:** Fine-grained module strategy to optimize build times and enforce strict architectural boundaries.
- **Localization & Theme Support:** Full support for RTL (Arabic) and LTR (English) languages, with theme-aware assets.
- **Image Loading:** Integrated **Coil 3** for high-performance, cross-platform image loading and caching.

---

## 🏗 Architecture & Modularization

The project follows a "Feature-First" modularization strategy:

### 🧩 Module Breakdown

| Module | Level | Description |
| :--- | :--- | :--- |
| `:androidApp` | Platform | The Android entry point, handling application startup and platform-specific configurations. |
| `:shared` | Orchestration | Coordinates features and shared UI. Contains the Koin DI initialization for cross-platform dependency access. |
| `:feature:login` | Feature | Self-contained module for Login functionality, including MVI ViewModels and UI. |
| `:feature:dashboard` | Feature | Dashboard feature module with product listings, category filtering, and localization. |
| `:domain` | Core | The heart of the app: contains Business Models, Repository Interfaces, and Use Cases. Pure Kotlin. |
| `:data` | Infrastructure | Implementation of repositories, Ktor networking (DummyJSON API), and data mapping logic. |
| `:core` | Utility | Shared UI components, Design System (`Dimens`), Test Tags, networking helpers, and Coil configuration. |

---

## 🛠 Tech Stack

- **UI:** Compose Multiplatform (Material 3)
- **Networking:** Ktor Client (Content Negotiation, Logging, Serialization)
- **Serialization:** Kotlinx.Serialization
- **DI:** **Koin** (Unified Dependency Injection for Android & iOS)
- **Image Loading:** **Coil 3**
- **Concurrency:** Kotlin Coroutines & Flows
- **Resource Management:** Compose Resources (Adaptive Drawables, Localized Strings)

---

## 🎨 MVI & UI Implementation

This project implements a clean MVI pattern to manage UI state:
- **State:** A single source of truth for the UI (e.g., `DashboardState`).
- **Intent:** User actions or system events (e.g., `DashboardIntent.SelectCategory`).
- **Effect:** One-time side effects like navigation (e.g., `DashboardEffect.NavigateToLogin`).

**Product Tab Implementation:**
- **Horizontal Category List:** Dynamically generated from API data.
- **Vertical Product List:** Filtered based on category selection.
- **Lazy Loading:** Efficient rendering of large lists using `LazyColumn` and `LazyRow`.

---

## 💉 Dependency Injection Strategy

The project uses **Koin** for a unified, pure-Kotlin dependency injection experience:
- **Common Logic:** Modules for Network, Repositories, and UseCases are defined in the `:shared` module.
- **ViewModels:** Koin manages the lifecycle of shared ViewModels using the `viewModelOf` DSL.
- **Platform Initialization:** A simple `initKoin()` call in `MyApplication.kt` (Android) and `iOSApp.swift` (iOS) sets up the entire graph.

---

## 🧪 Testing Excellence

The project maintains a high standard of quality through comprehensive testing:
- **Unit Testing:** Business logic in `:domain` and `:feature` ViewModels is verified using `kotlin.test`.
- **Flow/State Testing:** Uses **Turbine** for assertive testing of `StateFlow` and `SharedFlow` emissions.
- **UI Testing:** Compose UI tests verify user interactions using a unified `TestTags` system.

---

## 🛠 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- Xcode 15+ (for iOS development)
- Kotlin Multiplatform plugin

### Running the App
- **Android:** Select `androidApp` and click **Run**.
- **iOS:** Run via Android Studio's `iosApp` configuration or open the `iosApp` folder in Xcode.

---

## 👨‍💻 Skills Showcased
- **Advanced KMP:** Shared UI and Logic across Android & iOS.
- **MVI Architecture:** Scalable state management.
- **Clean Architecture:** Enterprise-grade code organization.
- **Backend Integration:** Consuming REST APIs with Ktor.
- **Modern DI:** Leveraging Koin for multiplatform dependency management.
