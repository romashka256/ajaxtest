package com.ajaxtest.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.model.repo.ContactsRepository
import com.ajaxtest.utils.createDataSourceError
import com.core.base.viewmodel.BaseViewModel
import com.core.repository.network.launchSafe
import com.core.repository.repository.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ContactsViewModel @Inject constructor(
    val contactsRepository: ContactsRepository,
    val preferences: SharedPreferences
) :
    BaseViewModel() {

    val contactsState = MutableLiveData<Result<List<ContactData>>>()

    val onItemsUpdate = MutableLiveData<Unit>()

    private var contacts: MutableList<ContactData> = mutableListOf()

    fun onCreate() {
        loadContacts()
    }

    fun loadContacts(refresh: Boolean? = null) {
        launchSafe(::handleContactsError) {
            contactsRepository.getContacts(this, refresh ?: checkFirstRun()).collectLatest {
                contactsState.value = it

                it.check({

                }, {
                    contacts = it
                }, {

                })
            }
        }
    }


    fun onDelete(contact: ContactData) {
        launchSafe(::handleDeleteError) {
            contactsRepository.deleteContact(contact).collectLatest {
                it.check({
                    // loading
                }, {
                    contacts.remove(contact)
                    updateContacts()
                }, {
                    // error
                })
            }
        }
    }

    fun onUpdate(
        contact: ContactData
    ) {
        launchSafe(::handleUpdateError) {
            contactsRepository.updateContact(contact).collectLatest {
                it.check({
                    // loading
                }, {
                    for ((index, item) in contacts.withIndex()) {
                        if (item.id == contact.id)
                            contacts[index] = contact
                    }

                    updateContacts()
                }, {
                    // error
                })
            }
        }
    }

    fun updateContacts() {
        onItemsUpdate.value = Unit
    }

    fun checkFirstRun(): Boolean {
        return if (preferences.getBoolean("first_run", true)) {
            preferences.edit().putBoolean("first_run", false).apply();
            true;
        } else {
            false;
        }
    }

    fun handleContactsError(throwable: Throwable) {
        contactsState.value = Result.Error(createDataSourceError(throwable))
    }

    fun handleDeleteError(throwable: Throwable) {
        contactsState.value = Result.Error(createDataSourceError(throwable))
    }

    fun handleUpdateError(throwable: Throwable) {
        contactsState.value = Result.Error(createDataSourceError(throwable))
    }
}