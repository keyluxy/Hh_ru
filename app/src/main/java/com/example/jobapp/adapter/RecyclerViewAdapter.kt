package com.example.jobapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.R
import com.example.jobapp.data.Offer

class OffersAdapter(private var offersList: List<Offer>, private val context: Context) :
    RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offersList[position]
        holder.title.text = offer.title
        offer.iconResId?.let { holder.icon.setImageResource(it) }
        holder.offerText.setText(offer.buttonText)



        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = offersList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateOffers(newOffers: List<Offer>) {
        offersList = newOffers
        notifyDataSetChanged()
    }


    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.offer_title)
        var icon: ImageView = itemView.findViewById(R.id.offer_icon)
        var offerText: TextView = itemView.findViewById(R.id.offer_text)
    }



}
