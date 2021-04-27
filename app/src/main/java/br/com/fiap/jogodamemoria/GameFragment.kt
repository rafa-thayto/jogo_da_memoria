package br.com.fiap.jogodamemoria

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.fiap.jogodamemoria.data.MemoryCard
import br.com.fiap.jogodamemoria.databinding.FragmentGameBinding

private const val TAG = "MainActivity"
class GameFragment : Fragment() {
    private lateinit var bindings: FragmentGameBinding
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        val images = mutableListOf(
            R.drawable.ic_baseline_brightness_4_24,
            R.drawable.ic_baseline_cake_24,
            R.drawable.ic_baseline_camera_24,
            R.drawable.ic_baseline_engineering_24,
            R.drawable.ic_baseline_filter_drama_24,
            R.drawable.ic_baseline_fingerprint_24,
            R.drawable.ic_baseline_free_breakfast_24,
            R.drawable.ic_baseline_language_24,
        )
        images.addAll(images)
        images.shuffle()

        buttons = listOf(bindings.imageButton1, bindings.imageButton2, bindings.imageButton3, bindings.imageButton4, bindings.imageButton5,
            bindings.imageButton6, bindings.imageButton7, bindings.imageButton8, bindings.imageButton9,
            bindings.imageButton10, bindings.imageButton11, bindings.imageButton12, bindings.imageButton13,
            bindings.imageButton14, bindings.imageButton15, bindings.imageButton16)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!")
                // Update models
                updateModels(index)
                // Update the UI for the game
                updateViews()
            }
        }

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings.timerGame.start()
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.ic_baseline_import_export_24)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking:
        if (card.isFaceUp) {
//            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
//            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }

//TODO - CRIAR A LOGICA DO JOGO, DESLIGAR O TIMER AO SAIR E PREVINIR O BOTÃO DE SAIDA
}
