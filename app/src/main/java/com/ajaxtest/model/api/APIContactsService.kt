package com.ajaxtest.model.api

import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.api.APIResWrap
import retrofit2.Response
import retrofit2.http.*

interface APIContactsService {
    @GET("api")
    suspend fun getContacts(
        @Query("results") amount: Int
    ): Response<APIResWrap>
}