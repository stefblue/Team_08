package com.swt.augmentmycampus.dependencyInjection

import androidx.viewbinding.BuildConfig
import com.swt.augmentmycampus.network.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

data class WebserviceConfiguration(val baseUrl: String)

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideWebservice(okHttpClient: OkHttpClient, webserviceConfiguration: WebserviceConfiguration): Webservice {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(webserviceConfiguration.baseUrl)
            .build()
            .create(Webservice::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }
    }.build()
}
