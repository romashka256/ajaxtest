package com.ajaxtest.di

import android.app.DownloadManager
import android.content.Context
import android.app.NotificationManager
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.core.base.usecases.ErrorHandleInterceptor
import com.core.base.util.CameraManager
import com.core.base.util.FileManager
import com.core.repository.network.HeaderInterceptor
import com.core.repository.network.NoInternetInterceptor
import com.google.gson.GsonBuilder
import com.ajaxtest.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import javax.inject.Named
import android.location.Geocoder
import com.ajaxtest.model.api.APIContactsService
import com.ajaxtest.model.repo.database.AppDatabase
import com.ajaxtest.model.repo.database.dao.ContactDao

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, Constants.databaseName
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAPIContactsService(retrofit: Retrofit): APIContactsService =
        retrofit.create(APIContactsService::class.java)


    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideHttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("SHARED_PREFS_NAME", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClientWithLogging(
        cache: Cache,
        context: Context
    ): OkHttpClient {
        val client = OkHttpClient.Builder()

        with(client) {
            addInterceptor(NoInternetInterceptor(context))
            addInterceptor(HeaderInterceptor())
            addInterceptor(ErrorHandleInterceptor())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }

        return client.cache(cache).build()
    }

    @Provides
    @Singleton
    internal fun provideContactDao(db: AppDatabase): ContactDao = db.contactDao()
}
