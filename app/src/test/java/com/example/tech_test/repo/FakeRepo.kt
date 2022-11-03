package com.example.tech_test.repo

import com.example.tech_test.api.ApiInterface
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.room.ArticleDao
import com.example.tech_test.room.ArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class FakeRepo @Inject constructor(val articleDao: ArticleDao, val apiInterface: ApiInterface): RepoInterface {

    private val spaceModelItem = Space_ModelItem(
        events = listOf(),
        featured = false,
        id = 0,
        imageUrl = "",
        launches = listOf(),
        newsSite = "",
        publishedAt = "",
        summary = "",
        title = "",
        updatedAt = "",
        url = ""
    )

    var list : MutableList<Space_ModelItem> = mutableListOf()

    override suspend fun getData(): Response<ArrayList<Space_ModelItem>> {
        return apiInterface.getData()
    }

    override fun insertArticlesToDB(articleEntity: ArticleEntity) {
        list.add(spaceModelItem)
    }

    override fun readArticlesFromDB(): Flow<List<ArticleEntity>> {
        return flow { list }
    }

    override fun nukeTable() {}


}