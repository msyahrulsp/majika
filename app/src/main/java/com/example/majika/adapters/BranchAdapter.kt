package com.example.majika.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.models.Branch

class BranchAdapter(val context: Context, val branchList: List<Branch>): RecyclerView.Adapter<BranchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var branchName: TextView
        var addressName: TextView
        var phoneNumber: TextView
        var mapButton: Button

        init {
            branchName = itemView.findViewById(R.id.branchName)
            addressName = itemView.findViewById(R.id.addressName)
            phoneNumber = itemView.findViewById(R.id.phoneNumber)
            mapButton = itemView.findViewById(R.id.mapButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.branch_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.branchName.text = branchList[position].name
        holder.addressName.text = branchList[position].address
        holder.phoneNumber.text = branchList[position].phone_number
        holder.mapButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${branchList[position].latitude},${branchList[position].longitude}(${branchList[position].name})")
            val mapIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
    }
}