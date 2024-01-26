package com.androidtest.bymilanchothani.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.bymilanchothani.databinding.ItemNominationBinding
import com.androidtest.bymilanchothani.models.Nomination

class NominationAdapter : ListAdapter<Nomination, NominationAdapter.NominationViewHolder>(NominationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NominationViewHolder {
        val binding = ItemNominationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NominationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NominationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NominationViewHolder(private val binding: ItemNominationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nomination: Nomination) {
            binding.name.text = nomination.nomineeName
            binding.reason.text = nomination.reason
        }
    }

    class NominationDiffCallback : DiffUtil.ItemCallback<Nomination>() {
        override fun areItemsTheSame(oldItem: Nomination, newItem: Nomination): Boolean {
            return oldItem.nominationId == newItem.nominationId
        }

        override fun areContentsTheSame(oldItem: Nomination, newItem: Nomination): Boolean {
            return oldItem == newItem
        }
    }
}