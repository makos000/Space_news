package com.example.tech_test.repo

import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.room.ArticleEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepoInterface {

    suspend fun getData(): Response<ArrayList<Space_ModelItem>>

    fun insertArticlesToDB(articleEntity: ArticleEntity)

    fun readArticlesFromDB(): Flow<List<ArticleEntity>>

    fun nukeTable()
}