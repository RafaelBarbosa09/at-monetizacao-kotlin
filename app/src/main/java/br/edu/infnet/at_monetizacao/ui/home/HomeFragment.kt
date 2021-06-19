package br.edu.infnet.at_monetizacao.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import br.edu.infnet.at_monetizacao.R
import br.edu.infnet.at_monetizacao.adapter.AnotacaoAdapter
import br.edu.infnet.at_monetizacao.domain.entity.Anotacao
import br.edu.infnet.at_monetizacao.domain.entity.AnotacaoUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_fragment.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var firebaseAuthService: FirebaseAuth
    var path: File ?= null
    var listaDeArquivos: ArrayList<Anotacao> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"

        firebaseAuthService = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        path = context?.getFilesDir()
        val lista = path?.list()

        listaDeArquivos = lista?.let { retornaListaDeAnotacoes(it) }!!

        viewModel.listaAnotacoes(listaDeArquivos)

        viewModel.anotacoes.observe(viewLifecycleOwner, Observer {
            setupAnotacoesList(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogoutHome.setOnClickListener {
            firebaseAuthService.signOut()
            findNavController().navigate(R.id.loginFragment)
        }

        btnAddAnotacao.setOnClickListener {
            view.findNavController().navigate(R.id.cadastroAnotacaoFragment)
        }

    }

    fun retornaListaDeAnotacoes(lista: Array<String>): ArrayList<Anotacao> {
        var listaTeste = ArrayList<Anotacao>()
        if (lista != null) {
            for(item in lista) {
                var anotacao = Anotacao(null, null, null, item, null)
                listaTeste.add(anotacao)
            }
        }
        return listaTeste
    }

    private fun setupAnotacoesList(anotacoes: List<Anotacao>) {
        recyclerView.adapter = AnotacaoAdapter(anotacoes) {

            val arquivo = lerArquivo(it)
            AnotacaoUtil.anotacaoSelecionada = arquivo

            findNavController().navigate(R.id.anotacaoShowFragment)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun lerArquivo(arquivo: Anotacao): Anotacao {
        val encryptedIn: FileInputStream = getEncFile(arquivo.title.toString()).openFileInput()
        val br = BufferedReader(InputStreamReader(encryptedIn))

        var list: ArrayList<String> = ArrayList()
        br.lines().forEach {
                t -> list.add(t)
        }

        arquivo.title = list[0]
        arquivo.description = list[1]
        encryptedIn.close()

        return arquivo
    }

    private fun getEncFile(nome: String): EncryptedFile {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val file = File(context?.filesDir, nome)

        return EncryptedFile.Builder(
            file,
            requireContext(),
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB)
            .build()
    }
}