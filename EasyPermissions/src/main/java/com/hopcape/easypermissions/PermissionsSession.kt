package com.hopcape.easypermissions

import android.content.Context
import androidx.activity.ComponentActivity
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * To overcome the case where the app is opened for the first time
 * @see [PermissionHandlerImpl.hasPermission] will request false and
 * @see [ComponentActivity.shouldShowRequestPermissionRationale] will also return false
 * and in the case where user has denied the permission permanently both the methods will return false too
 * so to differentiate between the cases below helper functions save a flag in preferences and when this
 * flag is not set it will be the case where the permissions has never been requested yet and when the
 * flat is set it will mean the permission has been requested already this way we will be able to differentiate
 * between the two cases.
 *
 * The below class is responsible for storing the flag for the
 * @property propertyName - which here is going to be the actual permission with
 * @property defaultValue - which is going to be set to false initially
 * @property context - is used to construct the Shared Prefs
 * */
internal class PermissionsSessionDelegate(
    private val context: Context,
    private val propertyName: String,
    private val defaultValue: Boolean
): ReadWriteProperty<Any?,Boolean>{

    private val sharedPreferences by lazy {
        context.getSharedPreferences("permissionsSession", ComponentActivity.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return sharedPreferences.getBoolean(propertyName,defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        sharedPreferences.edit().putBoolean(propertyName,value).apply()
    }
}


/**
 * Helper function for getting hold of the
 * [PermissionsSessionDelegate] with permission
 * @param name*/
internal fun Context.permissionSession(name: String) = PermissionsSessionDelegate(
    context = this,
    propertyName = name,
    defaultValue = false
)