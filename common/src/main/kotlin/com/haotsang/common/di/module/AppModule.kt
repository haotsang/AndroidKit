package com.haotsang.common.di.module

import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.service.media.MediaBrowserService
import com.google.gson.Gson
import com.haotsang.common.format.gson.GsonFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideContext(@ApplicationContext context: Context): Context


    companion object {

        @Singleton
        @Provides
        fun provideResource(@ApplicationContext context: Context): Resources {
            return context.resources
        }

        @Singleton
        @Provides
        fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager? {
            return context.getSystemService(MediaBrowserService.NOTIFICATION_SERVICE) as NotificationManager?
        }

        //全局的Gson  使用框架进行容错处理
        @Provides
        @Singleton
        fun provideGson(): Gson {
            return GsonFactory.instance.getSingletonGson()
        }
    }
}