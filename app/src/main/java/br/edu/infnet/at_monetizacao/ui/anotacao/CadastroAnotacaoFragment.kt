package br.edu.infnet.at_monetizacao.ui.anotacao

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import br.edu.infnet.at_monetizacao.R
import br.edu.infnet.at_monetizacao.domain.service.AnotacaoService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cadastro_anotacao.*
import java.io.*
import java.util.*

class CadastroAnotacaoFragment : Fragment() {

    private lateinit var viewModel: CadastroAnotacaoViewModel
    private val CAMERA_REQUEST_CODE = 0
    private lateinit var bitmapPicture: Bitmap

    private lateinit var firebaseAuthService: FirebaseAuth
    private lateinit var anotacaoService: AnotacaoService
    private val FILE_NAME = "expfile"
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cadastro_anotacao, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Cadastro"

        firebaseAuthService = FirebaseAuth.getInstance()
        viewModel = CadastroAnotacaoViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogoutAnotacao.setOnClickListener {
            firebaseAuthService.signOut()
            findNavController().navigate(R.id.loginFragment)
        }

        btnVoltarAnotacao.setOnClickListener {
            findNavController().popBackStack()
        }

        imgAnotacao.setOnClickListener{
            salvarFoto(it)
        }

        btnCadastrarAnotacao.setOnClickListener {
            val editTextTitulo = view.findViewById<EditText>(R.id.iptTituloAnotacao).text.toString()
            val editTextDescricao = view.findViewById<EditText>(R.id.iptTituloDescricao).text.toString()

            val file = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), editTextTitulo + Calendar.getInstance().time.toString() + ".jpeg")
            val fOut = FileOutputStream(file)

            bitmapPicture.compress(Bitmap.CompressFormat.JPEG, 85, fOut)

            fOut.flush()
            fOut.close()

            var picture = bitmapPicture

            viewModel.salvarAnotacao(editTextTitulo, editTextDescricao, picture)

            clickGravar(it, editTextTitulo, editTextDescricao)

            view.findNavController().popBackStack()
        }
    }

    private fun getEncFile(nome: String): EncryptedFile{
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val file = File(context?.filesDir, nome)

        return EncryptedFile.Builder(
                file,
                requireContext(),
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB)
                .build()
    }

    fun clickGravar(view: View, titulo: String, descricao: String){
        val encryptedOut: FileOutputStream = getEncFile("$titulo.txt").openFileOutput()
        val pw = PrintWriter(encryptedOut)
        pw.println(titulo)
        pw.println(descricao)
        pw.flush()
        encryptedOut.close()
    }

    fun clickLer(view: View){
        val encryptedIn: FileInputStream = getEncFile("teste ler.txt").openFileInput()
        val br = BufferedReader(InputStreamReader(encryptedIn))

        br.lines().forEach {
            t -> Log.d("LEITURA: ",t)
        }
        encryptedIn.close()
    }



    private fun salvarFoto(view: View) {
        if(ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), "erro", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Log.e("permissão câmera -> ", "deu ruim na permissao da camera")
            }
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val img = data?.extras?.get("data") as Bitmap

            bitmapPicture = img
            imgAnotacao.setImageBitmap(bitmapPicture)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}