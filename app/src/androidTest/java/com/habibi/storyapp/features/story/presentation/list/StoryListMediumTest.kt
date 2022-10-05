package com.habibi.storyapp.features.story.presentation.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.habibi.core.data.source.remote.network.ApiConstant
import com.habibi.core.utils.EspressoIdlingResource
import com.habibi.storyapp.R
import com.habibi.storyapp.features.story.presentation.StoryActivity
import com.habibi.storyapp.utils.JsonConverter.readStringFromFile
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class StoryListMediumTest {

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
    fun getDataShow_Empty() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readStringFromFile("list_story_empty_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_story_list_image_error)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_story_list_message_error)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_story_list_reload)).check(matches(isDisplayed()))
    }

    @Test
    fun getDataShow_Success() {
        launchActivity<StoryActivity>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readStringFromFile("list_story_success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list)).check(matches(isDisplayed()))
        onView(withText("budi")).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story_list))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("gg@gg.gg"))
                )
            )
    }

}