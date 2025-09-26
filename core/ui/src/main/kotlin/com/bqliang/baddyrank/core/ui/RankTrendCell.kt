package com.bqliang.baddyrank.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RankTrendCell(
    modifier: Modifier = Modifier,
    rankTrend: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val icon = when {
            rankTrend > 0 -> Icons.AutoMirrored.Default.TrendingUp
            rankTrend < 0 -> Icons.AutoMirrored.Default.TrendingDown
            else -> null
        }


        if (icon != null) {
            Icon(
                modifier = Modifier.size(12.dp),
                tint = if (rankTrend > 0) Color.Green else Color.Red,
                imageVector = icon,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(4.dp))
        }

        val text = if (rankTrend == 0) "-" else rankTrend.toString()
        Text(text = text)
    }
}