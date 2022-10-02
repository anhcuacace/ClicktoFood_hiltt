package com.tunanh.clicktofood_hilt.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.tunanh.clicktofood_hilt.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase( @ApplicationContext appContext: Context): LocalDatabase {
        return Room
            .databaseBuilder(
                appContext,
                LocalDatabase::class.java,
                "localDb"
            )
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase) =
        db.userDao()

    @Singleton
    @Provides
    fun provideFoodDao(db: LocalDatabase) =
        db.foodDao()

    @Singleton
    @Provides
    fun provideHistorySearchDao(db: LocalDatabase) =
        db.historySearchDao()

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

}