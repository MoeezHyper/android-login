# 🔐 Simple Android Login Using Firebase Realtime Database (Java + Android)

A simple, elegant Android app built using **Java** and **Firebase Realtime Database** for user authentication.  
It includes **Signup**, **Login**, and **Password Reset** screens — all with a modern UI design and CardView layouts.

---

## 🚀 Features

✅ **Signup Screen**
- Creates new user accounts  
- Stores user data (`name`, `email`, `password`) in Firebase  
- Validates empty fields and email formats  

✅ **Login Screen**
- Authenticates user credentials  
- Checks Firebase for matching email & password  
- Redirects to the main app screen after successful login  

✅ **Forget Password Screen**
- Verifies if the entered email exists in Firebase  
- Redirects to **Reset Password** screen if found  

✅ **Reset Password Screen**
- Allows users to create a new password  
- Validates that both password fields match  
- Updates the password in Firebase Realtime Database  

✅ **UI Highlights**
- Frosted glass-style CardView  
- White hint text and clean input fields  
- Edge-to-edge layout with a custom background  

---

## 🧩 Tech Stack

| Component | Description |
|------------|-------------|
| **Language** | Java |
| **Database** | Firebase Realtime Database |
| **UI** | XML + CardView + Material Components |
| **IDE** | Android Studio |
| **Min SDK** | 24 (Android 7.0) |

---

## ⚙️ Firebase Structure Example

```json
{
  "users": {
    "-Nxyz12345": {
      "name": "John Doe",
      "email": "john@example.com",
      "password": "123456"
    },
    "-Nabc67890": {
      "name": "Jane Doe",
      "email": "jane@example.com",
      "password": "mypassword"
    }
  }
}
