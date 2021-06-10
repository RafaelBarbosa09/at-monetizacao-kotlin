package br.edu.infnet.at_monetizacao.ui.usuario.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.at_monetizacao.domain.service.UsuarioService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel: ViewModel() {

    private var _msg = MutableLiveData<String>()
    var msg: LiveData<String> = _msg

    private var _sucesso = MutableLiveData<Boolean>()
    var sucesso: LiveData<Boolean> = _sucesso

    private val usuarioService = UsuarioService()

    fun login(email: String, senha: String) {
        val task = usuarioService.login(email, senha)

        task.addOnSuccessListener {
            _msg.value = "Usuario Autenticado"
            _sucesso.value = true
        }
        .addOnFailureListener{
            _msg.value = try {
                throw task.exception!!
            } catch (e: FirebaseAuthInvalidUserException) {
                "Usuário não cadastrado"
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                "Email ou senha não correspondem"
            } catch (e: Exception) {
                "Erro ao fazer login"
            }
        }
    }
}