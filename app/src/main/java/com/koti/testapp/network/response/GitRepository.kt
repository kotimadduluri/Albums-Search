package com.koti.testapp.network.response


import com.google.gson.annotations.SerializedName

data class GitRepository(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean,
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("total_count")
    var totalCount: Int
)