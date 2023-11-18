package com.hopcape.permissionhandler

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hopcape.easypermissions.PermissionRationaleContentProvider
import com.hopcape.easypermissions.RationaleContent
import com.hopcape.easypermissions.getPermissionHandler

class MainActivity : AppCompatActivity() {

    /**
     *Step 1:  Get instance of permission handler
     * */
    private val permissionHandler by getPermissionHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step 3: Ask Permissions
        permissionHandler.requestPermissionIfNeeded(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationaleContent = NotificationRationaleContentProvider()
        )

        findViewById<Button>(R.id.btnExample).setOnClickListener {
            Intent(this,MainActivity2::class.java).also {
                startActivity(it)
            }
        }

    }

    /**
     * Step 2: Create Rationale*/
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

