package br.edu.infnet.at_monetizacao.ui.home

import android.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.edu.infnet.at_monetizacao.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var firebaseAuthService: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"

        viewModel = HomeViewModel()
        firebaseAuthService = FirebaseAuth.getInstance()

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
}