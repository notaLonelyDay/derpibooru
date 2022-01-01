package me.lonelyday.derpibooru.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding
import me.lonelyday.derpibooru.databinding.FragmentSearchQueryBinding
import me.lonelyday.derpibooru.ui.createDefaultQuery

class SearchQueryFragment : Fragment() {

    companion object{
        val DEFAULT_QUERY = createDefaultQuery()
    }

    private var _binding: FragmentSearchQueryBinding? = null
    private val binding get() = _binding!!

    private val querySharedViewModel by activityViewModels<SearchQuerySharedViewModel>()
    val viewModel by viewModels<SearchQueryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.queryText.setText("safe")
        querySharedViewModel.query.value = DEFAULT_QUERY

        binding.queryText.addTextChangedListener{
            querySharedViewModel.query.value = Query(it.toString())
        }
    }
}