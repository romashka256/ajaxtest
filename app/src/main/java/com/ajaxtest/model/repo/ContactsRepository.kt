package com.ajaxtest.model.repo

import com.core.repository.repository.Repository
import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.mapper.mapContactList
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.model.repo.database.AppDatabase
import com.ajaxtest.model.repo.datasources.ContactLocalDataSource
import com.ajaxtest.model.repo.datasources.ContactRemoteDataSource
import com.ajaxtest.utils.createDataSourceError
import com.core.repository.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Extension repository
 */

@ExperimentalCoroutinesApi
@Singleton
class ContactsRepository @Inject constructor(
    val remoteDataSource: ContactRemoteDataSource,
    val localDataSource: ContactLocalDataSource
) {
    private var _contactsFlow = MutableStateFlow<Result<MutableList<ContactData>>>(Result.Loading)
    val contactsFlow = _contactsFlow

    suspend fun getContacts(
        scope: CoroutineScope,
        load: Boolean
    ): StateFlow<Result<MutableList<ContactData>>> {
        _contactsFlow.value = Result.Loading

        if (load) {
            remoteDataSource.loadContacts().check({
                //loading
            }, {
                scope.launch {
                    localDataSource.saveAll(it)

                    _contactsFlow.value = localDataSource.getAllAsync()
                }
            }, {
                //error
            })
        } else {
            _contactsFlow.value = localDataSource.getAllAsync()
        }

        return contactsFlow
    }

    private var _deleteFlow = MutableStateFlow<Result<Unit>>(Result.Loading)
    val deleteFlow = _deleteFlow

    suspend fun deleteContact(
        contactData: ContactData
    ): StateFlow<Result<Unit>> {
        _deleteFlow.value = Result.Loading

        try {
            localDataSource.remove(contactData)

            _deleteFlow.value = Result.Success(Unit)
        } catch (e: Exception) {
            _deleteFlow.value = Result.Error(createDataSourceError(e))
        }


        return deleteFlow
    }

    private var _updateFlow = MutableStateFlow<Result<Unit>>(Result.Loading)
    val updateFlow = _updateFlow

    suspend fun updateContact(
        contactData: ContactData
    ): StateFlow<Result<Unit>> {
        _deleteFlow.value = Result.Loading

        try {
            localDataSource.update(contactData)

            _updateFlow.value = Result.Success(Unit)
        } catch (e: Exception) {
            _updateFlow.value = Result.Error(createDataSourceError(e))
        }

        return updateFlow
    }
}