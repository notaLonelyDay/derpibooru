package me.lonelyday.derpibooru.ui.search


import android.R.attr
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding
import android.R.attr.y
import androidx.fragment.app.activityViewModels


private const val SEARCH_QUERY = "search_query"

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var searchQuery: List<String> = arrayListOf("safe")

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    val querySharedViewModel by activityViewModels<SearchQuerySharedViewModel>()

    private lateinit var adapter: ImagesAdapter

    private var mainJob: Job? = null


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initQueryListener()
    }

    private fun initQueryListener() {
        querySharedViewModel.query.observe(viewLifecycleOwner) {
            submitQuery(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    private fun initAdapter() {
        val glide = Glide.with(this)
        adapter = ImagesAdapter(requireContext())
        binding.recyclerList.adapter = adapter

    }

    private fun submitQuery(query: Query){
        viewLifecycleOwner.lifecycleScope.launch {
            mainJob?.let {
                it.cancel()
                it.join()
            }

            mainJob = viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getImagesPagingDataFlow(query).collectLatest {
                    adapter.submitData(it)
                }
            }
        }

    }
}