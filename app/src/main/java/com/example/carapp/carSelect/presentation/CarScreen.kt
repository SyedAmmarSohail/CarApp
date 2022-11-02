package com.example.carapp.carSelect.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carapp.R
import com.example.carapp.carSelect.presentation.component.ActionAppBar
import com.example.carapp.carSelect.presentation.component.ComposeBorderText
import com.example.carapp.carSelect.presentation.component.ComposeButton
import com.example.carapp.carSelect.utils.UiEvent
import com.example.carapp.ui.theme.DarkGray
import com.example.carapp.ui.theme.spacerHeight
import com.example.carapp.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CarScreen(
    viewModel: CarViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val listType = rememberSaveable { mutableStateOf(ListType.MANUFACTURER) }


    val manufacturerValue =
        rememberSaveable { mutableStateOf(context.getString(R.string.car_manufacturer)) }
    val mainTypeValue =
        rememberSaveable { mutableStateOf(context.getString(R.string.car_main_type)) }
    val builtDateValue =
        rememberSaveable { mutableStateOf(context.getString(R.string.car_built_date)) }

    val state = viewModel.state

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                    keyboardController?.hide()
                }
            }
        }
    }


    ModalBottomSheetLayout(
        modifier = Modifier.testTag("testBottomSheet"),
        sheetState = bottomSheetScaffoldState,
        sheetContent = {
            SheetContentScreen(state, listType.value, onClick = {
                coroutineScope.launch {
                    viewModel.updateList(it, listType.value)
                    when (listType.value) {
                        ListType.MANUFACTURER -> {
                            mainTypeValue.value = context.getString(R.string.car_main_type)
                            builtDateValue.value = context.getString(R.string.car_built_date)
                            manufacturerValue.value = it
                        }
                        ListType.MAIN_TYPE -> {
                            builtDateValue.value = context.getString(R.string.car_built_date)
                            mainTypeValue.value = it
                        }
                        ListType.BUILT_DATE -> {
                            builtDateValue.value = it
                        }
                    }
                    bottomSheetScaffoldState.hide()
                }

            }, onLoadMore = {
                viewModel.onEvent(CarEvent.OnGetManufacturer)
            }, onSearch = {
                viewModel.onEvent(CarEvent.OnSearch(it))
            }, onFocusChanged = {
                viewModel.onEvent(CarEvent.OnSearchFocusedChanged(it))
            })
        },
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.spacing.view_4x,
            topEnd = MaterialTheme.spacing.view_4x
        ),
    ) {
        Scaffold(
            topBar = {
                ActionAppBar(
                    title = stringResource(R.string.select_car_data),
                    textStyle = MaterialTheme.typography.h3.copy(color = DarkGray)
                )
            }
        ) {
            val configuration = LocalConfiguration.current
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = MaterialTheme.spacing.view_4x)
            ) {
                item {
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            setLandscapeScreen(
                                manufacturerValue,
                                coroutineScope,
                                listType,
                                viewModel,
                                bottomSheetScaffoldState,
                                mainTypeValue,
                                builtDateValue,
                            )
                        }
                        else -> {
                            setPortraitScreen(
                                manufacturerValue,
                                coroutineScope,
                                listType,
                                viewModel,
                                bottomSheetScaffoldState,
                                mainTypeValue,
                                builtDateValue,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun setPortraitScreen(
    manufacturerValue: MutableState<String>,
    coroutineScope: CoroutineScope,
    listType: MutableState<ListType>,
    viewModel: CarViewModel,
    bottomSheetScaffoldState: ModalBottomSheetState,
    mainTypeValue: MutableState<String>,
    builtDateValue: MutableState<String>,
) {
    val context = LocalContext.current

    CarSummaryScreen(
        manufacturerValue.value,
        mainTypeValue.value,
        builtDateValue.value
    )

    spacerHeight(height = MaterialTheme.spacing.view_6x)

    Column(
        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.view_4x),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ManufacturerBorderText(
            manufacturerValue,
            context.getString(R.string.manufacturer),
            coroutineScope,
            listType,
            viewModel,
            bottomSheetScaffoldState
        )

        spacerHeight(height = MaterialTheme.spacing.view_3x)

        MainTypeBorderText(
            mainTypeValue,
            context.getString(R.string.model),
            coroutineScope,
            listType,
            viewModel,
            manufacturerValue,
            bottomSheetScaffoldState
        )

        spacerHeight(height = MaterialTheme.spacing.view_3x)

        BuiltDateBorderText(
            builtDateValue,
            context.getString(R.string.year),
            coroutineScope,
            listType,
            viewModel,
            manufacturerValue,
            mainTypeValue,
            bottomSheetScaffoldState
        )

        spacerHeight(height = MaterialTheme.spacing.view_3x)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun setLandscapeScreen(
    manufacturerValue: MutableState<String>,
    coroutineScope: CoroutineScope,
    listType: MutableState<ListType>,
    viewModel: CarViewModel,
    bottomSheetScaffoldState: ModalBottomSheetState,
    mainTypeValue: MutableState<String>,
    builtDateValue: MutableState<String>,
) {
    val context = LocalContext.current

    spacerHeight(height = MaterialTheme.spacing.view_4x)

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f).padding(horizontal = MaterialTheme.spacing.view_4x)) {

            ManufacturerBorderText(
                manufacturerValue,
                context.getString(R.string.manufacturer),
                coroutineScope,
                listType,
                viewModel,
                bottomSheetScaffoldState
            )

            spacerHeight(height = MaterialTheme.spacing.view_3x)

            MainTypeBorderText(
                mainTypeValue,
                context.getString(R.string.model),
                coroutineScope,
                listType,
                viewModel,
                manufacturerValue,
                bottomSheetScaffoldState
            )

            spacerHeight(height = MaterialTheme.spacing.view_3x)

            BuiltDateBorderText(
                builtDateValue,
                context.getString(R.string.year),
                coroutineScope,
                listType,
                viewModel,
                manufacturerValue,
                mainTypeValue,
                bottomSheetScaffoldState
            )
        }

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            CarSummaryScreen(
                manufacturerValue.value,
                mainTypeValue.value,
                builtDateValue.value,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BuiltDateBorderText(
    builtDateValue: MutableState<String>,
    label : String,
    coroutineScope: CoroutineScope,
    listType: MutableState<ListType>,
    viewModel: CarViewModel,
    manufacturerValue: MutableState<String>,
    mainTypeValue: MutableState<String>,
    bottomSheetScaffoldState: ModalBottomSheetState
) {
    ComposeBorderText(
        modifier = Modifier.testTag("testBuiltDate"),
        text = builtDateValue.value,
        label = label,
        icon = R.drawable.ic_built_date
    ) {
        coroutineScope.launch {
            listType.value = ListType.BUILT_DATE
            viewModel.onEvent(
                CarEvent.OnGetBuiltDate(
                    manufacturerValue.value,
                    mainTypeValue.value
                )
            )
            bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainTypeBorderText(
    mainTypeValue: MutableState<String>,
    label : String,
    coroutineScope: CoroutineScope,
    listType: MutableState<ListType>,
    viewModel: CarViewModel,
    manufacturerValue: MutableState<String>,
    bottomSheetScaffoldState: ModalBottomSheetState
) {
    ComposeBorderText(
        modifier = Modifier.testTag("testMainType"),
        text = mainTypeValue.value,
        label = label,
        icon = R.drawable.ic_main_type
    ) {
        coroutineScope.launch {
            listType.value = ListType.MAIN_TYPE
            viewModel.onEvent(CarEvent.OnGetMainType(manufacturerValue.value))
            bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ManufacturerBorderText(
    manufacturerValue: MutableState<String>,
    label : String,
    coroutineScope: CoroutineScope,
    listType: MutableState<ListType>,
    viewModel: CarViewModel,
    bottomSheetScaffoldState: ModalBottomSheetState
) {
    ComposeBorderText(
        modifier = Modifier.testTag("testManufacturer"),
        text = manufacturerValue.value,
        label = label,
        icon = R.drawable.ic_manufacturer
    ) {
        coroutineScope.launch {
            listType.value = ListType.MANUFACTURER
            viewModel.onEvent(CarEvent.OnGetManufacturer)
            bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }
}