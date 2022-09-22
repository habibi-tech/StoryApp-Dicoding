package com.habibi.core.utils

import android.app.Activity
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackBar(message: String) {
    Snackbar.make(
        this.findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG
    ).setTextMaxLines(5)
    .show()
}

fun Activity.showToast(message: String) {
    Toast.makeText(this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}