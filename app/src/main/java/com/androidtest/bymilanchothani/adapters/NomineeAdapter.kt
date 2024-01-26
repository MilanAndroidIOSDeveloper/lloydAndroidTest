package com.androidtest.bymilanchothani.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.androidtest.bymilanchothani.R
import com.androidtest.bymilanchothani.activities.CreateNominationActivity
import com.androidtest.bymilanchothani.databinding.ItemNomineeBinding
import com.androidtest.bymilanchothani.models.Nominee

class NomineeSpinnerAdapter(context: Context, resource: Int, private val nominees: List<Nominee>) : ArrayAdapter<Nominee>(context, resource, nominees) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding: ItemNomineeBinding

        if (convertView == null) {
            binding = ItemNomineeBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemNomineeBinding
        }
        val nominees = nominees[position]

        binding.root.text = "${nominees.firstName} ${nominees.lastName}"

        return binding.root

    }
}