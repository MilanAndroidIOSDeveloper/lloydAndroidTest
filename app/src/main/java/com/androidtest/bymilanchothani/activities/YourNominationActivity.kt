package com.androidtest.bymilanchothani.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.bymilanchothani.adapters.NominationAdapter
import com.androidtest.bymilanchothani.databinding.ActivityYournominationBinding
import com.androidtest.bymilanchothani.viewmodels.NominationViewModel
import com.androidtest.bymilanchothani.viewmodels.YourNominationViewModel
import com.cube.cubeacademy.lib.dao.NomineeDatabase
import com.androidtest.bymilanchothani.utilities.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YourNominationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYournominationBinding
    private lateinit var viewModel: YourNominationViewModel
    private lateinit var viewModelNominee: NominationViewModel
    private lateinit var adapter: NominationAdapter
    private lateinit var database: NomineeDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYournominationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = NomineeDatabase.getDatabase(this@YourNominationActivity)

        setupViewModel()
        setupRecyclerView()
        observeNominationList()

        binding.createButton.setOnClickListener {
            val intent = Intent(this, CreateNominationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        observeNominationList()
    }

    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this@YourNominationActivity).get(YourNominationViewModel::class.java)
        viewModelNominee =
            ViewModelProvider(this@YourNominationActivity).get(NominationViewModel::class.java)
        viewModelNominee.getNomineeList().observe(this) {}

    }

    private fun setupRecyclerView() {
        adapter = NominationAdapter()
        binding.nominationsList.adapter = adapter
        binding.nominationsList.layoutManager = LinearLayoutManager(this)
    }

    private fun observeNominationList() {
        if (!Utility.NetworkUtils.isInternetAvailable(this)) {
            Utility.showNoInternetDialog(this@YourNominationActivity)
            return
        }

        viewModel.getNominationList().observe(this) { nominations ->

            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread { binding.progressBar.visibility = View.VISIBLE }
                for (nomination in nominations) {

                    database.nomineeDao().getAllNominees()

                    val nomineeNames =
                        database.nomineeDao().getNomineeNameById(nomination.nomineeId)
                    try {
                        nomination.nomineeName =
                            nomineeNames.firstName + " " + nomineeNames.lastName
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    if (nominations.isEmpty()) {
                        binding.emptyContainer.visibility = View.VISIBLE
                        binding.nominationsList.visibility = View.GONE
                    } else {

                        binding.emptyContainer.visibility = View.GONE
                        binding.nominationsList.visibility = View.VISIBLE

                    }
                    val reversedNominations = nominations.reversed()

                    adapter.registerAdapterDataObserver(object :
                        RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            super.onItemRangeInserted(positionStart, itemCount)
                            binding.nominationsList.smoothScrollToPosition(0)
                        }
                    })
                    adapter.submitList(reversedNominations)
                }
            }


        }
    }
}
