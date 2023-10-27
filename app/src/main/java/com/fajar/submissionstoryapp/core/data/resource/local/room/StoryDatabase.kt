package com.fajar.submissionstoryapp.core.data.resource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Story::class, KeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun StoryDao(): StoryDao
    abstract fun KeysDao(): KeysDao

    companion object {
        @Volatile private var instance: StoryDatabase? = null

        fun getDatabase(context: Context): StoryDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, StoryDatabase::class.java, "story")
                .fallbackToDestructiveMigration()
                .build()
    }
}