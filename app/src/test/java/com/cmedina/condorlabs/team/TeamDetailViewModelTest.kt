package com.cmedina.condorlabs.team


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.viewmodel.TeamDetailViewModel
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
class TeamDetailViewModelTest {


    @Mock
    private lateinit var context: Application
    private lateinit var teamDetailViewModel: TeamDetailViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var onRenderTeamObserver: Observer<Team>
    private lateinit var onRenderEventListObserver: Observer<List<Event>>
    private lateinit var team: Team
    private lateinit var eventList: List<Event>

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
        onRenderTeamObserver = Mockito.mock(Observer::class.java) as Observer<Team>
        onRenderEventListObserver = Mockito.mock(Observer::class.java) as Observer<List<Event>>
    }

    private fun mockData() {
        team = fakeSuccessTeamRepository.mockList.first()
        eventList = fakeSuccessTeamRepository.eventMockList
    }


    @Test
    fun `fetch team detail with ViewModel and Repository returns successful data`() {
        teamDetailViewModel = TeamDetailViewModel(fakeSuccessTeamRepository)
        with(teamDetailViewModel) {
            getTeamDetail("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            teamData.observeForever(onRenderTeamObserver)
        }

        runBlockingTest {
            val response = fakeSuccessTeamRepository.fetchTeamDetailsById("12")
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(teamDetailViewModel.isViewLoading.value)
            Assert.assertTrue(teamDetailViewModel.teamData.value != null)
        }
    }


    @Test
    fun `fetch team detail with ViewModel and Repository returns an error`() {
        teamDetailViewModel = TeamDetailViewModel(fakeFailureTeamRepository)
        with(teamDetailViewModel) {
            getEventsByTeamId("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            teamData.observeForever(onRenderTeamObserver)
        }

        runBlockingTest {
            val response = fakeFailureTeamRepository.fetchTeamDetailsById("12")
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(teamDetailViewModel.isViewLoading.value)
            Assert.assertNotNull(teamDetailViewModel.onMessageError.value)
        }
    }


    @Test
    fun `fetch event list with ViewModel and Repository returns successful data`() {
        teamDetailViewModel = TeamDetailViewModel(fakeSuccessTeamRepository)
        with(teamDetailViewModel) {
            getEventsByTeamId("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            eventList.observeForever(onRenderEventListObserver)
        }

        runBlockingTest {
            val response = fakeSuccessTeamRepository.fetchEventsByTeamId("12")
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(teamDetailViewModel.isViewLoading.value)
            Assert.assertTrue(teamDetailViewModel.eventList.value?.size == 2)
        }
    }


    @Test
    fun `fetch event list with ViewModel and Repository returns an error`() {
        teamDetailViewModel = TeamDetailViewModel(fakeFailureTeamRepository)
        with(teamDetailViewModel) {
            getEventsByTeamId("12")
            isViewLoading.observeForever(isViewLoadingObserver)
            eventList.observeForever(onRenderEventListObserver)
        }

        runBlockingTest {
            val response = fakeFailureTeamRepository.fetchEventsByTeamId("12")
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(teamDetailViewModel.isViewLoading.value)
            Assert.assertNotNull(teamDetailViewModel.onMessageError.value)
        }
    }



}