package com.ajaxtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ajaxtest.R
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.model.dto.processed.Name
import com.ajaxtest.viewmodel.ContactsViewModel
import com.ajaxtest.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ContactDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @ExperimentalCoroutinesApi
    val contactsViewModel: ContactsViewModel by navGraphViewModels(R.id.mainFragment) {
        viewModelFactory
    }

    lateinit var contact: ContactData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            contact = it.getParcelable(contactKey)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, null)

        Glide.with(requireContext())
            .load(contact.pic.medium)
            .into(view.fragment_details_pic)

        bind(view, contact)

        view.fragment_details_delete.setOnClickListener {
            contactsViewModel.onDelete(contact)
            findNavController().navigateUp()
        }

        view.fragment_details_update.setOnClickListener {
            val name = Name(
                contact.name.title,
                view.fragment_details_name.text.toString(),
                view.fragment_details_lastname.text.toString()
            )

            contactsViewModel.onUpdate(
                ContactData(
                    contact.id,
                    name,
                    view.fragment_details_email.text.toString(),
                    contact.pic
                )
            )
        }

        view.fragment_details_cancel.setOnClickListener {
            bind(view, contact)
            contactsViewModel.onUpdate(contact)
        }

        return view
    }

    fun bind(view: View, contact: ContactData) {
        view.fragment_details_name.setText(contact.name.first)
        view.fragment_details_lastname.setText(contact.name.last)
        view.fragment_details_email.setText(contact.email)

    }

    companion object {
        val contactKey = "ContactData"

        fun getBundle(contact: ContactData): Bundle {
            return bundleOf(contactKey to contact)
        }
    }
}