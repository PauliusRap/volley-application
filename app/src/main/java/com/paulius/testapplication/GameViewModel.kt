package com.paulius.testapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import java.util.concurrent.TimeUnit

class GameViewModel : ViewModel() {

    init {
        Log.e("Paulius", "viewModel acquired")
    }

    private val _movementState: MutableLiveData<MovementState> = MutableLiveData()
     val movementState: LiveData<MovementState> = _movementState

    private val _jumpState: MutableLiveData<JumpState> = MutableLiveData()
    val jumpState: LiveData<JumpState> = _jumpState

    fun walkLeft() {
        _movementState.value = MovementState.Left
    }

    fun walkRight() {
        _movementState.value = MovementState.Right
    }

    fun stopMoving() {
        _movementState.value = MovementState.Idle
    }

    fun resolveJump(differential: Float): Completable {
        return if (differential < -20 && _jumpState.value != JumpState.MidJump) {
            _jumpState.value = JumpState.MidJump
            Completable.timer(1, TimeUnit.SECONDS)
        } else Completable.complete()
    }

    fun endJump() {
        _jumpState.value = JumpState.OnGround
    }

    sealed class MovementState {
        object Left : MovementState()
        object Right : MovementState()
        object Idle : MovementState()
    }

    sealed class JumpState {
        object OnGround : JumpState()
        object MidJump : JumpState()
    }
}