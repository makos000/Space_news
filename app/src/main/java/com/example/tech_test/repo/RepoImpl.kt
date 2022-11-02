package com.example.tech_test.repo

import com.example.tech_test.api.ApiInterface
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.room.ArticleDao
import com.example.tech_test.room.ArticleEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepoImpl @Inject constructor(val apiInterface: ApiInterface, val articleDao: ArticleDao): RepoInterface {
    override suspend fun getData(): Response<ArrayList<Space_ModelItem>> {
        return apiInterface.getData()
    }

    override fun insertArticlesToDB(articleEntity: ArticleEntity) {
        return articleDao.insertArticleToDB(articleEntity)
    }

    override fun readArticlesFromDB(): Flow<List<ArticleEntity>> {
        return articleDao.readArticleFromDB()
    }

    override fun nukeTable() {
        return articleDao.nukeTable()
    }

}