package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.articles.presentation.adapter.CountriesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.countries_bottom_sheet_dialog_fragment.*

class CountriesBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val countriesAdapter: CountriesAdapter by lazy { CountriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.countries_bottom_sheet_dialog_fragment, container, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        // TODO: Submit list to adapter

    }

    companion object {
        const val TAG = "countries_bottom_sheed"
    }

}