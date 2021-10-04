package com.arbaelbarca.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbaelbarca.foodapp.R
import com.arbaelbarca.foodapp.adapter.AdapterMenuPasta
import com.arbaelbarca.foodapp.basedata.BaseFragmentBinding
import com.arbaelbarca.foodapp.databinding.FragmentMenuPastaBinding
import com.arbaelbarca.foodapp.datasource.remote.response.ResponseError
import com.arbaelbarca.foodapp.datasource.remote.response.ResultsItem
import com.arbaelbarca.foodapp.utils.*
import com.arbaelbarca.foodapp.viewmodel.ViewModelPasta
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMenuPasta.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMenuPasta : BaseFragmentBinding<FragmentMenuPastaBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var page = 2
    var perPage = 10
    var totalPage = 0
    lateinit var viewmodelPasta: ViewModelPasta
    lateinit var linearLayoutManager: LinearLayoutManager
    var arrayListMenuPastaBinding: ArrayList<ResultsItem> = ArrayList()
    lateinit var adapterMenuPasta: AdapterMenuPasta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMenuPasta().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuPastaBinding
        get() = FragmentMenuPastaBinding::inflate

    override fun setupView(binding: FragmentMenuPastaBinding) {
        initial(binding)
    }

    private fun initial(binding: FragmentMenuPastaBinding) {
        viewmodelPasta = ViewModelProvider(requireActivity()).get(ViewModelPasta::class.java)
        viewmodelPasta.getMenuPasta("pasta", page, perPage)
        initObserve(binding)
    }

    private fun initObserve(binding: FragmentMenuPastaBinding) {
        observerNextList(binding)
        observerList(binding)

    }

    private fun observerList(binding: FragmentMenuPastaBinding) {
        viewmodelPasta.observerMenuPasta()
            .observe(viewLifecycleOwner, {
                when (it) {
                    is UiState.Loading -> {
                        showView(binding.pbLoadingMenu)
                    }
                    is UiState.Success -> {
                        hideView(binding.pbLoadingMenu)
                        val dataItem = it.data
                        if (dataItem.results?.isNotEmpty()!!) {
                            initAdapter(binding, dataItem.results)
                            totalPage = dataItem.totalResults!!
                        }
                    }
                    is UiState.Failure -> {
                        hideView(binding.pbLoadingMenu)
                        it.throwable.printStackTrace()
                        showToast(showErrorMessageThrowable(it.throwable), requireContext())
                    }
                }
            })
    }

    private fun observerNextList(binding: FragmentMenuPastaBinding) {
        viewmodelPasta.observerMenuPastaNext()
            .observe(viewLifecycleOwner, {
                when (it) {
                    is UiState.Loading -> {
                        adapterMenuPasta.showLoading()
                    }
                    is UiState.Success -> {
                        val dataItem = it.data
                        if (dataItem.results?.isNotEmpty()!!) {
                            totalPage = dataItem.totalResults!!
                            dataItem.results.forEach {
                                arrayListMenuPastaBinding.addAll(
                                    listOf(
                                        it
                                    )
                                )
                            }

                            adapterMenuPasta.apply {
                                notifyDataSetChanged()
                                hideLoading()
                            }
                        }
                    }
                    is UiState.Failure -> {
                        adapterMenuPasta.hideLoading()
                        it.throwable.printStackTrace()
                        println("respon Error ${it.throwable.message}")
                        showToast(showErrorMessageThrowable(it.throwable), requireContext())
                    }
                }
            })
    }

    private fun initAdapter(binding: FragmentMenuPastaBinding, results: List<ResultsItem?>) {
        arrayListMenuPastaBinding = results as ArrayList<ResultsItem>
        adapterMenuPasta = AdapterMenuPasta(arrayListMenuPastaBinding)

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvListMenuPasta.apply {
            adapter = adapterMenuPasta
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(PaginationScrollListener(linearLayoutManager) {
                if (adapterMenuPasta.getListAdapter().size != perPage) {
                    page++
                    viewmodelPasta.getMenuPastaNext(
                        "pasta",
                        page,
                        perPage
                    )
                }
            })
        }

    }


}