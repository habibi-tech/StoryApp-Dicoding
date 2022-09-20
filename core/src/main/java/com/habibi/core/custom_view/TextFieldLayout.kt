package com.habibi.core.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextFieldLayout: TextInputLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addOnEditTextAttachedListener {
            setUpTextFieldEditText(editText)
        }
    }

    private fun setUpTextFieldEditText(editText: EditText?) {
        if (editText is TextInputEditText) {
            (editText as TextFieldEditText).setOnEditTextErrorListener {
                setErrorMessage(it)
            }
        }
    }

    private fun setErrorMessage(message: String) {
        error = message
    }

}