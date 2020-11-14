package com.ajaxtest.model.repo.datasources

import com.ajaxtest.model.api.APIContactsService
import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.api.APIResWrap
import com.ajaxtest.model.dto.mapper.mapAPIResWrap
import com.ajaxtest.model.dto.mapper.mapContactList
import com.ajaxtest.model.dto.processed.ContactData
import com.core.repository.repository.Result
import com.core.repository.repository.convertAndMap
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import javax.inject.Inject


@ExperimentalCoroutinesApi
class ContactRemoteDataSource @Inject constructor(
    private val contactService: APIContactsService
) {
    suspend fun loadContacts(): Result<MutableList<ContactData>> {
        return contactService.getContacts(20).convertAndMap(::mapAPIResWrap)
    }
}
