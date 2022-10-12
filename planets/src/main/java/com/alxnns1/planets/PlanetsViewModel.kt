package com.alxnns1.planets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxnns1.planets.network.PlanetsRepository
import com.alxnns1.utils.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetsViewModel @Inject constructor(
    private val planetsRepository: PlanetsRepository
): ViewModel() {

    private val _planetsState = MutableStateFlow<ResultOf<List<String>>>(ResultOf.Initial)
    val planetsState: StateFlow<ResultOf<List<String>>> = _planetsState.asStateFlow()

    fun getPlanets(query: String = "") {
        viewModelScope.launch {
            _planetsState.emit(ResultOf.Loading)
            _planetsState.emit(planetsRepository.getPlanets(query))
        }
    }
}