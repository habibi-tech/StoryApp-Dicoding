package com.habibi.storyapp.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.habibi.storyapp.R

class TextFieldEditText: TextInputEditText {

    private var onEditTextErrorListener: (String) -> Unit = {}
    private var editTextType: Int = EditTextType.DESCRIPTION.type

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        checkAttributes(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        checkAttributes(context, attrs)
        init()
    }

    private fun checkAttributes(context: Context, attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextFieldEditText,
            0, 0
        ).apply {
            try {
                editTextType = getInteger(R.styleable.TextFieldEditText_editTextType, EditTextType.DESCRIPTION.type)
            } finally {
                recycle()
            }
        }
    }

    private fun init() {
        doOnTextChanged { text, _, _, _ ->
            text?.let {
                checkValue(it.toString())
            }
        }
    }

    private fun checkValue(text: String) {
        val messageResource: Int = when (editTextType) {
            EditTextType.EMAIL.type -> emailValidate(text)
            EditTextType.PASSWORD.type -> passwordValidate(text)
            EditTextType.NAME.type -> nameValidate(text)
            EditTextType.DESCRIPTION.type -> descriptionValidate(text)
            else -> 0
        }
        val message = if (messageResource == 0) "" else context.getString(messageResource)
        onEditTextErrorListener(message)
    }

    fun setOnEditTextErrorListener(listener: (String) -> Unit) {
        onEditTextErrorListener = listener
    }

}