package com.example.tech_test.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.repo.RepoInterface
import com.example.tech_test.repo.getOrAwaitValue
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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(mockRepo)
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
    fun `get error response attempt with getorwaitvalue`() = runBlocking{
        val expectedResult = Response.error<ArrayList<Space_ModelItem>>(404, "error".toResponseBody() )
        whenever(mockRepo.getData()).thenReturn(expectedResult)

        viewModel.getData()

        val result = viewModel.data.getOrAwaitValue()

        assertEquals("error", result)
    }
}