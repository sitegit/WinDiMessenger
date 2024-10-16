package com.example.windimessenger

import android.app.Application
import com.example.windimessenger.di.DaggerWinDiApplicationComponent
import com.example.windimessenger.di.WinDiApplicationComponent

class WinDiApplication : Application() {

    val component: WinDiApplicationComponent by lazy {
        DaggerWinDiApplicationComponent.factory().create(this)
    }
}

//@Composable
//fun getApplicationComponent(): ApplicationComponent {
//    return (LocalContext.current.applicationContext as WinDiApplication).component
//}