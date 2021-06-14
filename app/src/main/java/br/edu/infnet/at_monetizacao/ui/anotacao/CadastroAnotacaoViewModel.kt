package br.edu.infnet.at_monetizacao.ui.anotacao

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.at_monetizacao.domain.entity.Anotacao
import br.edu.infnet.at_monetizacao.domain.service.AnotacaoService
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.util.*

class CadastroAnotacaoViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val anotacaoService = AnotacaoService()

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun salvarAnotacao(title: String, description: String, picture: Bitmap) {

        var emailKey = firebaseAuth.currentUser!!.email

        val stream = ByteArrayOutputStream()
        picture.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        var anotacao = Anotacao(
            null,
            emailKey,
            Calendar.getInstance().time,
            title,
            description
        )

        var task = anotacaoService.salvar(anotacao)

        task
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Image saved successfully"
            }
            .addOnFailureListener {
                Log.e("Salvar anotacao -> ", "${it.message}")
                _msg.value = "${it.message}"
            }
    }
}