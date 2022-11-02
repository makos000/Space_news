package com.example.tech_test.room

import androidx.room.TypeConverter
import com.example.tech_test.model.Space_ModelItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {
    var gson = Gson()

    @TypeConverter
    fun articlesToString(spaceModel: Space_ModelItem): String = gson.toJson(spaceModel)

    @TypeConverter
    fun stringToArticle(data: String): Space_ModelItem {
        val listType = object : TypeToken<Space_ModelItem>() {}.type
        return gson.fromJson(data, listType)
    }
}