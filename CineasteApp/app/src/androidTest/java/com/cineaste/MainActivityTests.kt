package com.cineaste

import android.app.Instrumentation
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.equalTo

import org.hamcrest.core.Is

import org.junit.Before
import org.junit.Test

class MainActivityTests {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }
    @Test
    fun testSearchButtonDisplayed() {
        onView(withId(R.id.searchButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSearchTextDisplayed() {
        onView(withId(R.id.searchText))
            .check(matches(isDisplayed()))
    }
    @Test
    fun testFavoriteMoviesListDisplayed() {
        // Check if the RecyclerView for favorite movies is displayed
        onView(withId(R.id.favoriteMovies))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFavouriteMovieListCount(){
        onView(withId(R.id.favoriteMovies)).check(hasItemCount(8))
    }

    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                equalTo(n)
            )
        }
    }
}