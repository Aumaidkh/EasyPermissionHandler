# EasyPermissions Library

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Latest Version](https://img.shields.io/badge/Latest%20Version-1.0-green)](https://github.com/your_username/your_repository/releases/tag/1.0)

EasyPermissions is an Android library designed to simplify permission handling based on best practices outlined in the [Android Developer's documentation](https://developer.android.com/training/permissions/requesting).

---

## Overview

This library provides simplified permission request handling using a delegate pattern and encapsulates rationale content generation.

### Key Classes

#### RationaleContent
- `RationaleContent` represents the content to be displayed during permission rationale.
  - `iconResId`: Icon displayed at the top of the notification.
  - `title`: Title of the rationale.
  - `message`: Message describing the rationale.
  - `positiveButtonText`: Text for the positive button.
  - `negativeButtonText`: Text for the negative button.

#### PermissionHandlerDelegate
- `PermissionHandlerDelegate` allows access to the `IPermissionHandler` using the `by` keyword in activities.
  - Used to acquire the reference to the permission handler.

#### getPermissionHandler()
- Utility function to obtain the `PermissionsSessionDelegate` using the `by` keyword in activities.

---

## Usage Example

```kotlin
// Example usage in an activity
class MainActivity : AppCompatActivity() {

    private val permission by getPermissionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permission.requestPermissionIfNeeded(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationaleContent = NotificationRationaleContentProvider()
        )
    }

    // Class providing the permission rationale content
    class NotificationRationaleContentProvider : PermissionRationaleContentProvider {
        override fun getPermissionRationaleContent(isPermanentlyDeclined: Boolean): RationaleContent {
            // Return RationaleContent based on the permission status
        }
    }
}
