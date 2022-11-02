package com.example.carapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.carapp.carSelect.domain.model.Type
import com.example.carapp.carSelect.domain.usecase.BuiltDateUsecase
import com.example.carapp.carSelect.domain.usecase.CarUsecase
import com.example.carapp.carSelect.domain.usecase.MainTypeUsecase
import com.example.carapp.carSelect.domain.usecase.ManufacturerUsecase
import com.example.carapp.carSelect.presentation.CarScreen
import com.example.carapp.carSelect.presentation.CarViewModel
import com.example.carapp.repository.CarRepositoryFake
import com.example.carapp.ui.theme.CarAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalComposeUiApi
@HiltAndroidTest
class CarE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var carRepositoryFake: CarRepositoryFake
    private lateinit var carViewModel: CarViewModel
    private lateinit var carUsecase: CarUsecase

    @Before
    fun setup() {
        carRepositoryFake = CarRepositoryFake()

        carUsecase = CarUsecase(
            manufacturerUsecase = ManufacturerUsecase(carRepositoryFake),
            mainTypeUsecase = MainTypeUsecase(carRepositoryFake),
            builtDateUsecase = BuiltDateUsecase(carRepositoryFake)
        )
        carViewModel = CarViewModel(
            carUsecase = carUsecase
        )

        composeRule.setContent {
            CarAppTheme {
                CarScreen()
            }
        }
    }

    @Test
    fun showCarSummaryBySelectingData(){
        setFakeRepositoryResult("Manufacturer")

        composeRule.onNodeWithTag("testManufacturer").performClick()
        composeRule.onNodeWithTag("searchField").performTextInput("Manufacturer1")
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

        setFakeRepositoryResult("MainType")

        composeRule.onNodeWithTag("testMainType").performClick()
        composeRule.onNodeWithTag("searchField").performTextInput("MainType1")
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

        setFakeRepositoryResult("BuiltDate")

        composeRule.onNodeWithTag("testBuiltDate").performClick()
        composeRule.onNodeWithTag("searchField").performTextInput("BuiltDate1")
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

        composeRule.onNodeWithTag("testSummaryButton").performClick()
        composeRule.onNodeWithTag("testSummaryView").assertIsDisplayed()
    }

    private fun setFakeRepositoryResult(type: String) {

        val carMap = mutableMapOf<String, Type>()
        for (i in 1..4) {
            carMap["key$i"] = Type(title = "$type$i")
        }
        carRepositoryFake.resultList = carMap
    }


}