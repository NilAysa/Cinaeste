package com.cineaste

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.allOf
import org.hamcrest.number.OrderingComparison


@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MovieDetailActivity> = ActivityScenarioRule(MovieDetailActivity::class.java)

    @Test
    fun testDetailActivityInstantiation(){
        val pokreniDetalje = Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).check(matches(withText("Pulp Fiction")))
        onView(withId(R.id.movie_genre)).check(matches(withText("crime")))
        onView(withId(R.id.movie_overview)).check(
            matches(
                withSubstring(
                    "is a cult film that delves into the criminal"
                )
            )
        )
        onView(withId(R.id.movie_poster)).check(matches(withImage(R.drawable.crime)))
    }

    @Test
    fun testLinksIntent(){
        Intents.init()
        val pokreniDetalje = Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_website)).perform(click())
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        Intents.release()
    }

    private fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>(){
        override fun describeTo(description: Description) {
            description.appendText("Drawable does not contain image with id: $id")
        }

        override fun matchesSafely(item: View): Boolean {
            val context: Context = item.context
            val bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
            return item is ImageView && item.drawable.toBitmap().sameAs(bitmap)
        }

    }
    @Test
    fun provjeriRasporedElemenata() {
        val pokreniDetalje = Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pride and Prejudice")
        launchActivity<MovieDetailActivity>(pokreniDetalje)

        onView(withId(R.id.movie_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_poster_card)).check(matches(isDisplayed()))

        onView(withId(R.id.movie_title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Pride and Prejudice")))

        onView(withId(R.id.movie_release_date))
            .check(matches(isDisplayed()))

        onView(withId(R.id.movie_genre))
            .check(matches(isDisplayed()))

        onView(withId(R.id.movie_website))
            .check(matches(isDisplayed()))

        onView(withId(R.id.movie_overview))
            .check(matches(isDisplayed()))

        onView(withId(R.id.shareButton))
            .check(matches(isDisplayed()))
            .check(matches(hasSibling(withId(R.id.movie_overview))))
    }
    @Test
    fun testTitleClickOpensWebSearch() {
        Intents.init()
        val pokreniDetalje = Intent(ApplicationProvider.getApplicationContext(), MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title", "Serenity")
        launchActivity<MovieDetailActivity>(pokreniDetalje)

        onView(withId(R.id.movie_title)).perform(click())

        val searchQuery = "Serenity trailer"
        intended(
            allOf(
                hasAction(Intent.ACTION_WEB_SEARCH),
                hasExtra(SearchManager.QUERY, searchQuery)
            )
        )
    }
    @Test
    fun testSEND() {
        val intent = Intent()
        intent.putExtra(Intent.EXTRA_TEXT, "Neki tekst")
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.`package` = "com.cineaste"
        launchActivity<MainActivity>(intent).use {
            onView(withId(R.id.searchText)).check(matches(withText("Neki tekst")))
        }
    }
    @Test
    fun testFavorites(){
        val pokreniApp = Intent(ApplicationProvider.getApplicationContext(),MainActivity::class.java)
        launchActivity<MainActivity>(pokreniApp)
        val lista = getFavoriteMovies()
        for(movie in lista)
            onView(withId(R.id.favoriteMovies)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(withText(movie.title))
                )
            )
    }


}
//onView podaci odmah dostupni
//onData() podaci koji se definisu sa adapterima
