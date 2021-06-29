package com.koti.testapp.ui.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.NewUserDialog
import com.koti.testapp.R
import com.koti.testapp.databinding.ActiviyDetailsBinding
import com.koti.testapp.db.roomDB.RepoWithContributors
import com.koti.testapp.ui.adapter.ContributorsAdapter
import com.koti.testapp.ui.vm.DetailsViewModel

/**
 * @author koti
 * to show full details of repo
 */
class DetailsActivity : BaseActivity(R.layout.activiy_details), View.OnClickListener,
    NewUserDialog.NewUserDialogListner {
    lateinit var item: RepoWithContributors
    lateinit var viewmodel: DetailsViewModel
    lateinit var viewBinder: ActiviyDetailsBinding
    var repoId: Int = 0

    private val contributorsAdapter: ContributorsAdapter by lazy {
        ContributorsAdapter()
    }

    companion object {
        const val DATA = "_data"
        fun showDetails(context: Context, repoId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DATA, repoId)
            //context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            context.startActivity(intent)
        }
    }

    override fun initViewmodel() {
        viewmodel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        item = viewmodel.getRepoById(repoId)
    }

    override fun handleIntent() {
        repoId = intent.getIntExtra(DATA, 0)
        if (repoId == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun initView() {
        viewBinder = DataBindingUtil.setContentView(this, R.layout.activiy_details)

        viewBinder.apply {
            repo = item
        }

        viewBinder.RvContributor.apply {
            adapter = contributorsAdapter
            addItemDecoration(DividerItemDecoration(this@DetailsActivity, RecyclerView.VERTICAL))
        }

        viewBinder.addNewPerson.setOnClickListener {
            NewUserDialog(this, this).show()
        }
    }

    override fun startObservers() {
        if (item.contributors.isNotEmpty()) {
            contributorsAdapter.submitList(item.contributors)
        } else {
            item.repoEntity.name?.let { name ->
                item.repoEntity.loginName?.let { loginName ->
                    viewmodel.getContributors(name, loginName, item.repoEntity._id).observe(this, {
                        it?.let {
                            contributorsAdapter.submitList(it)
                        }
                    })
                }
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
                Uri.parse(item.repoEntity.url)
            )
            startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            showMessage("No application found to show details. Install any browser to continue")
        }
    }

    override fun addNewPerson(name: String) {
        viewmodel.addNewPerson(name, item.repoEntity._id).observe(this, {
            it?.let {
                contributorsAdapter.addNewUser(it)
                viewBinder.RvContributor.smoothScrollToPosition(0)
            }
        })
    }

    override fun userDialogDismissed() {
        //do if required
    }
}