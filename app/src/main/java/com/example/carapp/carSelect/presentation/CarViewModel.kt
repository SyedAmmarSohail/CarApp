package com.example.carapp.carSelect.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapp.R
import com.example.carapp.carSelect.domain.usecase.CarUsecase
import com.example.carapp.carSelect.presentation.component.dummyList
import com.example.carapp.carSelect.utils.DefaultPaginator
import com.example.carapp.carSelect.utils.UiEvent
import com.example.carapp.carSelect.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carUsecase: CarUsecase
) : ViewModel() {

    var state by mutableStateOf(CarUiState(isSearching = true, manufacturerList = dummyList))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isSearching = it)
        },
        onRequest = { nextPage ->
            carUsecase.manufacturerUsecase.invoke(nextPage, PAGE_SIZE)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.service_fetch_error)))
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                isSearching = false,
                manufacturerList = if (newKey <= 1) items
                else state.manufacturerList.plus(items).toMutableMap(),
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun onEvent(carEvent: CarEvent) {
        when (carEvent) {
            CarEvent.OnGetManufacturer -> getManufacturer()
            is CarEvent.OnGetMainType -> getMainType(carEvent.manufacturerId)
            is CarEvent.OnGetBuiltDate -> getBuiltDate(carEvent.manufacturerId, carEvent.mainTypeId)
            is CarEvent.OnSearch -> state = state.copy(searchKey = carEvent.key)
            is CarEvent.OnSearchFocusedChanged -> state = state.copy(
                isHintVisible = !carEvent.isFocused && state.searchKey.isBlank()
            )
        }
    }

    private fun getManufacturer() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun getMainType(manufacturerValue: String) {
        if (state.manufacturerList.isNotEmpty()) {
            viewModelScope.launch {
                val manufacturerId =
                    state.manufacturerList.filterValues { it.title == manufacturerValue }.keys.firstOrNull()
                        ?: return@launch _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.select_car_manufacturer_first_msg)))

                carUsecase.mainTypeUsecase.invoke(manufacturerId).onSuccess {
                    state = state.copy(isSearching = false, mainTypeList = it)
                }.onFailure {
                    _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.service_fetch_error)))
                }
            }
        }
    }

    private fun getBuiltDate(manufacturerValue: String, mainTypeValue: String) {

        viewModelScope.launch {
            if (state.manufacturerList.isEmpty() && state.mainTypeList.isEmpty()) return@launch _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(
                        R.string.select_car_manufacturer_main_type_first_msg
                    )
                )
            )
            val manufacturerId =
                state.manufacturerList.filterValues { it.title == manufacturerValue }.keys.firstOrNull()
                    ?: return@launch _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(
                                R.string.select_car_manufacturer_first_msg
                            )
                        )
                    )

            val mainTypeId =
                state.mainTypeList.filterValues { it.title == mainTypeValue }.keys.firstOrNull()
                    ?: return@launch _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(
                                R.string.select_car_main_type_first_msg
                            )
                        )
                    )

            carUsecase.builtDateUsecase.invoke(manufacturerId, mainTypeId).onSuccess {
                state = state.copy(isSearching = false, builtDateList = it)
            }.onFailure {
                _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.service_fetch_error)))
            }
        }
    }

    fun updateList(value: String, listType: ListType) {
        when (listType) {
            ListType.MANUFACTURER -> state.manufacturerList.values.find { it.title == value }?.isPreviouslySelected =
                true
            ListType.MAIN_TYPE -> state.mainTypeList.values.find { it.title == value }?.isPreviouslySelected =
                true
            ListType.BUILT_DATE -> state.builtDateList.values.find { it.title == value }?.isPreviouslySelected =
                true
        }
    }

    companion object {
        const val PAGE_SIZE = 15
    }
}