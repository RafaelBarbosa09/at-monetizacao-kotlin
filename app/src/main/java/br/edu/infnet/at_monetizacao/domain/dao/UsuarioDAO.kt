package br.edu.infnet.at_monetizacao.domain.dao

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface UsuarioDAO {
    fun getUsuarioLogado(): FirebaseUser
    fun salvarUsuario(email: String, senha: String): Task<AuthResult>
    fun login(email: String, senha: String): Task<AuthResult>
    fun isUsuarioLogado(): Boolean
    fun logout()
}