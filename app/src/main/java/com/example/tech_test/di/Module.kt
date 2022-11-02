package com.example.tech_test.di

import android.content.Context
import androidx.room.Room
import com.example.tech_test.api.ApiInterface
import com.example.tech_test.api.ApiRes
import com.example.tech_test.repo.RepoImpl
import com.example.tech_test.repo.RepoInterface
import com.example.tech_test.room.ArticleDao
import com.example.tech_test.room.ArticleDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    fun retrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiRes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun getApiDetails(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }


    @Provides
    fun getRepo(apiInterface: ApiInterface, articleDao: ArticleDao): RepoInterface {
        return RepoImpl(apiInterface, articleDao)
    }

    @Provides
    fun provideAcronymDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ArticleDatabase::class.java,
        "ArticleDatabase",
    ).build()

    @Provides
    fun provideDao(database: ArticleDatabase) = database.articleDao()
}