package com.example.carapp.carSelect.presentation

sealed class CarEvent {
    object OnGetManufacturer : CarEvent()
    data class OnGetMainType(val manufacturerId: String) : CarEvent()
    data class OnGetBuiltDate(val manufacturerId: String, val mainTypeId: String) : CarEvent()
    data class OnSearch(val key: String) : CarEvent()
    data class OnSearchFocusedChanged(val isFocused: Boolean) : CarEvent()
}

enum class ListType() {
    MANUFACTURER, MAIN_TYPE, BUILT_DATE
}
