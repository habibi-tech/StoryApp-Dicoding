package com.habibi.storyapp.features.authentication.presentation

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.habibi.core.data.source.remote.network.ApiConstant
import com.habibi.core.utils.EspressoIdlingResource
import com.habibi.storyapp.R
import com.habibi.storyapp.features.story.presentation.StoryActivity
import com.habibi.storyapp.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthenticationActivityTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConstant.baseUrl = "http://127.0.0.1:8080/"

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun showLoginToRegisterThenBackPressed() {
        launchActivity<AuthenticationActivity>()

        //goto register
        onView(withId(R.id.tv_login_action_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_action_register)).perform(click())

        //back pressed
        pressBack()

        //check login displayed
        onView(withId(R.id.iv_login_icon)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_question_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_action_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showLoginThenToRegister() {
        launchActivity<AuthenticationActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("register_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        //check login displayed
        onView(withId(R.id.iv_login_icon)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_question_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_action_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //goto register
        onView(withId(R.id.tv_login_action_register)).perform(click())

        //check register displayed
        onView(withId(R.id.ed_register_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_register_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_register_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun registerSuccessThenShowLogin() {
        val name = "Habibi"
        val email = "habibi@mail.com"
        val password = "123456"
        launchActivity<AuthenticationActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("register_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        //goto register
        onView(withId(R.id.tv_login_action_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_action_register)).perform(click())

        //check field and input field
        onView(withId(R.id.ed_register_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_register_name)).perform(typeText(name), closeSoftKeyboard())
        onView(withId(R.id.ed_register_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_register_email)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.ed_register_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_register_password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.button_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_register)).perform(click())

        //check login displayed
        onView(withId(R.id.iv_login_icon)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_question_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_login_action_register)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loginSuccessThenGoToStoryActivity() {
        val email = "habibi@mail.com"
        val password = "123456"
        launchActivity<AuthenticationActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("login_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        Intents.init()

        //input field login
        onView(withId(R.id.ed_login_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_email)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ed_login_password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.button_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //goto StoryActivity
        onView(withId(R.id.button_login)).perform(click())

        //check intent to StoryActivity
        intended(hasComponent(StoryActivity::class.java.name))
    }

}