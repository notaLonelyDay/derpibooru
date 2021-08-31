package me.lonelyday.derpibooru.ui.search


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding


private const val SEARCH_QUERY = "search_query"

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var searchQuery: List<String> = arrayListOf("safe")

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    private val querySharedViewModel by activityViewModels<SearchQuerySharedViewModel>()

    private lateinit var adapter: ImagesAdapter


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
        adapter = ImagesAdapter(requireContext())
        binding.recyclerList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchLoadStateAdapter(adapter),
            footer = SearchLoadStateAdapter(adapter)
        )
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.images.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            var check_ScrollingUp = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    if(check_ScrollingUp)
                    {
                        YourView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.trans_downwards));
                        check_ScrollingUp = false;
                    }

                } else {
                    // User scrolls down
                    if(!check_ScrollingUp )
                    {
                        YourView
                            .startAnimation(AnimationUtils
                                .loadAnimation(context,R.anim.trans_upwards));
                        check_ScrollingUp = true;

                    }
                }
            }
        })
    }

    private fun submitQuery(query: Query) {
        viewModel.submitQuery(query)
    }
}