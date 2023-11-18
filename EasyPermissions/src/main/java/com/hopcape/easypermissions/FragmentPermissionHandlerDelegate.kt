package com.hopcape.easypermissions

import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Delegate for getting access to the
 * [IPermissionHandler] inside a [Fragment]*/
class FragmentPermissionHandlerDelegate(
    private val fragment: Fragment
): ReadOnlyProperty<Fragment, IPermissionHandler> {

    private val permissionHandler by lazy {
        PermissionHandlerImpl(
            activity = fragment.requireActivity())
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): IPermissionHandler {
        return permissionHandler
    }

}

/**
 * Helper function for getting hold of
 * [FragmentPermissionHandlerDelegate]*/
fun Fragment.getPermissionHandler() = FragmentPermissionHandlerDelegate(this)