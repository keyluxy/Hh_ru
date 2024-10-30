package com.example.jobapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.R
import com.example.jobapp.application.App.Companion.context
import com.example.jobapp.data.Vacancy

class VacanciesAdapter(private var vacanciesList: List<Vacancy>) :
    RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacanciesList[position]
        holder.title.text = vacancy.title
        holder.company.text = vacancy.company
        holder.address.text = vacancy.address.toString()
    }

    override fun getItemCount(): Int = vacanciesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateVacancies(newVacancies: List<Vacancy>) {
        vacanciesList = newVacancies
        notifyDataSetChanged()
    }

    class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.vacancy_title)
        val company: TextView = itemView.findViewById(R.id.vacancy_company)
        val address: TextView = itemView.findViewById(R.id.vacancy_address)
    }
}

