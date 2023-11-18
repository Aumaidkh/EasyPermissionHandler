package com.hopcape.easypermissions

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

internal fun getPermissionDialog(context: Context, onPositiveClick: () -> Unit, onNegativeClick: () -> Unit,
                        rationaleContent: RationaleContent): Dialog {

    val rationaleDialog = Dialog(context)
    rationaleDialog.apply {
        setContentView(R.layout.dialog_layout)
        setDialogTitle(rationaleContent.title)
        setIcon(R.drawable.phone_icon)
        setMessage(rationaleContent.message)
        setPositiveButton(
            buttonText = rationaleContent.positiveButtonText,
            onClick = onPositiveClick
        )
        setNegativeButton(
            buttonText = rationaleContent.negativeButtonText,
            onClick = onNegativeClick
        )
        setWidth(0.87f)
    }

    rationaleDialog.window?.setBackgroundDrawable(context.resources.getDrawable(R.drawable.dialog_background))
    return rationaleDialog
}

private fun Dialog.setDialogTitle(title: String) {
    this.findViewById<TextView>(R.id.tvTitle).text = title
}


private fun Dialog.setMessage(message: String) {
    this.findViewById<TextView>(R.id.tvMessage).text = message
}

private fun Dialog.setIcon(iconResId: Int) {
    this.findViewById<ImageView>(R.id.ivIcon).setImageResource(iconResId)
}

private fun Dialog.setPositiveButton(buttonText: String, onClick: () -> Unit) {
    this.findViewById<Button>(R.id.positiveButton).apply {
        text = buttonText
        setOnClickListener {
            dismiss()
            onClick()
        }
    }
}

private fun Dialog.setNegativeButton(buttonText: String?, onClick: () -> Unit) {
    this.findViewById<Button>(R.id.negativeButton).apply {
        if (buttonText == null){
            // Hide the Button
            this.visibility = View.GONE
        } else {
            text = buttonText
            setOnClickListener {
                dismiss()
                onClick()
            }
        }
    }
}

/**
 * Sets the width to the dialog*/
private fun Dialog.setWidth(percentage: Float){
    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(this.window?.attributes)

    // Set the width to 80% of the screen width
    val displayMetrics = context.resources.displayMetrics
    val dialogWidth = (displayMetrics.widthPixels * percentage).toInt()

    layoutParams.width = dialogWidth
    this.window?.attributes = layoutParams
}