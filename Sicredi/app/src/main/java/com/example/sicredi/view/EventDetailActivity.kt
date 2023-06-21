package com.example.sicredi.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sicredi.databinding.FragmentEventDetailsBinding
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.example.sicredi.model.Event

class EventDetailActivity : AppCompatActivity() {
    lateinit var binding: FragmentEventDetailsBinding

    private lateinit var address: String
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val event = intent.getSerializableExtra("event") as Event
            setLayoutTexts(event)

        binding.checkinButton.setOnClickListener {
            setCheckinButtonAction(event)
        }
    }

    private fun setCheckinButtonAction(event: Event) {
      val bottomSheet = CheckinBottomSheetFragment.newInstance(event)
          .show(supportFragmentManager,"bottomSheet")
    }

    private fun setLayoutTexts(event: Event) {
        val format = NumberFormat.getCurrencyInstance()
        val timestamp = try {
            Timestamp(event.date.toLong())
        } catch (e:Exception){
            null
        }
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("E, HH:mm", Locale.getDefault())
        val latitude = event.latitude
        val longitude = event.longitude
        val geocoder = Geocoder(this, Locale.getDefault())
        var location: Address? = null
        try {
            location = geocoder.getFromLocation(latitude, longitude, 1)?.get(0)
        } catch (e: Exception) {
            Toast.makeText(this, "Ocorreu um erro ao buscar o endereço", Toast.LENGTH_LONG).show()
        }
        Glide.with(binding.cardImage.context).load(event.imageUrl).into(binding.cardImage)
        format.currency = Currency.getInstance("BRL")

        date = timestamp?.let { dateFormat.format(timestamp) } ?: ""

        binding.detailsPrice.text = format.format(event.price)
        binding.detailsTitle.text = event.title
        binding.detailsDescription.text = event.description
        binding.detailsDateText.text = date
        binding.detailsTimeText.text = timestamp?.let { dateFormat.format(timestamp) } ?: ""

        if (location != null) {
            if (location.featureName != null) {
                address = location.getAddressLine(0)
                binding.detailsLocationText.text = location.featureName
                binding.detailsAddresText.text = address
            } else {
                binding.detailsLocationText.text = location.getAddressLine(0)
                binding.detailsAddresText.text = ""
            }
        } else {
            binding.detailsLocationText.text = "Localização temporariamente indisponível"
            binding.detailsAddresText.text = ""
        }
    }

}