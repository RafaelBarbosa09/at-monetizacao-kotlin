package br.edu.infnet.at_monetizacao.ui.usuario.cadastro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.infnet.at_monetizacao.R
import kotlinx.android.synthetic.main.fragment_cadastro_usuario.*

class CadastroUsuarioFragment : Fragment() {

    private lateinit var viewModel: CadastroUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cadastro_usuario, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = ""

        viewModel = ViewModelProvider(requireActivity()).get(CadastroUsuarioViewModel::class.java)

        viewModel.sucesso.observe(viewLifecycleOwner, Observer {
            if(it)
                findNavController().navigate(R.id.loginFragment)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank())
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        linkLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        btnCadastrarUsuario.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.iptEmailCadastro).text.toString()
            val senha = view.findViewById<EditText>(R.id.iptSenhaCadastro).text.toString()

            viewModel.cadastrarUsuario(email, senha)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}