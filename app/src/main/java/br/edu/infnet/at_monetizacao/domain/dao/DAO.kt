package br.edu.infnet.at_monetizacao.domain.dao

import com.google.android.gms.tasks.Task

interface DAO<E, K> {
    fun salvar(entity: E): Task<Void>;
    fun excluir(key: K): Task<Void>;
    fun alterar(entity: E, key: K): Task<Void>;
}