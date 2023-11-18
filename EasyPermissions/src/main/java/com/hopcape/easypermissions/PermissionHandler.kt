package com.hopcape.easypermissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat


internal class PermissionHandlerImpl(private val activity: ComponentActivity) : IPermissionHandler {

    private lateinit var permissionResultLauncher: ActivityResultLauncher<String>

    private var permissionAlreadyRequested: Boolean = false


    override fun requestPermissionIfNeeded(permission: String, rationaleContent: PermissionRationaleContentProvider,
                                           versionCheckToEscape: Boolean?) {

        permissionAlreadyRequested = activity.permissionSession(permission).getValue(
            thisRef = this,
            property = this::permissionAlreadyRequested
        )

        permissionResultLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->

                /**
                 * User has clicked on Don't Allow for the first time
                 * */
                val educateUserWhyPermissionIsNeeded =
                    !permissionGranted && permissionAlreadyRequested && activity.shouldShowRequestPermissionRationale(permission)

                /**
                 * User clicked on Don't Allow twice while asking for permission*/
                val userDeniedPermissionPermanently =
                    !permissionGranted && !activity.shouldShowRequestPermissionRationale(permission)

                /**
                 * Show Rationale for why permission is needed and then
                 * request permission again*/
                if (educateUserWhyPermissionIsNeeded) {
                    showRationaleDialog(
                        context = activity,
                        rationaleContent = rationaleContent.getPermissionRationaleContent(false),
                        onPositiveClick = {
                            launchPermissionLauncher(permission)
                        },
                        onNegativeClick = {
                            // User clicked on Don't Allow
                            // Show him the rationale with message directing him to settings
                            launchPermissionLauncher(permission)
                        }
                    )
                    return@registerForActivityResult
                }

                /**
                 * Show user that he has denied permission permanently and take him
                 * to the settings where he can grant the permissions manually if he chooses to
                 * */
                if (userDeniedPermissionPermanently) {
                    // User had denied the permission permanently
                    showRationaleDialog(
                        context = activity,
                        rationaleContent = rationaleContent.getPermissionRationaleContent(true),
                        onPositiveClick = {
                            activity.openAppSettings()
                        },
                        onNegativeClick = {

                        }
                    )
                    return@registerForActivityResult
                }
            }

        versionCheckToEscape?.let {
            if (it) {
                return
            }
        }

        /*
        * Permission Already granted*/
        if (activity.hasPermission(permission)) {
            return
        }

        /*
        * Permission has never been requested yet
        * Request permission and save the flag in preferences that we
        * have requested the permission once*/
        if (!permissionAlreadyRequested && !activity.shouldShowRequestPermissionRationale(permission)) {
            permissionAlreadyRequested = true
            launchPermissionLauncher(permission)
            return
        }

        /*
        * Permission has already been requested once
        * and user has clicked on don't allow and there is a need to educate the user.
        * Show him the rationale and request again*/
        if (permissionAlreadyRequested && activity.shouldShowRequestPermissionRationale(permission)) {
            // Show Rationale and request again
            showRationaleDialog(
                context = activity,
                rationaleContent = rationaleContent.getPermissionRationaleContent(false),
                onPositiveClick = {
                    launchPermissionLauncher(permission)
                },
                onNegativeClick = {
                    // Dismisses the rationale
                }
            )
            return
        }

        /*
        * Permission was requested and user has already
        * chosen to deny the permission permanently, show him a rationale and navigate him to settings*/
        showRationaleDialog(
            context = activity,
            rationaleContent = rationaleContent.getPermissionRationaleContent(true),
            onPositiveClick = {
                activity.openAppSettings()
            },
            onNegativeClick = {

            }
        )

    }

    /**
     * Launches the permissionLauncher for
     * @param permission
     * */
    private fun launchPermissionLauncher(permission: String) {
        permissionResultLauncher.launch(permission)
    }

    /**
     * Shows the Rationale Dialog*/
    private fun showRationaleDialog(context: Context, onPositiveClick: () -> Unit, onNegativeClick: () -> Unit,
                                    rationaleContent: RationaleContent) {
        getPermissionDialog(
            context = context,
            onPositiveClick = onPositiveClick,
            onNegativeClick = onNegativeClick,
            rationaleContent = rationaleContent
        ).show()

    }


    /**
     * Checks if the
     * @param permission is accepted
     * @return true else false*/
    private fun Context.hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Opens up the Settings Screen of the device
     * */
    private fun ComponentActivity.openAppSettings() {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
        } else {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:$packageName")
        }

        startActivity(intent)
    }


}


