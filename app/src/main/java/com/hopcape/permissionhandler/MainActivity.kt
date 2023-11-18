package com.hopcape.permissionhandler

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hopcape.easypermissions.PermissionRationaleContentProvider
import com.hopcape.easypermissions.RationaleContent
import com.hopcape.easypermissions.getPermissionHandler

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

    class NotificationRationaleContentProvider: PermissionRationaleContentProvider{
        override fun getPermissionRationaleContent(isPermanentlyDeclined: Boolean): RationaleContent {
            return RationaleContent(
                title = "Allow this app to send you Notifications",
                message = if (isPermanentlyDeclined) "Seems like you have denied the Notification permissions permanently. Please grant the permission from the Settings" else "This app requires notification access to keep you updated.",
                positiveButtonText = if (isPermanentlyDeclined) "Go to settings" else "Grant",
                negativeButtonText = if (isPermanentlyDeclined) "No thanks" else "Deny"
            )
        }
    }

}

