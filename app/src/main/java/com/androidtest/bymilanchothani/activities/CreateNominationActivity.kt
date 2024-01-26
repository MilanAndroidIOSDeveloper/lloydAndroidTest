package com.androidtest.bymilanchothani.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidtest.bymilanchothani.R
import com.androidtest.bymilanchothani.adapters.NomineeSpinnerAdapter
import com.androidtest.bymilanchothani.databinding.ActivityCreatenominationBinding
import com.androidtest.bymilanchothani.models.Nominee
import com.androidtest.bymilanchothani.utilities.Utility
import com.androidtest.bymilanchothani.viewmodels.CreateNominationViewModel
import com.androidtest.bymilanchothani.viewmodels.NominationViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateNominationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatenominationBinding
    private lateinit var viewModel: NominationViewModel
    private lateinit var createviewModel: CreateNominationViewModel
    private lateinit var selectedItemId: String
    private lateinit var selectedItem: String
    private lateinit var etReasoningValue: String
    private var selectedProcessValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatenominationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListner()
        setupViewModel()
        observeNomineeList()

    }

    private fun setUpListner() {
        binding.backButton.setOnClickListener {
            showBackBottomSheet()
        }

        binding.etReasoning.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                validateAndToggleSubmitButton()
            }
        })
        binding.rgProcess.setOnCheckedChangeListener { _, checkedId ->
            selectedProcessValue = when (checkedId) {
                R.id.rb_very_unfair -> getString(R.string.very_unfair_)
                R.id.rb_unfair -> getString(R.string.unfair_)
                R.id.rb_not_sure -> getString(R.string.not_sure_)
                R.id.rb_fair -> getString(R.string.fair_)
                R.id.rb_very_fair -> getString(R.string.very_fair_)
                else -> ""
            }
            validateAndToggleSubmitButton()
        }

        binding.submitButton.setOnClickListener {

            if (!Utility.NetworkUtils.isInternetAvailable(this)) {
                Utility.showNoInternetDialog(this@CreateNominationActivity)
                return@setOnClickListener
            }

            if (selectedItem.isEmpty()) {
                Utility.ToastUtils.showToast(
                    this@CreateNominationActivity,
                    getString(R.string.please_select_valid_nominee)
                )
                return@setOnClickListener
            }


            etReasoningValue = binding.etReasoning.text.toString()
            if (etReasoningValue.isEmpty()) {
                Utility.ToastUtils.showToast(
                    this@CreateNominationActivity,
                    getString(R.string.please_input_your_valid_reason)
                )
                return@setOnClickListener
            }

            if (selectedProcessValue.isEmpty()) {
                Utility.ToastUtils.showToast(
                    this@CreateNominationActivity,
                    getString(R.string.please_select_valid_cube_of_month_fair)
                )
                return@setOnClickListener
            }



            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread { binding.progressBar.visibility = View.VISIBLE }
                val createdNomination = createviewModel.createNomination(
                    nomineeId = selectedItemId,
                    reason = etReasoningValue,
                    process = selectedProcessValue
                )

                createdNomination?.let {
                    val intent = Intent(
                        this@CreateNominationActivity,
                        NominationSubmittedActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                } ?: run {

                    runOnUiThread {
                        Utility.ToastUtils.showToast(
                            this@CreateNominationActivity,
                            getString(R.string.failed_to_post_nomination)
                        )
                    }


                }
                runOnUiThread { binding.progressBar.visibility = View.GONE }
            }


        }

    }


    private fun showBackBottomSheet() {
        val btnSheet = layoutInflater.inflate(R.layout.back_bottomsheet, null)
        val dialog = BottomSheetDialog(this@CreateNominationActivity)
        dialog.setContentView(btnSheet)

        val bottomSheetLeaveButton = btnSheet.findViewById<Button>(R.id.leave_button)
        val bottomSheetCancelButton = btnSheet.findViewById<Button>(R.id.cancel_button)

        bottomSheetLeaveButton.setOnClickListener {
            finish()
        }

        bottomSheetCancelButton.setOnClickListener {
            dialog.hide()
        }

        dialog.show()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this@CreateNominationActivity)[NominationViewModel::class.java]
        createviewModel =
            ViewModelProvider(this@CreateNominationActivity)[CreateNominationViewModel::class.java]
    }


    private fun observeNomineeList() {
        if (!Utility.NetworkUtils.isInternetAvailable(this)) {
            Utility.showNoInternetDialog(this@CreateNominationActivity)
            return
        }
        viewModel.getNomineeList().observe(this) { nominees ->
            val adapter =
                NomineeSpinnerAdapter(this, android.R.layout.simple_spinner_item, nominees)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter.insert(Nominee("", getString(R.string.select_option_hint), ""), 0)

            binding.nomineeSp.adapter = adapter
            binding.nomineeSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        selectedItem = parentView?.getItemAtPosition(position).toString()
                        selectedItemId = nominees[position].nomineeId
                    }
                    validateAndToggleSubmitButton()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                }
            }

        }
    }

    private fun validateAndToggleSubmitButton() {
        val isSpinnerSelected = binding.nomineeSp.selectedItemPosition != 0
        val isEditTextNotEmpty = binding.etReasoning.text!!.isNotEmpty()
        val isRadioButtonSelected = binding.rgProcess.checkedRadioButtonId != -1

        binding.submitButton.isEnabled =
            isSpinnerSelected && isEditTextNotEmpty && isRadioButtonSelected
    }
}
