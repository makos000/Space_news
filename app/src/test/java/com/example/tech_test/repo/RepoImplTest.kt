package com.example.tech_test.repo

import com.example.tech_test.api.ApiInterface
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.room.ArticleDao
import com.example.tech_test.room.ArticleEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class RepoImplTest {
    private lateinit var repository: RepoImpl
    private lateinit var fakeRepo: FakeRepo
    private val testDispatcher = StandardTestDispatcher()

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
    private lateinit var dao: ArticleDao

    @Mock
    private lateinit var api: ApiInterface

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        repository = RepoImpl(api, dao)
        fakeRepo = FakeRepo(dao,api)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `simulate error response from api call`() = runBlocking{
        val expectedResult = Response.error<ArrayList<Space_ModelItem>>(404, "error".toResponseBody())
        whenever(api.getData()).thenReturn(expectedResult)

        val response = repository.getData()

        assertEquals(response, expectedResult)
    }

    @Test
    fun `simulate succsess response from api call`() = runBlocking{
        val expectedResult = Response.error<ArrayList<Space_ModelItem>>(404, "error".toResponseBody())
        whenever(api.getData()).thenReturn(expectedResult)

        val response = repository.getData()

        assertEquals(response, expectedResult)
    }

    @Test
    fun `get list from fakerepo with space model object after insertArticlesToDB`() = runBlocking{
        val expected = arrayListOf(spaceModelItem)
        fakeRepo.insertArticlesToDB(ArticleEntity(spaceModelItem))
        assertEquals(expected,fakeRepo.list)
    }

    @Test
    fun `get empty list of ArticleEntity from flow collect of fake repo`() = runBlocking{
        val expected = listOf<ArticleEntity>()
        fakeRepo.insertArticlesToDB(ArticleEntity(spaceModelItem))
        val result = fakeRepo.readArticlesFromDB()
        result.collect(){
            assertEquals(expected,it)
        }

    }
}