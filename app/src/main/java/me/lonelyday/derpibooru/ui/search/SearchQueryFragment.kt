package me.lonelyday.derpibooru.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding
import me.lonelyday.derpibooru.databinding.FragmentSearchQueryBinding

class SearchQueryFragment : Fragment() {

    private var _binding: FragmentSearchQueryBinding? = null
    private val binding get() = _binding!!

    val querySharedViewModel by activityViewModels<SearchQuerySharedViewModel>()
    val viewModel by viewModels<SearchQueryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchQueryBinding.inflate(inflater, container, false)
        return binding.root
    }
}