package com.cineaste

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "d2c3908b1d0393afd947b181e1c519eb"
    ): Response<GetMoviesResponse>
}