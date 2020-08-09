package br.com.alura.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.databinding.ItemNotaBinding
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.databinding.NotaData
import kotlinx.android.synthetic.main.item_nota.view.*

class ListaNotasAdapter(
    private val context: Context,
    var onItemClickListener: (notaSelecionada: Nota) -> Unit = {}
) : ListAdapter<Nota, ListaNotasAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val viewDataBinding = ItemNotaBinding.inflate(inflater, parent, false)

        return ViewHolder(viewDataBinding).also {
            viewDataBinding.lifecycleOwner = it
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nota ->
            holder.vincula(nota)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.marcarComoAtivo()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.marcarComoDesativado()
    }

    inner class ViewHolder(private val dataBinding: ItemNotaBinding) :
        RecyclerView.ViewHolder(dataBinding.root) , View.OnClickListener, LifecycleOwner{

        private lateinit var nota: Nota

        private val campoDescricao: TextView by lazy {
            itemView.item_nota_descricao
        }
        private val campoFavorita: ImageView by lazy {
            itemView.item_nota_favorita
        }
        private val campoImagem: ImageView by lazy {
            itemView.item_nota_imagem
        }

        private val registry = LifecycleRegistry(this)

        init {
            registry.markState(Lifecycle.State.INITIALIZED)
            dataBinding.clickListenerNota = this
        }

        fun marcarComoAtivo(){
            registry.markState(Lifecycle.State.STARTED)
        }

        fun marcarComoDesativado(){
            registry.markState(Lifecycle.State.DESTROYED)
        }


        fun vincula(nota: Nota) {
            this.nota = nota
            dataBinding.nota = NotaData(nota)
        }

        override fun onClick(view: View) {
            if (::nota.isInitialized) {
                onItemClickListener(nota)
            }
        }

        override fun getLifecycle(): Lifecycle = registry
    }
}

object DiffCallback : DiffUtil.ItemCallback<Nota>() {
    override fun areItemsTheSame(
        oldItem: Nota,
        newItem: Nota
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Nota, newItem: Nota) = oldItem == newItem
}