package com.example.tech_test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)


@TypeConverters(RoomConverter::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}