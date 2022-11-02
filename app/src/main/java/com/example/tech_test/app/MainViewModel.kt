package com.example.tech_test.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.repo.RepoInterface
import com.example.tech_test.room.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repoInterface: RepoInterface): ViewModel() {

    val _data: MutableLiveData<ArrayList<Space_ModelItem>> = MutableLiveData(arrayListOf())
    var recycler_list : ArrayList<Space_ModelItem> = arrayListOf()


    fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repoInterface.getData()
                if(response.isSuccessful){
                    if (response.body()!!.isNotEmpty()){
                        response.body().let{ articles ->

                            if (articles != null) {
                                for (article in articles){
                                    recycler_list.add(article)
                                    insertIntoDatabase(article)
                                }
                            }
                            _data.postValue(recycler_list)
                        }
                    }
                }
                else{
                    Log.i("Response", "Api Response not successful")
                }
            }catch (e: Error){
                Error(e)
            }
        }
    }

    fun insertIntoDatabase(model: Space_ModelItem){
        val articleEntity = ArticleEntity(model)
        CoroutineScope(Dispatchers.IO).launch {
            repoInterface.insertArticlesToDB(articleEntity)
        }
    }

    val readArticle: LiveData<List<ArticleEntity>> = repoInterface.readArticlesFromDB().asLiveData()

    fun nukeData(){
        CoroutineScope(Dispatchers.IO).launch {
            repoInterface.nukeTable()
        }
    }
}