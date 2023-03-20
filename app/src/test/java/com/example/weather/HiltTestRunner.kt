package com.example.weather

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
class HiltTestRunner : AndroidJUnitRunner() {

 /*  override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }*/
}
