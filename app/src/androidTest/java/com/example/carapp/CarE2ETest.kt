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
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
class CarE2ETest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var carRepositoryFake: CarRepositoryFake
    private lateinit var carViewModel: CarViewModel
    private lateinit var carUsecase: CarUsecase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
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
                CarScreen(carViewModel)
            }
        }
    }

    @Test
    fun selectingDataFromBottomSheet () : Unit = runBlockingTest{



        setFakeRepositoryResult("Manufacturer")
        composeRule.onNodeWithTag("searchField").performTextInput("Manufacturer1")
        composeRule.onNodeWithTag("testManufacturer").performClick()
        composeRule.onNodeWithTag("testBottomSheet").assertIsDisplayed()
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

        setFakeRepositoryResult("MainType")

        composeRule.onNodeWithTag("searchField").performTextInput("MainType1")
        composeRule.onNodeWithTag("testMainType").performClick()
        composeRule.onNodeWithTag("testBottomSheet").assertIsDisplayed()
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

        setFakeRepositoryResult("BuiltDate")

        composeRule.onNodeWithTag("searchField").performTextClearance()
        composeRule.onNodeWithTag("testBuiltDate").performClick()
        composeRule.onNodeWithTag("testBottomSheet").assertIsDisplayed()
        composeRule.onAllNodesWithContentDescription("testItemType").onFirst().performClick()

    }

    private fun setFakeRepositoryResult(type: String) {

        val carMap = mutableMapOf<String, Type>()
        for (i in 1..4) {
            carMap["key$i"] = Type(title = "$type$i")
        }
        carRepositoryFake.resultList = carMap
    }


    @After
    fun tearDown() {
        unmockkAll()
    }

}