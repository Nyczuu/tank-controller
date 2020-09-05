package com.example.tank_controller.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface SettingsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(settingsDbModel: SettingsModelDb)

    @get:Query("select * from SettingsModelDb where id =0")
    val settingsLiveData: LiveData<SettingsModelDb?>
}

@Database(entities = [SettingsModelDb::class], version = 1, exportSchema = false)
abstract class SettingsDatabase : RoomDatabase(){
    abstract val settingsDao: SettingsDao
}

private lateinit var INSTANCE: SettingsDatabase

fun getDatabase(context:Context): SettingsDatabase {
    synchronized(SettingsDatabase::class){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    SettingsDatabase::class.java,
                    "settings_db"
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
