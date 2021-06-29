package com.koti.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.databinding.ItemRepositoryBinding
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.db.roomDB.RepoWithContributors

/**
 * @author koti
 * To list all repositories
 */
class RepositoriesAdapter(val clickListener: RepositoriesClickListener) :
    PagedListAdapter<RepoWithContributors, RepositoriesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RepoWithContributors>() {
            override fun areItemsTheSame(oldItem: RepoWithContributors, newItem: RepoWithContributors) = oldItem == newItem
            override fun areContentsTheSame(oldItem: RepoWithContributors, newItem: RepoWithContributors) = (oldItem.repoEntity._id == newItem.repoEntity._id || oldItem.contributors.size!=newItem.contributors.size)
        }
    }

    inner class ViewHolder(private val binder: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binder.root) {
        init {
            binder.root.setOnClickListener {
                getItem(adapterPosition)?.let { it1 -> clickListener.onRepositoriesClick(it1.repoEntity._id) }
            }
        }

        fun bindData(item: RepoWithContributors) {
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

    fun updateData(newRepoData: RepoWithContributors) {
        try{
            val currentItem= currentList?.single { it.repoEntity._id == newRepoData.repoEntity._id }
            currentItem?.let {
                it.contributors=newRepoData.contributors
                it.repoEntity=newRepoData.repoEntity
            }
            notifyDataSetChanged()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}

//Interface to make interaction between list and activity/view
interface RepositoriesClickListener {
    fun onRepositoriesClick(item: Int)
}