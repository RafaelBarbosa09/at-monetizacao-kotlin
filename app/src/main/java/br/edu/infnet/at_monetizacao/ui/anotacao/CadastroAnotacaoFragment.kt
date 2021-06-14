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
import androidx.core.content.ContextCompat
import br.edu.infnet.at_monetizacao.R
import br.edu.infnet.at_monetizacao.domain.service.AnotacaoService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cadastro_anotacao.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CadastroAnotacaoFragment : Fragment() {

    private lateinit var viewModel: CadastroAnotacaoViewModel
    private val CAMERA_REQUEST_CODE = 0
    private lateinit var bitmapPicture: Bitmap

    private lateinit var firebaseAuthService: FirebaseAuth
    private lateinit var annotationService: AnotacaoService
    private lateinit var manifest: Manifest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cadastro_anotacao, container, false)

        viewModel = CadastroAnotacaoViewModel()

        salvarFoto(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextTitulo = view.findViewById<EditText>(R.id.iptTituloAnotacao).text.toString()
        val editTextDescricao = view.findViewById<EditText>(R.id.iptTituloDescricao).text.toString()

        val file = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), editTextTitulo + Calendar.getInstance().time.toString() + ".jpeg")
        val fOut = FileOutputStream(file)

        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, 85, fOut)


        fOut.flush()
        fOut.close()

        var picture = bitmapPicture

        viewModel.salvarAnotacao(editTextTitulo, editTextDescricao, picture)
    }

    private fun salvarFoto(view: View) {
        var fotoImageView = view.findViewById<ImageView>(R.id.imgAnotacao)

        fotoImageView.setOnClickListener {

            if(ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                } else {
                    Toast.makeText(requireContext(), "Peguei a foto!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Log.e("permissão câmera -> ", "deu ruim na permissao da camera")
                }
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }

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