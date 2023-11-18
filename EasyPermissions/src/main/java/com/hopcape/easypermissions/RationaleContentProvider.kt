package com.hopcape.easypermissions

/**
 * Provides the [RationaleContent]*/

interface PermissionRationaleContentProvider {

    /**
     * Returns [RationaleContent]
     * @param isPermanentlyDeclined whether permission is denied permanently
     **/
    fun getPermissionRationaleContent(isPermanentlyDeclined: Boolean): RationaleContent
}