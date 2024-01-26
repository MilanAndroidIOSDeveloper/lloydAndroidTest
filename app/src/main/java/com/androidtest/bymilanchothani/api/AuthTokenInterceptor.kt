package com.androidtest.bymilanchothani.api

import okhttp3.Interceptor
import okhttp3.Response
import com.androidtest.bymilanchothani.BuildConfig

class AuthTokenInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		var request = chain.request()

		if (request.headers("No-Authentication").isEmpty()) {
			val finalToken = "Bearer ${BuildConfig.AUTH_TOKEN}"
			request = request.newBuilder().addHeader("Authorization", finalToken).build()
		}
		return chain.proceed(request)
	}
}