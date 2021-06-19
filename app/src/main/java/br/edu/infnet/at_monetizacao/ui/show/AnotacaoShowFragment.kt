package br.edu.infnet.at_monetizacao.ui.show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.edu.infnet.at_monetizacao.R
import br.edu.infnet.at_monetizacao.domain.entity.AnotacaoUtil
import kotlinx.android.synthetic.main.fragment_anotacao_show.*

class AnotacaoShowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anotacao_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anotacaoUtil = AnotacaoUtil.anotacaoSelecionada
        var titulo = anotacaoUtil?.title
        var descricao = anotacaoUtil?.description

        tituloShow.text = titulo.toString()
        descricaoShow.text = descricao.toString()

        btnShowVoltar.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}