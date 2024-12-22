package br.edu.ifsp.dmo1.sitesfavoritos.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo1.sitesfavoritos.R
import br.edu.ifsp.dmo1.sitesfavoritos.databinding.ItemViewBinding
import br.edu.ifsp.dmo1.sitesfavoritos.model.Site
import br.edu.ifsp.dmo1.sitesfavoritos.view.listeners.SiteItemClickListener

class SiteAdapter(
    private val context: Context,
    private val dataset: List<Site>,
    private val listener: SiteItemClickListener
) : RecyclerView.Adapter<SiteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemViewBinding = ItemViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.textviewApelido.text = item.apelido
        holder.binding.textviewUrl.text = item.url

        // Alterna a cor do coração
        holder.binding.imgHeart.setColorFilter(
            context.getColor(if (item.favorito) R.color.red else R.color.gray)
        )

        // Listener para favoritar/desfavoritar
        holder.binding.imgHeart.setOnClickListener { listener.clickHeartSiteItem(position) }

        // Listener para exclusão
        holder.binding.imgDelete.setOnClickListener { listener.deleteSiteItem(position) }

        // Listener para clicar no site
        holder.binding.layoutItem.setOnClickListener { listener.clickSiteItem(position) }
    }

    override fun getItemCount(): Int = dataset.size
}
