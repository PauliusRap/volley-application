package com.paulius.testapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paulius.testapplication.databinding.FragmentMainBinding
import kotlin.system.exitProcess

class MainScreenFragment private constructor(): Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonExit.setOnClickListener {
            exitProcess(0)
        }

        binding.buttonStart.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.main_container, GameFragment.newInstance())?.commit()
        }
    }

    companion object {
        fun newInstance(): MainScreenFragment {
            return MainScreenFragment()
        }
    }
}