package com.androidtest.bymilanchothani

import com.androidtest.bymilanchothani.di.MockApiService
import com.androidtest.bymilanchothani.repositories.Repository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlinx.coroutines.runBlocking


@HiltAndroidTest
class RepositoryTest {
	@get:Rule
	var hiltRule = HiltAndroidRule(this)

	@Inject
	lateinit var repository: Repository

	@Before
	fun setUp() {
		hiltRule.inject()
	}

	@Test
	fun getNominationsTest() = runBlocking {
		val mockApiService = MockApiService()
		val resultData = repository.getNominationList()
		val nominationsData = mockApiService.getNomination()
		assertEquals(nominationsData, resultData)
	}

	@Test
	fun getNomineesTest() = runBlocking {
		val mockApiService = MockApiService()
		val resultData = repository.getNomineeList()
		val nomineesData = mockApiService.getNominee()
		assertEquals(nomineesData, resultData)
	}

	@Test
	fun createNominationTest() = runBlocking {
		val mockApiService = MockApiService()
		val resultData = repository.createNomination("test_nomineeId", "test_reason", "test_process")
		val nominationData = mockApiService.createNomination("test_nomineeId", "test_reason", "test_process")
		assertEquals(nominationData, resultData)
	}
}