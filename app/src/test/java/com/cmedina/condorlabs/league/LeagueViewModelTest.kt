package com.cmedina.condorlabs.league

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.viewmodel.LeagueViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LeagueViewModelTest {


    @Mock
    private lateinit var context: Application
    private lateinit var leagueViewModel: LeagueViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var onRenderLeagueListObserver: Observer<List<League>>
    private lateinit var leagueList: List<League>

    private val fakeSuccessLeagueRepository = FakeSuccessLeagueRepository()
    private val fakeFailureLeagueRepository = FakeFailureLeagueRepository()

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupObservers() {
        isViewLoadingObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = Mockito.mock(Observer::class.java) as Observer<Any>
        onRenderLeagueListObserver = Mockito.mock(Observer::class.java) as Observer<List<League>>
    }

    private fun mockData() {
        leagueList = fakeSuccessLeagueRepository.mockList
    }


    @Test
    fun `fetch league list with ViewModel and Repository returns successful data`() {
        leagueViewModel = LeagueViewModel(fakeSuccessLeagueRepository)
        with(leagueViewModel) {
            getLeagueList()
            isViewLoading.observeForever(isViewLoadingObserver)
            leagueList.observeForever(onRenderLeagueListObserver)
        }

        runBlockingTest {
            val response = fakeSuccessLeagueRepository.fetchLeagues()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(leagueViewModel.isViewLoading.value)
            Assert.assertTrue(leagueViewModel.leagueList.value?.size == 2)
        }
    }


    @Test
    fun `fetch league list with ViewModel and Repository returns an error`() {
        leagueViewModel= LeagueViewModel(fakeFailureLeagueRepository)
        with(leagueViewModel) {
            getLeagueList()
            isViewLoading.observeForever(isViewLoadingObserver)
            leagueList.observeForever(onRenderLeagueListObserver)
        }

        runBlockingTest {
            val response = fakeFailureLeagueRepository.fetchLeagues()
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(leagueViewModel.isViewLoading.value)
            Assert.assertNotNull(leagueViewModel.onMessageError.value)
        }
    }



}