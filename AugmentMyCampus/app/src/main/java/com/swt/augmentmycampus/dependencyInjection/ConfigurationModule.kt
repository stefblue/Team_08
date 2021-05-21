package com.swt.augmentmycampus.dependencyInjection

import com.swt.augmentmycampus.network.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {
    @Singleton
    @Provides
    fun provideWebserviceConfiguration(): WebserviceConfiguration =
        WebserviceConfiguration("http://192.168.0.19:8082")
}