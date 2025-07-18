package com.cattishapps.minka.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.FirebaseApp

class FirebaseInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
