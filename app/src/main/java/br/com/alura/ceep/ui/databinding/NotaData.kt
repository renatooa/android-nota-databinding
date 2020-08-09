package br.com.alura.ceep.ui.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.alura.ceep.model.Nota

class NotaData(
    var nota: Nota = Nota(),
    val titulo: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = nota.titulo
    },
    val descricao: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = nota.descricao
    },
    val favorita: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = nota.favorita
    },
    val imagemUrl: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = nota.imagemUrl
    }
) {
    val temUrl: LiveData<Boolean> = Transformations.map(imagemUrl) {
        it.isNotEmpty()
    }

    fun atualiza(nota: Nota) {
        this.nota = nota;
        titulo.postValue(nota.titulo)
        descricao.postValue(nota.descricao)
        favorita.postValue(nota.favorita)
        imagemUrl.postValue(nota.imagemUrl)
    }

    fun paraNota(): Nota? {
        return this.nota.copy(
            titulo = titulo.value ?: return null,
            descricao = descricao.value ?: return null,
            favorita = favorita.value ?: return null,
            imagemUrl = imagemUrl.value ?: return null
        )
    }
}