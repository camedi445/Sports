package com.cmedina.condorlabs.team


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.viewmodel.TeamListViewModel
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
class TeamListViewModelTest {


    @Mock
    private lateinit var context: Application
    private lateinit var teamListViewModel: TeamListViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var onRenderTeamListObserver: Observer<List<Team>>
    private lateinit var teamList: List<Team>

    private val fakeSuccessTeamRepository = FakeSuccessTeamRepository()
    private val fakeFailureTeamRepository = FakeFailureTeamRepository()

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
        onRenderTeamListObserver = Mockito.mock(Observer::class.java) as Observer<List<Team>>
    }

    private fun mockData() {
        teamList = fakeSuccessTeamRepository.mockList
    }


    @Test
    fun `fetch team list with ViewModel and Repository returns successful data`() {
        teamListViewModel = TeamListViewModel(fakeSuccessTeamRepository)
        with(teamListViewModel) {
            getTeamsByLeagueId("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            teamList.observeForever(onRenderTeamListObserver)
        }

        runBlockingTest {
            val response = fakeSuccessTeamRepository.fetchTeamsByLeagueId("12")
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(teamListViewModel.isViewLoading.value)
            Assert.assertTrue(teamListViewModel.teamList.value?.size == 2)
        }
    }


    @Test
    fun `fetch league list with ViewModel and Repository returns an error`() {
        teamListViewModel = TeamListViewModel(fakeFailureTeamRepository)
        with(teamListViewModel) {
            getTeamsByLeagueId("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            teamList.observeForever(onRenderTeamListObserver)
        }

        runBlockingTest {
            val response = fakeFailureTeamRepository.fetchTeamsByLeagueId("12")
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(teamListViewModel.isViewLoading.value)
            Assert.assertNotNull(teamListViewModel.onMessageError.value)
        }
    }


}