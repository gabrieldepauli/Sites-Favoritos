package br.edu.ifsp.dmo1.sitesfavoritos.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.sitesfavoritos.model.Site

class SiteViewModel: ViewModel() {
    private val _sites = MutableLiveData<MutableList<Site>>(mutableListOf())
    val sites: LiveData<MutableList<Site>> get() = _sites

    // Função para adicionar um site na lista
    fun addSite(site: Site) {
        _sites.value?.add(site)
        _sites.value = _sites.value
    }

    // Função para remover um site da lista
    fun deleteSite(position: Int) {
        _sites.value?.let {
            if (position in it.indices) {
                it.removeAt(position)
                _sites.value = _sites.value
            }
        }
    }

    // Função para favoritar e desfavoritar um site
    fun favoriteOrUnfavoriteSite(position: Int) {
        _sites.value?.get(position)?.let {
            it.favorito = !it.favorito
        }
        _sites.value = _sites.value
    }

}