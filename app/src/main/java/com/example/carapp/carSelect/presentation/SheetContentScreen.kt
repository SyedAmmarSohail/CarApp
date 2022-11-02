package com.example.carapp.carSelect.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.example.carapp.R
import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.presentation.component.*
import com.example.carapp.ui.theme.*
import java.util.*

@Composable
fun SheetContentScreen(
    state: CarUiState,
    listType: ListType,
    onClick: (String) -> Unit,
    onLoadMore: () -> Unit,
    onSearch: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit

) {
    var text by rememberSaveable { mutableStateOf("") }

    val list = when (listType) {
        ListType.MANUFACTURER -> state.manufacturerList.values.toList()
        ListType.MAIN_TYPE -> state.mainTypeList.values.toList()
        ListType.BUILT_DATE -> state.builtDateList.values.toList()
    }

    val filterList = if (state.searchKey.isEmpty()) {
        list
    } else list.filter {
        it.title.lowercase(
            Locale.getDefault()
        ).contains(state.searchKey.lowercase(Locale.getDefault()))
    }


    Column {
        SearchTextField(
            modifier = Modifier
                .padding(MaterialTheme.spacing.view_4x)
                .clip(shape = RoundedCornerShape(MaterialTheme.spacing.view_6x))
                .background(color = LightGray),
            text = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
//                TODO("tap on search")
            },
            onFocusChanged = {
                onFocusChanged(it.isFocused)
            }
        )

        spacerHeight(height = MaterialTheme.spacing.view_1x)

        ComposeVerticalList(
            list = filterList,
            modifier = Modifier
        ) { value, index ->
            if (state.isSearching) {
                placeHolderList()
            } else {
                if (listType == ListType.MANUFACTURER && index >= state.manufacturerList.size - 1 && !state.endReached && !state.isSearching) {
                    onLoadMore()
                } else {
                    ItemList(value, onClick, index)
                }
            }
        }

    }
}

@Composable
private fun ItemList(
    value: Type,
    onClick: (String) -> Unit,
    index: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (index % 2 == 0) LightIndigo else White
            )
            .clickable {
                onClick(value.title)

            }
            .semantics {
                contentDescription = "testItemType"
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.spacing.view_4x,
                    horizontal = MaterialTheme.spacing.view_4x
                ),
            text = value.title,
            color = DarkGray,
            style = MaterialTheme.typography.body1.copy(
                color = DarkGray,
                fontWeight = FontWeight.Bold
            )
        )
        if (value.isPreviouslySelected)
            ComposeTextWithBackground(
                modifier = Modifier.padding(end = MaterialTheme.spacing.view_4x),
                text = stringResource(id = R.string.previously_selected),
                textStyle = MaterialTheme.typography.caption.copy(
                    fontSize = MaterialTheme.fontSize.view_10x,
                    color = White
                ),
                backgroundColor = DarkGreen,
            )

    }
}

@Composable
private fun placeHolderList() {
    Box(
        modifier = Modifier.padding(
            vertical = MaterialTheme.spacing.view_4x,
            horizontal = MaterialTheme.spacing.view_4x
        )
    ) {
        Column {
            LinePlaceHolder()
        }
    }
}