# Text Size App with Database

This application allows users to input text, select a font size, display the text with the chosen size, and store the entries in a local SQLite database.

## Author

Vladyslav Riabyi  
Group: IM-24  
Variant: 10

## Description

The Text Size App is an Android application built using Jetpack Compose and SQLite database. It provides a user interface with the following features:

- A text input field for users to enter their desired text
- Radio buttons to select from predefined font sizes (16sp, 24sp, 32sp)
- "OK" button to save the entry and display the text with the selected size
- "View History" button to navigate to the history screen
- A display area to show the entered text with the chosen font size
- History screen showing all saved entries with their text, size, and timestamp
- Ability to clear the entire database

### Database Features

- Persistent storage using SQLite database
- Automatic timestamp recording for each entry
- Asynchronous database operations using Kotlin Coroutines
- Error handling for database operations
- Success/error notifications for user actions

## How to Run

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/vladriabyi/AndroidDev_Lab3.git
   ```

2. Open the project in Android Studio

3. Build and run the application on an Android emulator or a physical device

## Dependencies

- Jetpack Compose
- Kotlin
- Kotlin Coroutines
- SQLite Database
- AndroidX Fragment
- Material Design 3

## Technical Implementation

The application uses:
- MVVM architecture pattern
- Jetpack Compose for UI
- SQLiteOpenHelper for database operations
- Kotlin Coroutines for asynchronous operations
- Fragment-based navigation
- Material Design 3 components

## Features

1. **Main Screen**
   - Text input field
   - Font size selection
   - OK button to save and display
   - View History button

2. **Result Screen**
   - Display of entered text with selected size
   - Cancel button to return to input
   - View History button

3. **History Screen**
   - List of all saved entries
   - Entry details (text, size, timestamp)
   - Clear Database button
   - Back button to return to main screen

## Error Handling

- Input validation for empty text
- Database operation error notifications
- User-friendly error messages
- Asynchronous operation handling
