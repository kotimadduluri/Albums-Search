package com.koti.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.databinding.ItemRepositoryBinding
import com.koti.testapp.network.response.Item

/**
 * @author koti
 * To list all repositories
 */
class RepositoriesAdapter(val clickListner: RepositoriesClickListner) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {
    private val itemList = ArrayList<Item>()

    inner class ViewHolder(private val binder: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binder.root) {
        init {
            binder.root.setOnClickListener {
                clickListner.onRepositoriesClick(getItem(adapterPosition))
            }
        }
        fun bindData(item: Item) {
            binder.repo = item
            binder.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    fun submitData(items: List<Item>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size

    fun getItem(pos: Int) = itemList[pos]
}

interface RepositoriesClickListner {
    fun onRepositoriesClick(item: Item)
}