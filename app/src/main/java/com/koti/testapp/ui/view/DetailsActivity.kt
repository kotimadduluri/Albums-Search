package com.koti.testapp.ui.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.koti.testapp.R
import com.koti.testapp.databinding.ActiviyDetailsBinding
import com.koti.testapp.network.response.Item
import com.koti.testapp.ui.adapter.ContributorsAdapter
import com.koti.testapp.ui.vm.DetailsViewModel

/**
 * @author koti
 * to show full details
 */
class DetailsActivity : BaseActivity(R.layout.activiy_details), View.OnClickListener {
lateinit var item: Item
lateinit var viewmodel:DetailsViewModel
lateinit var viewBinder:ActiviyDetailsBinding

val contributorsAdapter:ContributorsAdapter by lazy {
    ContributorsAdapter()
}

    companion object{
        val DATA="_data"
        fun showDetails(context: Context, item: Item) {
            val intent=Intent(context, DetailsActivity::class.java)
            intent.putExtra(DATA, Gson().toJson(item))
            context.startActivity(intent)
        }
    }

    override fun initViewmodel() {
        viewmodel=ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun handleIntent() {
        item= Gson().fromJson(intent.getStringExtra(DATA), Item::class.java)
    }

    override fun initView() {
        viewBinder= DataBindingUtil.setContentView(this, R.layout.activiy_details)
        viewBinder.repo=item
        viewBinder.RvContributor.adapter=contributorsAdapter
    }

    override fun startObservers() {
        viewmodel.getContributors(item.name,item.owner.login).observe(this,{
            it?.let {
                println("Collaborators result :::: $it")
                contributorsAdapter.submitList(it)
            }
        })
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.openProject -> {
                startWebPage()
            }
            R.id.actionBack->onBackPressed()
        }
    }

    private fun startWebPage() {
        try{

            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(item.htmlUrl))
            startActivity(browserIntent)
        }catch (e:ActivityNotFoundException){
            showMessage("No application found to show details. Install any browser to continue")
        }
    }
}