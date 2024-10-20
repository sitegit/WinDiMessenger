package com.example.windimessenger

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.windimessenger.di.DaggerWinDiApplicationComponent
import com.example.windimessenger.di.WinDiApplicationComponent


class WinDiApplication : Application() {

    val component: WinDiApplicationComponent by lazy {
        DaggerWinDiApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): WinDiApplicationComponent {
    return (LocalContext.current.applicationContext as WinDiApplication).component
}