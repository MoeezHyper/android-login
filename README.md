# ConnectSphere: A Feature-Rich Android Application (Java + Android)

ConnectSphere is a comprehensive and robust Android application built with Java. It demonstrates a full-stack approach to modern app development, integrating everything from dynamic theming and secure authentication to offline data persistence and web service integration.

Originally a simple login system, this project has been expanded into a powerful template that showcases best practices in UI/UX, data management, and application architecture. It's the perfect foundation for building sophisticated, data-driven Android apps.

---

## Key Features & Technical Highlights
This project is organized into distinct sections, each implementing a core aspect of modern Android development.

 **Section 1: Dynamic Theme Management**
- Users can choose from several built-in themes, including Light, Dark, and custom color schemes.
- Runtime Theme Switching: Instantly switch themes from the app's Options Menu without restarting the app.
- Theme Persistence: The user's selected theme is saved using SharedPreferences and automatically applied on subsequent app launches.

 **Section 2: Secure Authentication & State Management**
- Secure Authentication: User login is managed securely, with an authentication flag (isLoggedIn) stored in SharedPreferences.
- Smart Redirection: On app launch, the application checks the isLoggedIn flag and directs users to either the main dashboard or the login screen.
- Robust State Handling: The app gracefully handles configuration changes (like screen rotation) to maintain the user's session and UI state.  

 **Section 3: REST API & Web Service Integration**
- Verifies if the entered email exists in Firebase  
- Redirects to **Reset Password** screen if found
- Live Data Fetching: Fetches data from a public REST API (e.g., users, posts, products) using the efficient Retrofit client.
- JSON Parsing: Seamlessly parses JSON responses into clean, easy-to-use model classes (POJOs).
- Resilient Networking: Gracefully handles network failures, timeouts, and empty API responses to prevent crashes and inform the user.

 **Section 4: Offline-First with SQLite Database**
- Local Data Persistence: Features a well-designed SQLite database schema to store data fetched from the API, enabling offline access.
- Offline Caching: Data is retrieved from the local SQLite database when the device is offline, ensuring a smooth user experience.
- Full CRUD Operations: Implements all essential database operations: Create, Read, Update, and Delete.

 **Section 5: Efficient Data Display with Adapters**
- RecyclerView Implementation: Displays data in a highly efficient and performant list using a custom RecyclerView.Adapter.
- Data Binding: Binds data from the SQLite database to UI components, ensuring the UI is always in sync with the data.
- Interactive List Items: Handles item click events within the adapter, allowing for actions like viewing details, editing, or deleting items.

 **Section 6: Intuitive Navigation & Menus**
- Options Menu: Provides global actions like changing the theme or logging out.
- Context Menu: Offers item-specific actions (e.g., "Edit," "Delete") on long-press.
- Popup Menu: Implements quick, contextual actions directly within list items.
- Intent-Based Navigation: Manages navigation between different screens (Activities) using Intents.

 **Section 7: Integrated Web Content**
- In-App Browsing: Integrates Android's WebView to display external web pages (e.g., product details, documentation) without leaving the app.
- Full Web Control: Enables JavaScript and handles page loading states to provide a seamless browsing experience.

 **Section 8: Interactive UI & Input Validation**
- Rich Input Controls: Utilizes a wide range of Material Design components, including EditText, Button, Spinner, and Switch.
- User Input Validation: Ensures data integrity by validating all user input before processing or saving it.

 **Section 9: Lifecycle & State Mastery**
- Rich Input Controls: Utilizes a wide range of Material Design components, including EditText, Button, Spinner, and Switch.
- User Input Validation: Ensures data integrity by validating all user input before processing or saving it.
- Configuration Change Handling: Preserves and restores UI state during screen rotations using onSaveInstanceState.
- Optimized Data Fetching: Avoids unnecessary API calls by intelligently restoring data from the saved state.
- Memory Safety: Engineered to prevent memory leaks during Activity lifecycle transitions, ensuring app stability.

---

## Tech Stack

| 	Component 	  |      Description	     |
|-------------------------|--------------------------|
| **Language** | Java | 
| **Networking** | Retrofit (for REST API communication) | 
| **Local Database** | SQLite (for offline data persistence) | 
| **UI** | XML, RecyclerView, WebView, Material Components | 
| **State & Prefs** | SharedPreferences, onSaveInstanceState | 
| **Architecture** | ViewModel, Repository Pattern, and Lifecycle-Aware Components | 
| **IDE** | Android Studio | 
| **Min SDK** | 24 (Android 7.0) |

---

🎓 Academic Acknowledgment
This project fulfills the requirements set by the Department of Robotics & Artificial Intelligence at Shaheed Zulfikar Ali Bhutto Institute of Science & Technology (SZABIST) University.


