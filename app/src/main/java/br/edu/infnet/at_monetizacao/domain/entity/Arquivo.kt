package br.edu.infnet.at_monetizacao.domain.entity

import java.io.File

object Arquivo {
    var arquivo: File ?= null
    var arquivosList: Array<String> ?= null

    var listaArquivosMock: MutableList<Anotacao> = mutableListOf(
        Anotacao(null, null, null, "Teste Título", "Teste Descrição"),
        Anotacao(null, null, null, "2", "asgfd"),
        Anotacao(null, null, null, "3", "asdf")
    )
}