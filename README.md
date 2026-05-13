# LETTURA 📚

Lettura is an Android book reading application developed for the Year 2 Semester 1 Human Computer Interaction (HCI) module using Java and Firebase.

The application allows users to create accounts, publish books, explore uploaded books, and read PDFs directly inside the app using an integrated PDF viewer.

---

# Features

* User Signup & Login
* Firebase Authentication
* Publish Books
* Explore Uploaded Books
* In-App PDF Viewer
* RecyclerView Book Listing
* Delete Uploaded Books
* Bionic Reading Feature
* Google Drive PDF Integration
* Modern Responsive UI

---

# Technologies Used

* Java
* Android Studio
* Firebase Authentication
* Firebase Realtime Database
* RecyclerView
* ConstraintLayout
* Android PDF Viewer Library
* Google Drive Integration

---

# System Requirements

## Software Requirements

* Android Studio Hedgehog or newer
* JDK 17
* Gradle 8+
* Android SDK 34
* Firebase Project
* Internet Connection

## Android Device Requirements

* Android 8.0 or higher
* Internet Connection

---

# Firebase Setup

To run this project locally, create your own Firebase project.

## Step 1 — Create Firebase Project

1. Go to Firebase Console
2. Create a new Firebase project
3. Add an Android app
4. Use your package name

Example:

```
com.example.bookreadingapp
```

---

## Step 2 — Enable Firebase Authentication

1. Open Firebase Console
2. Go to Authentication
3. Click Get Started
4. Enable:

```
Email/Password Authentication
```

---

## Step 3 — Enable Realtime Database

1. Open Realtime Database
2. Create Database
3. Start in Test Mode

Example Rules:

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

---

## Step 4 — Download google-services.json

1. Open Project Settings
2. Download:

```
google-services.json
```

3. Place it inside:

```
app/google-services.json
```

---

# Project Setup

## Clone Repository

```bash
git clone YOUR_GITHUB_REPOSITORY_LINK
```

---

## Open Project

1. Open Android Studio
2. Click:

```
Open Existing Project
```

3. Select the cloned project folder

---

## Sync Gradle

Allow Android Studio to:

```
Sync Gradle Files
```

---

# Required Dependencies

Add these dependencies inside:

```
app/build.gradle
```

```gradle
implementation 'com.google.firebase:firebase-auth:23.0.0'
implementation 'com.google.firebase:firebase-database:21.0.0'
implementation 'com.github.mhiew:android-pdf-viewer:3.2.0-beta.3'
implementation 'com.squareup.okhttp3:okhttp:4.12.0'
```

---

# JitPack Repository

Add inside:

```
settings.gradle
```

```gradle
maven { url 'https://jitpack.io' }
```

---

# Android Permissions

Inside:

```
AndroidManifest.xml
```

Add:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

---

# Google Drive PDF Setup

PDF files are not stored using Firebase Storage.

Instead:

1. Upload PDFs manually to Google Drive
2. Set file visibility to:

```
Anyone with the link → Viewer
```

3. Paste the Google Drive link into the application when publishing books

---

# Application Flow

```
Signup
   ↓
Login
   ↓
Dashboard
   ↓
Publish Book / Explore Books
   ↓
Open PDF Inside App
```

# Application User Interfaces

## Login & SignUp Interfaces

<img width="250" height="500" alt="Login" src="https://github.com/user-attachments/assets/9b61f3c2-c845-4245-9c41-a7f4a610e413" />
<img width="250" height="500" alt="Sign Up" src="https://github.com/user-attachments/assets/c4bbda77-fa92-4a00-97af-bde02c0ae131" />

## Dasboard Interface

<img width="250" height="500" alt="Dashboard" src="https://github.com/user-attachments/assets/05faa803-caab-4c60-a7b7-3dbc848246d0" />

## Bionic Reader, Publish and Explore Books Interfaces

<img width="250" height="500" alt="BionicReader" src="https://github.com/user-attachments/assets/015a1ac4-8002-434f-bff4-ddc572a6a1c9" />
<img width="250" height="500" alt="PublishBooks" src="https://github.com/user-attachments/assets/855942f6-b6fa-4ede-a0ae-f7c2940b5de0" />
<img width="250" height="500" alt="ExploreBooks" src="https://github.com/user-attachments/assets/768db482-9e91-4c62-a303-66c3d88284c6" />


# License

This project is intended for educational and portfolio purposes.
