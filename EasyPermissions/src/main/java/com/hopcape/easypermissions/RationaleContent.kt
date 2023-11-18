package com.hopcape.easypermissions

/**
 * Content to be displayed by the
 * Rationale
 * @property iconResId - Icon to be displayed on the top of notification
 * @property title - title of the notification
 * @property message - of the rationale
 * @property positiveButtonText - text to be displayed on the positive button
 * @property negativeButtonText - (optional) text to be displayed on the negative button*/
data class RationaleContent(
    val iconResId: Int = R.drawable.phone_icon,
    val title: String,
    val message: String,
    val positiveButtonText: String = "Allow",
    val negativeButtonText: String = "Don't Allow",
)
