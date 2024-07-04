package com.cineaste

import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasProperty
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTests {
    @Test
    fun testGetFavoriteMovies(){
        val movies = getFavoriteMovies()
        assertEquals(movies.size, 8)
        assertThat(
            movies,
            hasItem<Movie>(
                hasProperty(
                    "title",
                    `is`("Pulp Fiction")
                )
            )
        )
        assertThat(
            movies,
            not(
                hasItem<Movie>(
                    hasProperty(
                        "title",
                        `is`("Black Widow")
                    )
                )
            )
        )
    }
    @Test
    fun testGetRecentMovies(){
        val movies = getRecentMovies()
        assertEquals(movies.size, 5)
        assertThat(
            movies,
            hasItem<Movie>(
                hasProperty(
                    "title",
                    `is`("Deadpool & Wolverine")
                )
            )
        )
        assertThat(
            movies,
            not(
                hasItem<Movie>(
                    hasProperty(
                        "title",
                        `is`("Pulp Fiction")
                    )
                )
            )
        )
    }
}