package com.ajaxtest.model.repo.database.dao

import androidx.room.*
import com.ajaxtest.model.dto.processed.ContactData

@Dao
interface ContactDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ContactData>)

    @Delete
    suspend fun delete(item: ContactData)

    @Update
    suspend fun update(item: ContactData)

    @Query("SELECT * FROM ContactData")
    suspend fun getAll() : MutableList<ContactData>

    @Query("DELETE FROM ContactData")
    fun deleteAll()
}