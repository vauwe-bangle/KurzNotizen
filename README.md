# WishList App 📝✨

WishList App is a simple Android application built using Jetpack Compose and Room database. It provides an intuitive and modern way to manage your personal wish list items with a clean, material design interface.

Inspired by the Udemy course on [**The Complete Android 14 & Kotlin Development Masterclass**](https://www.udemy.com/course/android-kotlin-developer/) Created by [Denis Panjuta](https://www.udemy.com/user/denispanjuta/).

# Output Screenshot-
<p align="center">
<img src="https://github.com/user-attachments/assets/5d1aa452-8d5a-49ae-8e1f-63817712c7f7" width="288">
<img src="https://github.com/user-attachments/assets/b19a97b0-06b8-4dee-95c3-578e247fa300" width="288">
<img src="https://github.com/user-attachments/assets/55b47b14-739c-4ae5-8683-cb676c9ed123" width="288">
<img src="https://github.com/user-attachments/assets/7fec7ba5-260c-487d-a2e2-e95a95150674" width="288">
<img src="https://github.com/user-attachments/assets/9c408e47-0cad-4c50-8489-1299c403a300" width="288">
</p>

# Output Video-
https://github.com/user-attachments/assets/d70659f6-eeb9-4067-b1df-3d5e3b61425f


## 🌟 Features

- **Create Wishes**: Add new wishes with a title and description
- **View Wishes**: Browse all your wish list items in a clean list view
- **Edit Wishes**: Modify existing wish details with ease
- **Delete Wishes**: Remove items using an intuitive swipe-to-dismiss gesture
- **Error Handling**: Robust error management with user-friendly notifications
- **Modern UI**: Clean and contemporary Material Design 3 interface

## 🛠 Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room
- **Architecture**: MVVM (Model-View-ViewModel)
- **Asynchronous Programming**: Kotlin Coroutines
- **Dependency Management**: Manual dependency graph

## 🏗 Key Components

### Data Layer
- **`Wish`**: Data class representing a wish list item
- **`WishDao`**: Database access object for CRUD operations
- **`WishRepository`**: Manages data operations and provides a clean API for the ViewModel

### UI Layer
- **`WishListViewModel`**: Handles UI state, business logic, and interaction with the repository
- **`AddEditDetailView`**: Composable screen for adding and editing wishes
- **`AppBarView`**: Custom app bar for navigation
- **Validation**: Built-in input validation (e.g., title length between 1-50 characters)

### Navigation
- Utilizes Android Navigation Component for seamless screen transitions

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Kotlin plugin
- Android SDK 24 (Android 7.0) or higher

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/CGreenP/WishList-App.git
   ```

2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## 📱 How to Use

1. Launch the WishList App
2. Tap the "+" button to create a new wish
3. Enter a title (1-50 characters) and description
4. Save the wish
5. Swipe left on a wish to delete it
6. Tap on a wish to edit its details

## 🧩 Dependencies

- Jetpack Compose
- Room Database
- Kotlin Coroutines
- Android Navigation Component
- Material Design 3

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch 
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit your changes 
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push to the branch 
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Open a Pull Request

## 🎉 Conclusion

WishList App demonstrates modern Android development practices, combining the power of Jetpack Compose, Room Database, and MVVM architecture to create a simple yet effective wish tracking application.

Happy wishing! 🚀✨
