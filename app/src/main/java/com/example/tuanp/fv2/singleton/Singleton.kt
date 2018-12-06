package com.example.tuanp.fv2.singleton

import com.tinder.StateMachine

sealed class State {
    object Solid : State()
    object Liquid : State()
    object Gas : State()
}

sealed class Event {
    object OnMelted : Event()
    object OnFroze : Event()
    object OnVaporized : Event()
    object OnCondensed : Event()
}

sealed class SideEffect {
    object LogMelted : SideEffect()
    object LogFrozen : SideEffect()
    object LogVaporized : SideEffect()
    object LogCondensed : SideEffect()
}

public class Singleton private constructor() {
    var stateMachine:StateMachine<State, Event, SideEffect>
    init {
        println("This ($this) is a singleton")
        stateMachine = StateMachine.create<State, Event, SideEffect> {
            initialState(State.Solid)
            state<State.Solid> {
                on<Event.OnMelted> {
                    transitionTo(State.Liquid, SideEffect.LogMelted)
                }
            }
            state<State.Liquid> {
                on<Event.OnFroze> {
                    transitionTo(State.Solid, SideEffect.LogFrozen)
                }
                on<Event.OnVaporized> {
                    transitionTo(State.Gas, SideEffect.LogVaporized)
                }
            }
            state<State.Gas> {
                on<Event.OnCondensed> {
                    transitionTo(State.Liquid, SideEffect.LogCondensed)
                }
            }
            onTransition {
                val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
                when (validTransition.sideEffect) {
//                    SideEffect.LogMelted -> logger.log(ON_MELTED_MESSAGE)
//                    SideEffect.LogFrozen -> logger.log(ON_FROZEN_MESSAGE)
//                    SideEffect.LogVaporized -> logger.log(ON_VAPORIZED_MESSAGE)
//                    SideEffect.LogCondensed -> logger.log(ON_CONDENSED_MESSAGE)
                }
            }
        }
    }

    private object Holder { val INSTANCE = Singleton() }

    companion object {
        val instance: Singleton by lazy { Holder.INSTANCE }
    }
}