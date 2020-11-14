package com.ajaxtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajaxtest.R
import com.ajaxtest.model.dto.processed.ContactData
import com.ajaxtest.viewmodel.ContactsViewModel
import com.ajaxtest.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ContactsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val contactsViewModel: ContactsViewModel by navGraphViewModels(R.id.mainFragment) {
        viewModelFactory
    }

    lateinit var adapter: ContactsAdapter
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactsViewModel.onCreate()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, null)

        initRV(view)

        contactsViewModel.contactsState.observe(viewLifecycleOwner, Observer {
            it.check({
                view.fragment_main_progress.visibility = View.VISIBLE
            }, { contacts ->
                view.fragment_main_progress.visibility = View.INVISIBLE

                adapter =
                    ContactsAdapter(contacts, requireContext(), object : OnContactClickedListener {
                        override fun onClicked(pos: Int, contact: ContactData) {
                            findNavController().navigate(
                                R.id.action_mainFragment_to_contactDetailsFragment,
                                ContactDetailsFragment.getBundle(contact)
                            )
                        }

                        override fun onDeleteClicked(pos: Int, contact: ContactData) {
                            contactsViewModel.onDelete(contact)
                        }
                    })

                rv.adapter = adapter
            }, { error ->
                view.fragment_main_progress.visibility = View.INVISIBLE

                error.getContentIfNotHandled()?.let {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    Toast.makeText(
                        requireContext(),
                        "Нажмите обновить список чтобы загрузить контакты",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        })

        contactsViewModel.onItemsUpdate.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        view.fragment_main_refresh.setOnClickListener {
            contactsViewModel.loadContacts(true)
        }

        return view
    }

    fun initRV(view: View) {
        rv = view.fragment_main_contacts

        rv.layoutManager = LinearLayoutManager(requireContext())
    }

}