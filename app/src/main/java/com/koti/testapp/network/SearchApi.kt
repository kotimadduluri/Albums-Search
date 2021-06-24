package com.koti.testapp.network

import com.koti.testapp.network.response.Contributers
import com.koti.testapp.network.response.GitRepository
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi{
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/repositories")
    suspend fun search(
        @Query("q") query: String,
        @Query("per_page") perPage: Int=10,
        @Query("page") page: Int=1
    ) : GitRepository

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{login}/{name}/contributors")
    suspend fun getContributors(
        @Path("name") name: String,
        @Path("login") login: String
    ) : List<Contributers>
}
