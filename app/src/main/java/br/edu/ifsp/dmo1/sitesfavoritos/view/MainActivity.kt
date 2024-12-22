package br.edu.ifsp.dmo1.sitesfavoritos.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo1.sitesfavoritos.R
import br.edu.ifsp.dmo1.sitesfavoritos.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.sitesfavoritos.databinding.SitesDialogBinding
import br.edu.ifsp.dmo1.sitesfavoritos.model.Site
import br.edu.ifsp.dmo1.sitesfavoritos.modelView.SiteViewModel
import br.edu.ifsp.dmo1.sitesfavoritos.view.adapters.SiteAdapter
import br.edu.ifsp.dmo1.sitesfavoritos.view.listeners.SiteItemClickListener

class MainActivity : AppCompatActivity(), SiteItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SiteViewModel by viewModels() // Instância do ViewModel
    private lateinit var adapter: SiteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configRecyclerView()
        configListeners()

        viewModel.sites.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

    }

    override fun clickSiteItem(position: Int) {
        val site = viewModel.sites.value?.get(position)
        site?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://${it.url}"))
            startActivity(intent)
        }
    }

    override fun clickHeartSiteItem(position: Int) {
        viewModel.favoriteOrUnfavoriteSite(position)
    }

    override fun deleteSiteItem(position: Int) {
        viewModel.deleteSite(position)
    }

    private fun configRecyclerView() {
        adapter = SiteAdapter(this, viewModel.sites.value ?: emptyList(), this)
        binding.recyclerviewSites.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSites.adapter = adapter
    }

    private fun configListeners() {
        binding.buttonAdd.setOnClickListener { handleAddSite() }
    }

    private fun handleAddSite() {
        val view = layoutInflater.inflate(R.layout.sites_dialog, null)
        val dialogBinding = SitesDialogBinding.bind(view)

        AlertDialog.Builder(this)
            .setView(view)
            .setTitle(R.string.novo_site)
            .setPositiveButton(R.string.salvar) { dialog, _ ->
                val apelido = dialogBinding.edittextApelido.text.toString()
                val url = dialogBinding.edittextUrl.text.toString()
                viewModel.addSite(Site(apelido, url))
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancelar) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}