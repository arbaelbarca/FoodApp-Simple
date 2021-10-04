package com.arbaelbarca.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.foodapp.R
import com.arbaelbarca.foodapp.datasource.remote.response.NutrientsItem
import com.arbaelbarca.foodapp.datasource.remote.response.ResultsItem
import com.arbaelbarca.foodapp.utils.hideView
import com.arbaelbarca.foodapp.utils.loadImageUrl
import com.arbaelbarca.foodapp.utils.showView
import kotlinx.android.synthetic.main.layout_item_menupasta.view.*

class AdapterMenuPasta(val mutableListMenuPasta: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var isLoading = false

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolderLoading(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.pbLoading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_menupasta, parent, false)
            MyViewHolder(
                view
            )
        } else {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item_loading, parent, false)
            ViewHolderLoading(
                view
            )
        }
    }


    override fun getItemCount(): Int {
        return mutableListMenuPasta.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val resultsItem = mutableListMenuPasta[position]

        if (holder is MyViewHolder) {
            holder.apply {
                itemView.apply {
                    tvTitleMenuPasta.text = resultsItem.title
                    tvPriceMenu.text =
                        resultsItem.nutrition?.nutrients?.get(0)?.amount.toString()
                    tvKatMenu.text = resultsItem.nutrition?.nutrients?.get(0)?.title

                    imgMenu.loadImageUrl(resultsItem.image.toString(), context)
                }
            }
        }

        if (holder is ViewHolderLoading) {
            holder.apply {
                if (isLoading)
                    hideView(progressBar)
                else showView(progressBar)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mutableListMenuPasta.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun showLoading(): Boolean {
        return isLoading
    }

    fun hideLoading(): Boolean {
        return isLoading
    }

    @JvmName("setLoading1")
    fun setLoading(boolean: Boolean) {
        isLoading = boolean
    }

    fun getListAdapter(): ArrayList<ResultsItem> {
        return mutableListMenuPasta
    }
}