package com.example.jobapp

import OffersViewModel
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.adapter.OffersAdapter
import com.example.jobapp.adapter.VacanciesAdapter
import com.example.jobapp.data.Vacancy

class MainActivity : AppCompatActivity() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var offersAdapter: OffersAdapter
    private lateinit var offersViewModel: OffersViewModel

    private lateinit var vacanciesRecyclerView: RecyclerView
    private lateinit var vacanciesAdapter: VacanciesAdapter
    private lateinit var moreButton: Button
    private var allVacancies: List<Vacancy> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация RecyclerView для предложений
        offersRecyclerView = findViewById(R.id.recomendation)
        offersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        offersAdapter = OffersAdapter(emptyList(), this)
        offersRecyclerView.adapter = offersAdapter

        // Инициализация ViewModel
        offersViewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        offersViewModel.offersList.observe(this, Observer { offers ->
            offersAdapter.updateOffers(offers)
        })

        // Инициализация RecyclerView и кнопки для вакансий
        vacanciesRecyclerView = findViewById(R.id.vacancies_recycler_view)
        moreButton = findViewById(R.id.moreButton)

        vacanciesAdapter = VacanciesAdapter(emptyList())
        vacanciesRecyclerView.layoutManager = LinearLayoutManager(this)
        vacanciesRecyclerView.adapter = vacanciesAdapter

        offersViewModel.vacanciesList.observe(this, Observer { vacancies ->
            allVacancies = vacancies
            displayInitialVacancies()
            setupMoreButton()
        })
    }

    private fun displayInitialVacancies() {
        if (allVacancies.isNotEmpty()) {
            val initialVacancies = allVacancies.take(3)
            vacanciesAdapter.updateVacancies(initialVacancies)
            vacanciesRecyclerView.visibility = View.VISIBLE
        } else {
            vacanciesRecyclerView.visibility = View.GONE
        }
    }


    private fun setupMoreButton() {
        val remainingCount = allVacancies.size - 3
        if (remainingCount > 0) {
            moreButton.text = "Еще $remainingCount вакансий"
            moreButton.visibility = View.VISIBLE
        } else {
            moreButton.visibility = View.GONE
        }

        moreButton.setOnClickListener {
            vacanciesAdapter.updateVacancies(allVacancies)
            moreButton.visibility = View.GONE
        }
    }
}
