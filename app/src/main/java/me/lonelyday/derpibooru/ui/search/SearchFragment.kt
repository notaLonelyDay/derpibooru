package me.lonelyday.derpibooru.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding


import me.lonelyday.derpibooru.R

private const val SEARCH_QUERY = "search_query"

class SearchFragment : Fragment() {
    private var searchQuery: List<String> = arrayListOf("safe")

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchQuery = it.getStringArrayList(SEARCH_QUERY)!!
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }
}