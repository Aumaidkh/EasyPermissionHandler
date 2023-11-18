package com.hopcape.permissionhandler

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hopcape.easypermissions.PermissionRationaleContentProvider
import com.hopcape.easypermissions.RationaleContent
import com.hopcape.easypermissions.getPermissionHandler

class ExampleFragment : Fragment(){

    /**
     *Step 1:  Get instance of permission handler
     * */
   private val permissionHandler by getPermissionHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_example,container,false)


        //Step 3: Request permission
        permissionHandler.requestPermissionIfNeeded(
            Manifest.permission.BLUETOOTH,
            BluetoothPermissionRationaleContentProvider()
        )
        return view
    }

    /**
     * Step 2: Create Rationale also
     * @see [MainActivity.NotificationRationaleContentProvider]*/
    class BluetoothPermissionRationaleContentProvider: PermissionRationaleContentProvider{
        override fun getPermissionRationaleContent(isPermanentlyDeclined: Boolean): RationaleContent {
            return RationaleContent(
                title = "Allow the app to access your Bluetooth",
                message = "This app needs bluetooth access to connect you to the other users"
            )
        }
    }
}