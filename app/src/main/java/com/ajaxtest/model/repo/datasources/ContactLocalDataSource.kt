package com.ajaxtest.model.repo.datasources

import androidx.room.EmptyResultSetException
import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.model.repo.database.dao.ContactDao
import com.ajaxtest.utils.createDataSourceError
import com.core.repository.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

open class ContactLocalDataSource @Inject constructor(
    private val db: ContactDao
) {

    suspend fun saveAll(list: List<ContactData>) = withContext(Dispatchers.IO) {
        db.deleteAll()
        db.insertAll(list)
    }

    suspend fun update(item: ContactData) = withContext(Dispatchers.IO) {
        db.update(item)
    }

    suspend fun remove(item: ContactData) = withContext(Dispatchers.IO) {
        db.delete(item)
    }

    suspend fun getAllAsync(): Result<MutableList<ContactData>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(db.getAll())
            } catch (e: Exception) {
                Result.Error(createDataSourceError(e))
            }
        }
}