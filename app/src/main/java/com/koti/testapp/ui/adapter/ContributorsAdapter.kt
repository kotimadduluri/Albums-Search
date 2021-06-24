package com.koti.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.databinding.IitemContributorBinding
import com.koti.testapp.network.response.Contributers

class ContributorsAdapter:RecyclerView.Adapter<ContributorsAdapter.ViewHolder>() {

    val contributers=ArrayList<Contributers>()

    class ViewHolder(val binder: IitemContributorBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindData(item: Contributers) {
            binder.user=item
            binder.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(IitemContributorBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.iitem_contributor,parent,false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount()=contributers.size

    fun getItem(pos:Int)=contributers[pos]

    fun submitList(it: List<Contributers>) {
        contributers.clear()  //to clear previous
        contributers.addAll(it)
        notifyDataSetChanged()
    }
}