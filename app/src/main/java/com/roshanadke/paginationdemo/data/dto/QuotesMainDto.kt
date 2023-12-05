package com.roshanadke.paginationdemo.data.dto

data class QuotesMainDto(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int
)