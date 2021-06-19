package br.edu.infnet.at_monetizacao.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.at_monetizacao.domain.entity.Anotacao
import br.edu.infnet.at_monetizacao.domain.entity.Arquivo

class HomeViewModel : ViewModel() {

    private val _anotacoes = MutableLiveData<List<Anotacao>>();
    val anotacoes: LiveData<List<Anotacao>> = _anotacoes

    fun listaAnotacoes(lista: List<Anotacao>) {
        _anotacoes.value = lista
    }

//    fun listaAnotacoes() {
//        _anotacoes.value = Arquivo.listaArquivosMock
//    }
}