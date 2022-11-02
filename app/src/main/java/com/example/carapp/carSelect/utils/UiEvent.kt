package com.example.carapp.carSelect.utils

sealed class UiEvent {
    data class ShowToast(val message: UiText) : UiEvent()
}
