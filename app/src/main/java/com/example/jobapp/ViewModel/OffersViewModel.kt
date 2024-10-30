import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobapp.R
import com.example.jobapp.data.Address
import com.example.jobapp.data.Experience
import com.example.jobapp.data.Offer
import com.example.jobapp.data.Salary
import com.example.jobapp.data.Vacancy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class OffersViewModel(application: Application) : AndroidViewModel(application) {
    private val _offersList = MutableLiveData<List<Offer>>()
    val offersList: LiveData<List<Offer>> get() = _offersList

    private val _vacanciesList = MutableLiveData<List<Vacancy>>()
    val vacanciesList: LiveData<List<Vacancy>> get() = _vacanciesList

    init {
        loadOffers(application)
        loadVacancies(application)
    }

    private fun loadOffers(application: Application) {
        val offers = mutableListOf<Offer>()
        val json = loadJsonFromAssets(application)
        try {
            val jsonObject = JSONObject(json)
            val offersArray: JSONArray = jsonObject.getJSONArray("offers")

            for (i in 0 until offersArray.length()) {
                val offerJson: JSONObject = offersArray.getJSONObject(i)

                if (!offerJson.has("id")) continue

                val id: String = offerJson.getString("id")
                val title: String = offerJson.getString("title")
                val link: String = offerJson.getString("link")
                val buttonJson = offerJson.optJSONObject("button")
                val buttonText = buttonJson?.optString("text", null.toString())

                val iconResId = when (id) {
                    "near_vacancies" -> R.drawable.ic_avatar
                    "level_up_resume" -> R.drawable.ic_avatar2
                    "temporary_job" -> R.drawable.ic_avatar3
                    else -> R.drawable.ic_avatar
                }

                offers.add(Offer(id, title, link, iconResId, buttonText))
            }
            _offersList.value = offers
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun loadVacancies(application: Application) {
        val vacancies = mutableListOf<Vacancy>()
        val json = loadJsonFromAssets(application)
        try {
            val jsonObject = JSONObject(json)
            val vacanciesArray: JSONArray = jsonObject.getJSONArray("vacancies")

            for (i in 0 until vacanciesArray.length()) {
                val vacancyJson: JSONObject = vacanciesArray.getJSONObject(i)

                val id: String = vacancyJson.getString("id")
                val lookingNumber: Int = vacancyJson.getInt("lookingNumber")
                val title: String = vacancyJson.getString("title")
                val addressJson = vacancyJson.getJSONObject("address")
                val address = Address(
                    town = addressJson.getString("town"),
                    street = addressJson.getString("street"),
                    house = addressJson.getString("house")
                )
                val company: String = vacancyJson.getString("company")
                val experienceJson = vacancyJson.getJSONObject("experience")
                val experience = Experience(
                    previewText = experienceJson.getString("previewText"),
                    text = experienceJson.getString("text")
                )
                val publishedDate: String = vacancyJson.getString("publishedDate")
                val salaryJson = vacancyJson.getJSONObject("salary")
                val salary = Salary(
                    full = salaryJson.getString("full"),
                    short = salaryJson.optString("short", null)
                )
                val schedules = mutableListOf<String>()
                val schedulesArray = vacancyJson.getJSONArray("schedules")
                for (j in 0 until schedulesArray.length()) {
                    schedules.add(schedulesArray.getString(j))
                }
                val appliedNumber: Int = vacancyJson.getInt("appliedNumber")
                val description: String = vacancyJson.getString("description")
                val responsibilities: String = vacancyJson.getString("responsibilities")

                val questions = mutableListOf<String>()
                val questionsArray = vacancyJson.getJSONArray("questions")
                for (j in 0 until questionsArray.length()) {
                    questions.add(questionsArray.getString(j))
                }

                val isFavorite: Boolean = vacancyJson.optBoolean("isFavorite", false)

                vacancies.add(Vacancy(
                    id, lookingNumber, title, address, company, experience,
                    publishedDate, salary, schedules, appliedNumber, description, responsibilities, questions, isFavorite
                ))
            }
            _vacanciesList.value = vacancies
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("VACANCIES", "VACANCIES = ${vacancies}}" )
        }
    }


    private fun loadJsonFromAssets(application: Application): String? {
            return try {
                val inputStream: InputStream = application.assets.open("data.json")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, Charsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }




}
