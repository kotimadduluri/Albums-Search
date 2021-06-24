package com.koti.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.databinding.ItemRepositoryBinding
import com.koti.testapp.db.roomDB.RepoEntity

/**
 * @author koti
 * To list all repositories
 */
class RepositoriesAdapter(val clickListener: RepositoriesClickListener) :
    PagedListAdapter<RepoEntity, RepositoriesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity) = oldItem == newItem

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity) = oldItem._id == newItem._id
        }
    }

    inner class ViewHolder(private val binder: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binder.root) {
        init {
            binder.root.setOnClickListener {
                getItem(adapterPosition)?.let { it1 -> clickListener.onRepositoriesClick(it1) }
            }
        }

        fun bindData(item: RepoEntity) {
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
        getItem(position)?.let { holder.bindData(it) }
    }
}

//Interface to make interaction between list and activity/view
interface RepositoriesClickListener {
    fun onRepositoriesClick(item: RepoEntity)
}