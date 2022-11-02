package com.example.carapp.carSelect.presentation

import com.example.carapp.carSelect.domain.model.Type


data class CarUiState(
    val isSearching: Boolean = false,
    val manufacturerList: MutableMap<String, Type> = mutableMapOf(),
    val mainTypeList: MutableMap<String, Type> = mutableMapOf(),
    val builtDateList: MutableMap<String, Type> = mutableMapOf(),
    val searchKey: String = "",
    val isHintVisible: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0
)
