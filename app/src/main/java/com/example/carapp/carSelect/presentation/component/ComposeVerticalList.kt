package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> ComposeVerticalList(
    modifier: Modifier,
    list: List<T>,
    item: @Composable (T, Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(list) { index, item ->
            item(item, index)
        }
    }
}