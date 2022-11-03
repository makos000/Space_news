package com.example.tech_test.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.repo.RepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getData() {
        val expectedResult = listOf(spaceModelItem)
        runTest {
            // Given
            Mockito.`when`(mockRepo.getData()).thenReturn(
                Response.success(
                    arrayListOf(
                        spaceModelItem
                    )
                )
            )

            viewModel.getData()
            delay(2000)
            // When
            val actualResult = viewModel.data.value

            // Then
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

   /* @Test
    fun `get succesful response`() = runBlocking{
        val expectedResult = Response.success(arrayListOf(article))
        whenever(mockRepo.getData()).thenReturn(expectedResult)

        viewModel.getData()

        viewModel.data.observeForever {
            assertEquals(expectedResult, it)
        }
    }*/
}