package com.ajaxtest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajaxtest.R
import com.ajaxtest.model.dto.processed.ContactData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter constructor(
    val list: List<ContactData>,
    val context: Context,
    val listener: OnContactClickedListener
) :
    RecyclerView.Adapter<ContactsAdapter.ContactsAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapterVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)

        return ContactsAdapterVH(view)
    }

    override fun onBindViewHolder(holder: ContactsAdapterVH, position: Int) {
        val contact = list[position]

        Glide.with(context)
            .load(contact.pic.medium)
            .circleCrop()
            .into(holder.picIV)

        holder.nameTV.text = "${contact.name.first} ${contact.name.last}"
        holder.emailTV.text = contact.email

        holder.deleteIV.setOnClickListener {
            listener.onDeleteClicked(position, contact)
        }

        holder.itemView.setOnClickListener {
            listener.onClicked(position, contact)
        }
    }

    override fun getItemCount(): Int = list.size

    class ContactsAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picIV = itemView.item_contact_iv
        val nameTV = itemView.item_contact_name
        val emailTV = itemView.item_contact_email
        val deleteIV = itemView.item_contact_delete
    }
}

interface OnContactClickedListener {
    fun onClicked(pos: Int, contact: ContactData)
    fun onDeleteClicked(pos: Int, contact: ContactData)
}