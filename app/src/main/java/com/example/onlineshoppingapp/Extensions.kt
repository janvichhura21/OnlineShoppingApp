package com.example.onlineshoppingapp

import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputLayout

fun FragmentActivity.addFragment(
    container: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    supportFragmentManager.beginTransaction()
        .add(container, fragment, fragment.javaClass.simpleName)
        .apply {
            if (addToBackStack) addToBackStack(fragment.javaClass.simpleName)
        }
        .commit()
}

fun FragmentActivity.replaceFragment(
    container: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    supportFragmentManager.beginTransaction()
        .replace(container, fragment, fragment.javaClass.simpleName)
        .apply {
            if (addToBackStack) addToBackStack(fragment.javaClass.simpleName)
        }
        .commit()
}
fun Fragment.addBackButtonListener(listener: () -> Unit) {
    view?.isFocusableInTouchMode = true
    view?.requestFocus()
    view?.setOnKeyListener { _, keyCode, _ ->
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            listener()
            return@setOnKeyListener true
        }

        return@setOnKeyListener false
    }
}

fun TextInputLayout.setErrors(msg:String){
    error = msg
}