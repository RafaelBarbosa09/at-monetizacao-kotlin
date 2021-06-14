package br.edu.infnet.at_monetizacao.domain.entity

import com.google.firebase.firestore.DocumentId
import java.util.*

class Anotacao (
        @DocumentId
        var id: String ?= null,
        var foreignKey: String ?= null,
        var createdAt: Date?= null,
//    var urlImage: List<ByteArray> ?= null,
        var title: String ?= null,
        var description: String ?= null
)