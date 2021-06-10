package br.edu.infnet.at_monetizacao.domain.service

import br.edu.infnet.at_monetizacao.domain.dao.UsuarioDAO
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UsuarioService: UsuarioDAO {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun getUsuarioLogado(): FirebaseUser {
        return firebaseAuth.currentUser!!
    }

    override fun salvarUsuario(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, senha)
    }

    override fun login(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, senha)
    }

    override fun isUsuarioLogado(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}