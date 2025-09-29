package com.bqliang.baddyrank.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bqliang.baddyrank.core.ui.model.TableColumn

@Preview
@Composable
private fun TableHeaderPreview() {
    TableHeader(
        modifier = Modifier,
        tableColumns = TableColumn.entries,
        backgroundColor = Color(205, 55, 50),
        titleColor = Color.White,
    )
}

@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    titleColor: Color,
    tableColumns: List<TableColumn>,
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tableColumns.forEach { column ->
            Text(
                modifier = Modifier.weight(column.weight),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge,
                color = titleColor,
                text = column.title,
            )
        }
    }
}