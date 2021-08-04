package me.lonelyday.derpibooru.ui.search


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding
import me.lonelyday.derpibooru.ui.search.paging.ImagesAdapter

private const val SEARCH_QUERY = "search_query"

class SearchFragment : Fragment() {
    private var searchQuery: List<String> = arrayListOf("safe")

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val viewModel by viewModels<SearchViewModel>()


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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    fun initAdapter(){
        val glide = Glide.with(this)
        adapter = ImagesAdapter(glide)
        binding.recyclerList.adapter = adapter


        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                adapter.submitData(it)
            }
        }

    }
}