package br.edu.infnet.at_monetizacao.ui.usuario.login

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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = ""

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        viewModel.sucesso.observe(viewLifecycleOwner, Observer {
            if(it)
                findNavController().navigate(R.id.cadastroAnotacaoFragment)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank())
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        btnLogin.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.iptEmailLogin).text.toString()
            val senha = view.findViewById<EditText>(R.id.iptPasswordLogin).text.toString()

            viewModel.login(email, senha)
        }

        linkCadastroUsuario.setOnClickListener {
            findNavController().navigate(R.id.cadastroUsuarioFragment)
        }
    }

}