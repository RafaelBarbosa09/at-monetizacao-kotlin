package br.edu.infnet.at_monetizacao.domain.service

import br.edu.infnet.at_monetizacao.domain.dao.AnotacaoDAO
import br.edu.infnet.at_monetizacao.domain.entity.Anotacao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class AnotacaoService : AnotacaoDAO {

    var db = FirebaseFirestore.getInstance().collection("anotacao")

    override fun salvar(entity: Anotacao): Task<Void> {
        return db.document().set(entity)
    }

    override fun excluir(key: String): Task<Void> {
        return db.document(key).delete()
    }

    override fun alterar(entity: Anotacao, key: String): Task<Void> {
        return db.document(key).set(entity)
    }
}