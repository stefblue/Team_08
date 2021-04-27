package com.swt.augmentmycampus.dependencyInjection

import androidx.viewbinding.BuildConfig
import com.squareup.moshi.Moshi
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogic
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogicImpl
import com.swt.augmentmycampus.network.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

data class WebserviceConfiguration(val baseUrl: String)

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideJsonSerializer(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideWebservice(okHttpClient: OkHttpClient, moshi: Moshi, webserviceConfiguration: WebserviceConfiguration): Webservice {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(webserviceConfiguration.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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

    @Singleton
    @Provides
    fun provideUrlBusinessLogic(): UrlBusinessLogic = UrlBusinessLogicImpl()
}
