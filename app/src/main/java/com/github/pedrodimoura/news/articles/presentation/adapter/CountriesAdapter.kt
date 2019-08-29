package com.github.pedrodimoura.news.articles.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.common.util.inflate
import kotlinx.android.synthetic.main.item_country.view.*

class CountriesAdapter : ListAdapter<String, CountriesAdapter.CountriesViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder =
        CountriesViewHolder(parent.inflate(R.layout.countries_bottom_sheet_dialog_fragment))

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val countryName = getItem(position)
        holder.countryName.text = countryName
    }

    class CountriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.countryName
    }

    companion object {
        @JvmStatic
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem.contentEquals(newItem)
        }
    }

}