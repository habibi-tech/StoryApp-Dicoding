package com.habibi.core.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.habibi.core.R

fun Activity.showSnackBar(message: String) {
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        .setTextMaxLines(5)
        .show()
}