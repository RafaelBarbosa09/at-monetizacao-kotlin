package br.edu.infnet.at_monetizacao.ui.usuario.cadastro

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.at_monetizacao.domain.service.UsuarioService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroUsuarioViewModel: ViewModel() {

    private var _msg = MutableLiveData<String>()
    var msg: LiveData<String> = _msg

    private var _sucesso = MutableLiveData<Boolean>()
    var sucesso: LiveData<Boolean> = _sucesso

    private val usuarioService = UsuarioService()

    fun cadastrarUsuario(email: String, senha: String) {
        if (email.isNullOrBlank()) {
            _msg.value = "Preencha o email"
            return
        }

        if (senha.isNullOrBlank()) {
            _msg.value = "Preencha a senha"
            return
        }

        val task = usuarioService.salvarUsuario(email, senha)

        task.addOnSuccessListener {
            _msg.value = "Usuário cadastrado com sucesso!"
            _sucesso.value = true
        }
        .addOnFailureListener {
            _msg.value = try {
                throw task.exception!!
            } catch (e: FirebaseAuthWeakPasswordException) {
                "Digite uma senha mais forte"
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                "Digite um email válido"
            } catch (e: FirebaseAuthUserCollisionException) {
                "Email já cadastrado"
            } catch (e: Exception) {
                "Erro ao cadastrar usuário"
            }
        }
    }
}