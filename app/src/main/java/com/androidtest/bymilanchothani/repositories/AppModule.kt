package com.androidtest.bymilanchothani.repositories

import com.androidtest.bymilanchothani.BuildConfig
import com.androidtest.bymilanchothani.api.ApiService
import com.androidtest.bymilanchothani.api.AuthTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Singleton
	@Provides
	fun provideApi(): ApiService = OkHttpClient().newBuilder().apply {
		addInterceptor(AuthTokenInterceptor())
	}.build().let {
		Retrofit.Builder()
			.client(it)
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BuildConfig.API_URL).build().create(ApiService::class.java)
	}

	@Singleton
	@Provides
	fun provideRepository(api: ApiService): Repository = Repository(api)
}