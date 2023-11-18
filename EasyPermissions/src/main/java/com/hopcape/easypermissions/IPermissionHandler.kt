package com.hopcape.easypermissions

/**
 * Used to request permissions all we have to do is
 * method
 * */
interface IPermissionHandler {

    /**
     * Requests permissions if needed
     * @param permission - permissionToRequest
     * @param rationaleContent - Rationale Content
     * @param versionCheckToEscape - if there is a specific version check that needs to be excluded*/
    fun requestPermissionIfNeeded(permission: String,rationaleContent: PermissionRationaleContentProvider,
                                  versionCheckToEscape: Boolean? = null)


}