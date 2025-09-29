package com.bqliang.baddy.rank

import android.app.Application
import com.bqliang.baddyrank.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaddyRankApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Sync.initialize(context = this)
    }
}