package com.bqliang.baddyrank.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Preview(showBackground = true)
@Composable
private fun PlayerCellPreview() {
    PlayerCell(
        avatar = R.drawable.shi_yu_qi,
        chineseName = "石宇奇",
        englishName = "Shi Yu Qi",
    )
}

@Composable
fun PlayerCell(
    modifier: Modifier = Modifier,
    avatar: Any,
    chineseName: String,
    englishName: String,
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(size = 40.dp),
            contentScale = ContentScale.Crop,
            model = avatar,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column {
            Text(
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                text = chineseName,
            )
            Text(
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                text = englishName,
            )
        }
    }
}
