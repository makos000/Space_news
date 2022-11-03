package com.example.tech_test.repo

import com.example.tech_test.api.ApiInterface
import com.example.tech_test.room.ArticleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RepoImplTest {
    private lateinit var repository: RepoImpl
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var dao: ArticleDao

    @Mock
    private lateinit var api: ApiInterface

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        repository = RepoImpl(api, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}