package com.koti.testapp.ui.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.koti.testapp.R
import com.koti.testapp.databinding.ActiviyDetailsBinding
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.network.response.Item
import com.koti.testapp.ui.adapter.ContributorsAdapter
import com.koti.testapp.ui.vm.DetailsViewModel

/**
 * @author koti
 * to show full details of repo
 */
class DetailsActivity : BaseActivity(R.layout.activiy_details), View.OnClickListener {
    lateinit var item: RepoEntity
    lateinit var viewmodel: DetailsViewModel
    lateinit var viewBinder: ActiviyDetailsBinding

    private val contributorsAdapter: ContributorsAdapter by lazy {
        ContributorsAdapter()
    }

    companion object {
        const val DATA = "_data"
        fun showDetails(context: Context, item: RepoEntity) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DATA, Gson().toJson(item))
            //context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            context.startActivity(intent)
        }
    }

    override fun initViewmodel() {
        viewmodel = ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun handleIntent() {
        item = Gson().fromJson(intent.getStringExtra(DATA), RepoEntity::class.java)
    }

    override fun initView() {
        viewBinder = DataBindingUtil.setContentView(this, R.layout.activiy_details)

        viewBinder.apply {
            repo=item
        }

        viewBinder.RvContributor.apply {
            adapter = contributorsAdapter
            addItemDecoration(DividerItemDecoration(this@DetailsActivity,RecyclerView.VERTICAL))
        }
    }

    override fun startObservers() {
        item.name?.let {name->
            item.loginName?.let { loginName ->
                viewmodel.getContributors(name, loginName).observe(this, {
                    it?.let {
                        contributorsAdapter.submitList(it)
                    }
                })
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.openProject -> {
                startWebPage()
            }
            R.id.actionBack -> onBackPressed()
        }
    }

    //to start system web browser
    private fun startWebPage() {
        try {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(item.url)
            )
            startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            showMessage("No application found to show details. Install any browser to continue")
        }
    }
}