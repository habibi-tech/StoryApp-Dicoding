package com.habibi.core.custom_view

import android.util.Patterns
import com.habibi.core.R

fun emailValidate(email: String): Int =
    when {
        email.isEmpty() -> R.string.error_field_empty
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_field_email_invalid
        else -> 0
    }

fun passwordValidate(password: String): Int =
    when {
        password.isEmpty() -> R.string.error_field_empty
        password.length < 6 -> R.string.error_field_password_too_short
        else -> 0
    }

fun nameValidate(name: String): Int =
    when {
        name.isEmpty() -> R.string.error_field_empty
        name.length < 3 -> R.string.error_field_name_too_short
        else -> 0
    }

fun descriptionValidate(description: String): Int =
    when {
        description.isEmpty() -> R.string.error_field_empty
        else -> 0
    }