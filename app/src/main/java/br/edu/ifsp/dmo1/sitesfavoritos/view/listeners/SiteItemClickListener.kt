package br.edu.ifsp.dmo1.sitesfavoritos.view.listeners

interface SiteItemClickListener {

    fun clickSiteItem(position: Int)

    fun clickHeartSiteItem(position: Int)

    fun deleteSiteItem(position: Int)

}
