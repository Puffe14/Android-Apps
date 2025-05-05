package com.example.canjer

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.canjer.databinding.FragmentSecondBinding
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler
import kotlin.concurrent.timer
import kotlin.random.Random

val rem11 = "正直\tしょうじき honest\n" +
        "憎悪\tぞうお hatred\n" +
        "嫌悪\tけんお disgust\n" +
        "対して\tたいして toward\n" +
        "対象\tたいしょう target\n" +
        "激しい\tはげしい violent\n" +
        "感情\tかんじょう emotion\n" +
        "抱く\tいだく to_harbor\n" +
        "隔離\tかくり isolation\n" +
        "保護\tほご protection\n" +
        "特定\tとくてい specific\n" +
        "精神\tせいしん mind\n" +
        "精神医学\tせいしんいがく psychiatry\n" +
        "精神療法\tせいしんりょうほう psychotherapy\n" +
        "施設\tしせつ facility\n" +
        "通称\tつうしょう nickname\n" +
        "足元\tあしもと underfoot\n" +
        "筋肉\tきんにく muscle\n" +
        "関節\tかんせつ joint\n" +
        "内蔵\tないぞう viscera\n" +
        "箇所\tかしょ place\n" +
        "危機\tきき crisis\n" +
        "悲鳴\tひめい shriek\n" +
        "悲劇\tひげき tragedy\n" +
        "三文\tさんもん cheapness\n" +
        "芝居\tしばい acting\n" +
        "遠まわし\tとおまわし indirect\n" +
        "探り\tさぐり probing\n" +
        "単刀直入\tたんとうちょくにゅう point-blank\n" +
        "訊く\tきく enquire\n" +
        "女子大\tじょしだい women's college\n" +
        "鳩鳴館\tばとなかん"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val QStrings = rem11.split("\n")
    private var listOfQsSplit = QStrings.map { it.trimEnd().split(" ", "\t") }
    private var currentQ = /*if (listOfQsSplit.isNotEmpty()) listOfQsSplit[0] */ listOf("","","")
    private var skipped = false

    fun resetEdits() {
        //clears previous answer
        binding.editTextKana.text.clear()
        binding.editTextTranslate.text.clear()
        binding.textviewReveal.text = ""
        //selects new question
        val l = listOfQsSplit.size
        val r = Random.nextInt(l)
        currentQ = listOfQsSplit[r]
        skipped = false
        //asks the question
        setAnswerBtn("Answer")
        setPrompt(currentQ[0])
    }

    fun setAnswerBtn(new: String){ binding.buttonAnswer.text = new }
    fun setPrompt(new: String){ binding.textviewSecond.text = new }


    fun answer() {
        try {
            if (binding.editTextKana.text.toString() == currentQ[1] && binding.editTextTranslate.text.toString() == currentQ[2]) {
                if (!skipped && listOfQsSplit.size > 1) listOfQsSplit =
                     listOfQsSplit.filterNot { it == currentQ }
                else if (skipped)
                else setPrompt("Done!")
                resetEdits()
            }

            else {
                binding.buttonAnswer.text = getString(R.string.wrong)
            }
        }
        catch(e: Exception){
            if (e.message != null) setPrompt(e.message!!)
            //Toast.makeText(activity, e.message)
        }
    }

    @SuppressLint("SetTextI18n")
    fun reveal() {
        try {
            binding.textviewReveal.text = currentQ[1] + "\n" + currentQ[2]
        }
        catch(e: Exception){
            if (e.message != null) setPrompt(e.message!!)
        }
    }

    fun skip(){
        if (skipped) resetEdits()
        else {
            skipped = true
            reveal()
        }
    }

    fun gotIt(){
        //delay(100)
    }

    /*
    fun testAnswer(){
        binding.textviewReveal.text = currentQ[1] + currentQ[2] +
                " and " + (binding.editTextKana.text.toString() == currentQ[1]) +
                "-" +(binding.editTextTranslate.text.toString() == currentQ[2])
        println(currentQ[1]+" & "+binding.editTextKana.text.toString())
        println(currentQ[2]+" & "+binding.editTextTranslate.text.toString())
    }*/


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.buttonAnswer.setOnClickListener {
            answer()
        }
        binding.buttonSkip.setOnClickListener {
            skip()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}