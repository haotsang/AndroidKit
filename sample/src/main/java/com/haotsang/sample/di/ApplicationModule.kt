package com.haotsang.sample.di

import com.google.gson.Gson
import com.haotsang.common.format.gson.GsonFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * 全局的DI
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    //如果是自己定义的MyApplication，通过一个Module向下转型
//    @Provides
//    fun provideMyApplication(application: Application): MyApplication {
//        return application as MyApplication
//    }

    //全局的Gson  使用框架进行容错处理
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonFactory.instance.getSingletonGson()
    }

}