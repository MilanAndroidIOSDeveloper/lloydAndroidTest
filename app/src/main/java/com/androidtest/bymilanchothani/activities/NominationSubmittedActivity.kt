package com.androidtest.bymilanchothani.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidtest.bymilanchothani.databinding.ActivityNominationSubmittedBinding

class NominationSubmittedActivity : AppCompatActivity() {
	private lateinit var binding: ActivityNominationSubmittedBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityNominationSubmittedBinding.inflate(layoutInflater)
		setContentView(binding.root)

		populateUI()
	}

	private fun populateUI() {
		binding.backButton.setOnClickListener {

			finish()
		}
		binding.createButton.setOnClickListener {
			val intent = Intent(this, CreateNominationActivity::class.java)
			startActivity(intent)
			finish()
		}
	}
}