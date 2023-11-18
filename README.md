# EasyPermissions Library

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Latest Version](https://img.shields.io/badge/Latest%20Version-1.0-green)](https://github.com/your_username/your_repository/releases/tag/1.0)

EasyPermissions is an Android library designed to simplify permission handling based on best practices outlined in the [Android Developer's documentation](https://developer.android.com/training/permissions/requesting).

---

## Overview

This library provides simplified permission request handling using a delegate pattern and encapsulates rationale content generation.

### Setup

------------


In your build.gradle (Module) or settings.gradle add the below snippet.
```groovy
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
In your build.gradle (Module) add the following snippet.
```groovy
dependencies {
	        // other dependencies ...
	        implementation 'com.github.Aumaidkh:EasyPermissionHandler:Tag'
	}
```


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

### Previews

------------
Permission has not been requested yet.
###### Day Mode ( System Dialog )
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/5fa0d88b-febf-4e20-b9cb-1a8c328beb3b)


###### Night Mode  ( System Dialog )
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/6c863ca1-655c-4e8b-8c13-7b35941b8beb)

User has once denied the permission.
###### Day Mode
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/260d8b90-fd76-47a2-b851-70ef0270e0ec)

###### Night Mode
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/ccf8925e-e872-4be4-b145-a34cc30e723e)


User had denied the permission permanently
###### Day Mode
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/ed4e5f2c-8842-4926-8bd3-53822c92471e)

###### Night Mode
![image](https://github.com/Aumaidkh/EasyPermissionHandler/assets/52782821/1de6b500-f160-4cd0-910b-c47149899347)

### Theming
---
The library uses the primary color from the theme that has been applied to the activity/fragment for the buttons and icon to be displayed.
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
