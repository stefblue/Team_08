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

    public val endpoint = "http://192.168.0.19:8082";

    @Singleton
    @Provides
    fun provideWebserviceConfiguration(): WebserviceConfiguration =
        WebserviceConfiguration(endpoint)

}