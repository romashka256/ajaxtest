package com.ajaxtest.model.repo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.model.repo.database.dao.ContactDao
import java.util.*


@Database(entities = [ContactData::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}


class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}