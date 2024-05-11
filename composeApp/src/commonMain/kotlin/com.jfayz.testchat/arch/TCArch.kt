package com.jfayz.testchat.arch

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KProperty


/**
 * UiState is an interface that is used to represent the state of the UI.
 *
 * Usually, it is a data class with final variable that holds the state of the UI.
 * Should be immutable.
 */
@Immutable
interface UiState

/**
 * Action is asynchronous dispatched update that can also trigger UI events.
 */
interface Action

/**
 * UiEvent represent the events that are dispatched to update the state of the UI.
 * It means that compared to [Action] event is synchronous and must run on UI thread.
 *
 * Can also trigger side effects. See [SideEffect].
 */
interface UiEvent

/**
 * Actor is used to perform asynchronous actions based on the dispatched [Action].
 */
fun interface Actor<A : Action, E : UiEvent> {
    suspend fun performAction(action: A): E?
}

/**
 * Store is a class that holds the state of the application and dispatches actions to update the state.
 * It is based on top of a ViewModel that is used to manage the state of the application.
 *
 * Based on the TCA (The Composable Architecture) is a pattern that is inspired by Redux and Elm.
 */
open class Store<out S : UiState, in A : Action, in E : UiEvent>(
    private val actor: Actor<A, E>,
    private val reducer: Reducer<S, A, E>,
    initialState: S,
    initEvent: E? = null,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    init {
        initEvent?.let { dispatch(it) }
    }

    /**
     * Dispatches an action.
     * Launched as a coroutine.
     */
    fun action(action: A) = viewModelScope.launch {
        actor.performAction(action)?.let { event -> withContext(Dispatchers.Main) { dispatch(event) } }
    }

    /**
     * Dispatches an [UiEvent] to update the [UiState].
     */
    fun dispatch(event: E) {
        println("Dispatching event: $event")
        with(reducer) {
            val newState = reduce(event, _state.value)
            _state.value = newState
        }
    }
}

/**
 * Reducer is used to update the state of the application based on the action that is dispatched.
 *
 * To handle side effects, use [SideEffect] and [also] for updating the state.
 *
 * Example usage:
 * ```
 * // State
 * data class MyState(val value: Int) : UiState {
 *  var showSnackbar : Boolean by SideEffect(false)
 * }
 *
 * class MyReducer : Reducer<MyState, MyEvent> {
 *  override fun invoke(state: MyState, event: MyEvent) = when (event) {
 *      is Event.SomeEvent -> state.copy(value = newValue).also { it.showSnackbar = true }
 *  }
 */
fun interface Reducer<S : UiState, out A : Action, E : UiEvent> {
    @MainThread
    fun Store<S, A, E>.reduce(event: E, state: S): S
}

/**
 * SideEffect is a class that holds a single value that can be read only once.
 * After first access it is reset to the initial value.
 * It should be used to handle side effects in the TCA pattern.
 *
 * Example usage:
 * ```
 * var showSnackbar : Boolean by SideEffect(false)
 *
 * // Reducer
 *  when (event) {
 *    is Event.SomeEvent -> state.copy(value = newValue).also { it.showSnackbar = true }
 *  }
 *
 * // Composable
 * if (state.showSnackbar) { // Will reset to false after the first read
 *  scope.launch { snackbarHostState.showSnackbar("Snackbar") }
 * }
 * ```
 *
 */
class SideEffect<Value>(initialValue: Value) {
    private val _initial: Value = initialValue
    private var _value: Value = initialValue

    operator fun getValue(thisObj: Any?, property: KProperty<*>): Value = value

    operator fun setValue(thisObj: Any?, property: KProperty<*>, value: Value) {
        _value = value
    }

    operator fun invoke(value: Value) {
        _value = value
    }

    fun get() = value

    var value: Value
        get() {
            val currentValue = _value
            _value = _initial // reset the value
            return currentValue
        }
        set(newValue) {
            _value = newValue
        }
}


@MainThread
@Composable
inline fun <S : UiState, A : Action, E : UiEvent> store(
    state: S,
    actor: Actor<A, E>,
    reducer: Reducer<S, A, E>,
    key: String? = null,
    initEvent: E? = null,
): Store<S, A, E> = viewModel<Store<S, A, E>>(
    key = reducer::class.simpleName + key,
    factory = viewModelFactory { initializer { Store(actor, reducer, state, initEvent) } }
)
