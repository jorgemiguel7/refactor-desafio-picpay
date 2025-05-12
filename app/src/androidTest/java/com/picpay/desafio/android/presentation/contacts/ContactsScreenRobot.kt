package com.picpay.desafio.android.presentation.contacts

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.di.TestModules
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.MainActivity
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class ContactsScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) : KoinTest {

    private val fakeRepository = TestModules.getFakeUserRepository()

    fun withLoadingState(): ContactsScreenRobot {
        return this
    }

    fun withUsers(users: List<User>): ContactsScreenRobot {
        fakeRepository.emitUsers(users)
        composeTestRule.waitForIdle()
        return this
    }

    fun withError(): ContactsScreenRobot {
        fakeRepository.emitError(Exception("Test error"))
        composeTestRule.waitForIdle()
        return this
    }

    fun scrollToUser(index: Int): ContactsScreenRobot {
        if (index != -1) {
            composeTestRule.onNodeWithTag("contacts_list")
                .performScrollToIndex(index)
        }
        return this
    }

    fun verifyLoadingIndicatorIsVisible(): ContactsScreenRobot {
        composeTestRule.onNodeWithTag("progress_indicator").isDisplayed()
        return this
    }

    fun verifyLoadingIndicatorIsNotVisible(): ContactsScreenRobot {
        composeTestRule.onNodeWithTag("progress_indicator").assertDoesNotExist()
        return this
    }

    fun verifyUserIsDisplayed(name: String, username: String): ContactsScreenRobot {
        composeTestRule.onNodeWithText(name).assertExists()
        composeTestRule.onNodeWithText(username).assertExists()
        return this
    }

    fun verifyUserIsNotDisplayed(name: String): ContactsScreenRobot {
        composeTestRule.onNodeWithText(name).assertDoesNotExist()
        return this
    }

    fun verifyContactsListExists(): ContactsScreenRobot {
        composeTestRule.onNodeWithTag("contacts_list").assertExists()
        return this
    }

    companion object {
        fun setup(
            composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
        ): ContactsScreenRobot {
            TestModules.loadModules()
            composeTestRule.waitForIdle()

            return ContactsScreenRobot(composeTestRule)
        }
    }
}
