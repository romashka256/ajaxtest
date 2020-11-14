package com.ajaxtest.di

import com.ajaxtest.view.ContactDetailsFragment
import com.ajaxtest.view.ContactsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ContactsFragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeContactsFragment(): ContactsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeContactDetailsFragment(): ContactDetailsFragment

}