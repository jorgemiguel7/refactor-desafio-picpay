package com.picpay.desafio.android.presentation.contacts

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.di.KoinTestRule
import com.picpay.desafio.android.di.TestModules
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class ContactsScreenRobotTest : KoinTest {

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        TestModules.loadModules()
    }

    @After
    fun tearDown() {
        TestModules.unloadModules()
    }

    @Test
    fun contactsScreen_whenLoadingState_showsProgressIndicator() {
        ContactsScreenRobot.setup(composeTestRule)
            .withLoadingState()
            .verifyLoadingIndicatorIsVisible()
    }

    @Test
    fun contactsScreen_whenUsersLoaded_showsUserList() {
        val users = listOf(
            User(id = 1, name = "John Doe", username = "@johndoe", image = "url1"),
            User(id = 2, name = "Jane Smith", username = "@janesmith", image = "url2")
        )

        ContactsScreenRobot.setup(composeTestRule)
            .withUsers(users)
            .verifyLoadingIndicatorIsNotVisible()
            .verifyUserIsDisplayed("John Doe", "@johndoe")
            .verifyUserIsDisplayed("Jane Smith", "@janesmith")
    }

    @Test
    fun contactsScreen_whenError_hidesLoadingIndicator() {
        ContactsScreenRobot.setup(composeTestRule)
            .withError()
            .verifyLoadingIndicatorIsNotVisible()
    }

    @Test
    fun contactsScreen_withEmptyUserList_showsOnlyList() {
        ContactsScreenRobot.setup(composeTestRule)
            .withUsers(emptyList())
            .verifyLoadingIndicatorIsNotVisible()
            .verifyContactsListExists()
    }

    @Test
    fun contactsScreen_canScrollToSeeAllUsers() {
        val users = List(20) { index ->
            User(
                id = index,
                name = "User $index",
                username = "@user$index",
                image = "url$index"
            )
        }

        ContactsScreenRobot.setup(composeTestRule)
            .withUsers(users)
            .verifyUserIsDisplayed("User 0", "@user0")
            .scrollToUser(users.size)
            .verifyUserIsDisplayed("User 19", "@user19")
    }
}
