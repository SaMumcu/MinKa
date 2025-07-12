package com.cattishapps.minka.initializer

import android.content.Context
import androidx.startup.Initializer
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import com.microsoft.clarity.models.LogLevel

class ClarityInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val config = ClarityConfig(
            projectId = "YOUR_PROJECT_ID",
            logLevel = LogLevel.Verbose
        )
        Clarity.initialize(context, config)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}