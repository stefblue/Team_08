package com.swt.augmentmycampus.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.viewbinding.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.swt.augmentmycampus.ui.LocaleManager
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.DataBusinessLogicImpl
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogic
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogicImpl
import com.swt.augmentmycampus.network.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
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

    @Singleton
    @Provides
    fun provideDataBusinessLogic(urlBusinessLogic: UrlBusinessLogic, webservice: Webservice): DataBusinessLogic = DataBusinessLogicImpl(urlBusinessLogic, webservice)

    @Singleton
    @Provides
    fun provideLocalManager(@ApplicationContext context: Context): LocaleManager = LocaleManager(context)
}
