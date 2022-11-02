package com.example.tech_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tech_test.model.Space_ModelItem

@Entity(tableName = "article_table")
class ArticleEntity(val spaceModel: Space_ModelItem) {
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0
}