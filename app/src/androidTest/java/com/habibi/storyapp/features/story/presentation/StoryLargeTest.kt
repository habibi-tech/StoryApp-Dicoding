package com.habibi.storyapp.features.story.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.habibi.core.data.source.remote.network.ApiConstant
import com.habibi.core.utils.EspressoIdlingResource
import com.habibi.storyapp.R
import com.habibi.storyapp.utils.JsonConverter
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class StoryLargeTest {

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
    fun fromStoryListToAddStory() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_story_list)).perform(click())

        onView(withId(R.id.iv_add_preview)).check(matches(isDisplayed()))
        onView(withId(R.id.button_add_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.button_add_gallery)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_add_description)).check(matches(isDisplayed()))
        onView(withId(R.id.button_add)).check(matches(isDisplayed()))
    }

    @Test
    fun fromAddStoryAndBackPressedThenGoToStoryList() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.fab_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_story_list)).perform(click())

        pressBack()

        onView(withId(R.id.fab_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
    }

    @Test
    fun scrollList_Success() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun fromStoryListToDetailStory() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.iv_detail_photo)).check(matches(isDisplayed()))
    }

    @Test
    fun fromDetailStoryAndBackPressedThenGoToStoryList() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        pressBack()

        onView(withId(R.id.fab_story_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
    }

    @Test
    fun showMapsFragment() {
        launchActivity<StoryActivity>()

        val mDispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path?.contains("/stories") == true) {
                    return MockResponse().setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
                }
                return if (request.path?.contains("/stories>?location=1") == true) {
                    MockResponse().setBody(JsonConverter.readStringFromFile("list_story_success_response.json"))
                } else MockResponse().setResponseCode(200)
            }

        }
        mockWebServer.dispatcher = mDispatcher

        onView(withId(R.id.navigation_nearby)).perform(click())
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

}