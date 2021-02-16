package com.paulius.testapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.paulius.testapplication.GameViewModel.JumpState
import com.paulius.testapplication.GameViewModel.MovementState
import com.paulius.testapplication.databinding.FragmentGameBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GameFragment private constructor() : Fragment() {

    var startY = 0F

    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, NewInstanceFactory()).get(GameViewModel::class.java)
        val movementDisposable = CompositeDisposable()

        fun moveRight() {
            movementDisposable.clear()
            movementDisposable.add(Observable.timer(5, TimeUnit.MILLISECONDS)
                .repeat() //to perform your task every 5 milliseconds
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.surface.player.moveRight()
                    binding.surface.ball.addXVelocity(10)
                }
            )
        }

        fun moveLeft() {
            movementDisposable.clear()
            movementDisposable.add(Observable.timer(5, TimeUnit.MILLISECONDS)
                .repeat() //to perform your task every 5 milliseconds
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.surface.player.moveLeft()
                    binding.surface.ball.addXVelocity(-10)
                }
            )
        }

        viewModel.movementState.observe(viewLifecycleOwner, {
            when (it) {
                MovementState.Right -> {
                    binding.textRight.visibility = View.VISIBLE
                    moveRight()
                }
                MovementState.Left -> {
                    binding.textLeft.visibility = View.VISIBLE
                    moveLeft()
                }
                MovementState.Idle -> {
                    binding.textLeft.visibility = View.GONE
                    binding.textRight.visibility = View.GONE
                    movementDisposable.clear()
                }
            }
        })

        viewModel.jumpState.observe(viewLifecycleOwner, {
            when (it) {
                JumpState.MidJump -> {
                    binding.textJump.visibility = View.VISIBLE
                    binding.surface.ball.addYVelocity(-100)
                }
                JumpState.OnGround -> binding.textJump.visibility = View.GONE
            }
        })

        binding.areaTouchLeft.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startY = event.y
                    viewModel.walkLeft()
                    return@setOnTouchListener true
                }
//                MotionEvent.ACTION_MOVE -> {
//                    viewModel.stopMoving()
//                    return@setOnTouchListener true
//                }
                MotionEvent.ACTION_UP -> {
                    viewModel.resolveJump(event.y - startY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { viewModel.endJump() }
                    viewModel.stopMoving()
                    return@setOnTouchListener true
                }
                else -> false
            }
        }

        binding.areaTouchRight.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                viewModel.walkRight()
                return@setOnTouchListener true
            } else if (event.action == MotionEvent.ACTION_UP) {
                viewModel.stopMoving()
                return@setOnTouchListener true
            }
            false
        }
    }

    companion object {
        fun newInstance(): GameFragment {
            return GameFragment()
        }
    }
}