package com.roshanadke.paginationdemo.ui.viewmodels

import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import com.roshanadke.paginationdemo.data.dto.Results
import com.roshanadke.paginationdemo.data.network.QuotesApiService

class FakeQuotesService :QuotesApiService {

    val quotesMainDto = QuotesMainDto(
        count = 3993,
        lastItemIndex = 1522,
        page = 2802,
        results = listOf(
            Results(
                _id = "ac",
                author = "adolescens",
                authorSlug = "enim",
                content = "porttitor",
                dateAdded = "neglegentur",
                dateModified = "tale",
                length = 4170,
                tags = listOf()
            ),
            Results(
                _id = "graeci",
                author = "oporteat",
                authorSlug = "porro",
                content = "morbi",
                dateAdded = "risus",
                dateModified = "metus",
                length = 4784,
                tags = listOf()
            )
        ),
        totalCount = 9898,
        totalPages = 6350

    )

    override suspend fun getQuotes(page: Int, limit: Int): QuotesMainDto {
        return quotesMainDto
    }
}