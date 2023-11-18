package com.hopcape.easypermissions

import androidx.activity.ComponentActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Used to get the reference to the
 * [IPermissionHandler] by using 'by' keyword
 * */
class PermissionHandlerDelegate(
    private val activity: ComponentActivity
): ReadOnlyProperty<ComponentActivity,IPermissionHandler>{

    private val permissionHandler by lazy {
        PermissionHandlerImpl(
            activity = activity)
    }

    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): IPermissionHandler {
        return permissionHandler
    }

}

/**
 * Utility function for getting hold of the
 * [PermissionsSessionDelegate] using by keyword in an activity
 * val handler by getPermissionHandler()
 * */
fun ComponentActivity.getPermissionHandler() = PermissionHandlerDelegate(activity = this)