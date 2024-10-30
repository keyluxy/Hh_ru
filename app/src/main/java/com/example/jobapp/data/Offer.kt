package com.example.jobapp.data


data class Offer(
    val id: String,
    val title: String,
    val link: String,
    val iconResId: Int,
    val buttonText: String?
)



data class Vacancy(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String,
    val responsibilities: String,
    val questions: List<String>,
    val isFavorite: Boolean = false
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)

data class Experience(
    val previewText: String,
    val text: String
)

data class Salary(
    val full: String,
    val short: String? = null
)



