package com.bqliang.baddy.rank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.ui.NavDisplay
import com.bqliang.baddy.rank.ui.theme.BaddyRankTheme
import com.bqliang.baddyrank.feature.ranking.RankingScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaddyRankTheme {
                RankingScreen()
            }
        }
    }
}

