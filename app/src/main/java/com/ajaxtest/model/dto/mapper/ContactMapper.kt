package com.ajaxtest.model.dto.mapper

import com.ajaxtest.model.dto.api.APIContactData
import com.ajaxtest.model.dto.api.APIName
import com.ajaxtest.model.dto.api.APIPicture
import com.ajaxtest.model.dto.api.APIResWrap
import com.ajaxtest.model.dto.processed.Name
import com.ajaxtest.model.dto.processed.Picture
import com.ajaxtest.model.dto.processed.ContactData

fun mapContact(contact: APIContactData): ContactData {
    return ContactData(
        null,
        mapName(contact.name),
        contact.email ?: "No email",
        mapPicture(contact.pic)
    )
}

fun mapName(name: APIName?): Name {
    name?.let {
        return Name(
            it.title ?: "No name",
            it.first ?: "No name",
            it.last ?: "No name",
        )
    }

    return mapName(APIName())
}

fun mapPicture(pic: APIPicture?): Picture {
    pic?.let {
        return Picture(
            it.medium
        )
    }

    return mapPicture(APIPicture())
}

fun mapContactList(contacts: List<APIContactData>?): MutableList<ContactData> {
    val res = mutableListOf<ContactData>()
    contacts?.let {
        for (contact in it) {
            res.add(mapContact(contact))
        }
    }

    return res
}

fun mapAPIResWrap(res: APIResWrap): MutableList<ContactData> {
    return mapContactList(res.contacts)
}