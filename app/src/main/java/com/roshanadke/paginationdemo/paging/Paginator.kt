package com.roshanadke.paginationdemo.paging

interface Paginator<Key, Item> {

    suspend fun loadNextItems()
    fun reset()

}