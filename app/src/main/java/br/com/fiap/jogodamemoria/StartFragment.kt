package br.com.fiap.jogodamemoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import br.com.fiap.jogodamemoria.data.ASSharedPreferences
import br.com.fiap.jogodamemoria.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var sharedPrefs: ASSharedPreferences
    private lateinit var bindings: FragmentStartBinding
    private lateinit var bestRanking: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bestRanking = "José"
        bindings.scoreList.setText(bestRanking)

        super.onViewCreated(view, savedInstanceState)
        bindings.playBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_startFragment_to_gameFragment)
        }

    }
}