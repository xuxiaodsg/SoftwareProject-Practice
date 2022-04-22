package com.example.exp_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.exp_2.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    //在onViewCreated方法中使用绑定机制设置按钮的响应事件（创建应用程序时自带的按钮）。
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.toastButton.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }


        // find the toast_button by its ID and set a click listener
        view.findViewById<Button>(R.id.toast_button).setOnClickListener {
            // create a Toast with some text, to appear for a short time
            val myToast = Toast.makeText(context, "Hello Toast!", Toast.LENGTH_LONG)
            // show the Toast
            myToast.show()
        }

        view.findViewById<Button>(R.id.count_button).setOnClickListener {
            countMe(view)
        }

        binding.randomButton.setOnClickListener {
            randomMe(view)
        }


    }

    private fun randomMe(view: View){
        // Get the text view
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        // Get the value of the text view.
        val count = showCountTextView.text.toString()
        var count_int = count?.toInt()

//        val bundle = bundleOf("count" to count)
//        findNavController().navigate(R.id.SecondFragment,bundle)

        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(count_int)
        view.findNavController().navigate(action)
    }

    private fun countMe(view: View) {
        // Get the text view
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)

        // Get the value of the text view.
        val countString = showCountTextView.text.toString()

        // Convert value to a number and increment it
        var count = countString.toInt()
        count++

        // Display the new value in the text view.
        showCountTextView.text = count.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}