package com.example.firebase_realtimedatabase_studentmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_realtimedatabase_studentmanagement.R.*

class DataAdapter(
    private var data: List<Data>,
    private val editClickListener: (Data) -> Unit,
    private val deleteClickListener: (Data) -> Unit
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studid = itemView.findViewById<TextView>(R.id.idTxt)
        val name = itemView.findViewById<TextView>(R.id.nameTxt)
        val email = itemView.findViewById<TextView>(R.id.emaiTxt)
        val subject = itemView.findViewById<TextView>(R.id.subjectTxt)
        val birthdate = itemView.findViewById<TextView>(R.id.birthDateTxt)

        val edit = itemView.findViewById<ImageButton>(R.id.editBtn)
        val delete = itemView.findViewById<ImageButton>(R.id.deleteBtn)
    }

    fun updateData(newData: List<Data>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.studid.text = item.studid
        holder.name.text = item.name
        holder.email.text = item.email
        holder.subject.text = item.subject
        holder.birthdate.text = item.birthDate

        // Set click listeners for edit and delete buttons
        holder.edit.setOnClickListener {
            editClickListener(item)  // Handle edit click
        }

        holder.delete.setOnClickListener {
            deleteClickListener(item)  // Handle delete click
        }
    }
}
