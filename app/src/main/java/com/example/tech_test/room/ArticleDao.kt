package com.example.tech_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertArticleToDB(beerEntity: ArticleEntity)

    @Query("SELECT * FROM article_table order by `index` DESC")
    fun readArticleFromDB(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM article_table")
    fun nukeTable()
}