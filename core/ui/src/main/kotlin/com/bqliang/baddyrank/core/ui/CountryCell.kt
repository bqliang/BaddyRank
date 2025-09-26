package com.bqliang.baddyrank.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Preview(showBackground = true)
@Composable
private fun CountryCellPreview() {
    CountryCell(
        countryAbbreviation = "CHN",
        countryFlag = R.drawable.china,
    )
}

@Composable
fun CountryCell(
    modifier: Modifier = Modifier,
    countryAbbreviation: String,
    countryFlag: Any,
) {
    Box(
        modifier = modifier.height(72.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = countryAbbreviation,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            modifier = Modifier.alpha(0f)
        )

        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = countryAbbreviation,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
            )

            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                model = countryFlag,
                contentDescription = null,
            )
        }
    }
}
