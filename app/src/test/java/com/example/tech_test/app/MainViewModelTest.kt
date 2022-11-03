package com.example.tech_test.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tech_test.api.ApiInterface
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.repo.FakeRepo
import com.example.tech_test.repo.RepoInterface
import com.example.tech_test.room.ArticleDao
import com.example.tech_test.room.ArticleEntity
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var fakeRepo: FakeRepo
    private val testDispatcher = StandardTestDispatcher()


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()//For LiveData

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

    @Mock
    private lateinit var mockRepo: RepoInterface

    @Mock
    private lateinit var dao: ArticleDao

    @Mock
    private lateinit var api: ApiInterface

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(mockRepo)
        fakeRepo = FakeRepo(dao,api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `get successful response`() = runBlocking{
        val expectedResult = Response.success(arrayListOf(spaceModelItem))
        whenever(mockRepo.getData()).thenReturn(expectedResult)

        viewModel.getData()
        delay(20)
        viewModel.data.observeForever {
            assertEquals(arrayListOf(spaceModelItem), it)
        }
    }

    @Test
    fun `get error response`() = runBlocking{
        val expectedResult = Response.error<ArrayList<Space_ModelItem>>(404, "error".toResponseBody() )
        whenever(mockRepo.getData()).thenReturn(expectedResult)

        viewModel.getData()
        delay(20)

        viewModel.data.observeForever {
            assertEquals("error", it)
        }
    }


    @Test
    fun `get list from fakerepo with space model object after insertArticlesToDB`() = runBlocking{
        val expected = arrayListOf(spaceModelItem)
        fakeRepo.insertArticlesToDB(ArticleEntity(spaceModelItem))
        TestCase.assertEquals(expected, fakeRepo.list)
    }

    @Test
    fun `get empty list of ArticleEntity from flow collect of fake repo`() = runBlocking{
        val expected = listOf<ArticleEntity>()
        fakeRepo.insertArticlesToDB(ArticleEntity(spaceModelItem))
        val result = fakeRepo.readArticlesFromDB()
        result.collect(){
            TestCase.assertEquals(expected, it)
        }

    }


}