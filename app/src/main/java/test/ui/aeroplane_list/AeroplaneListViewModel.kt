package test.ui.aeroplane_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.domain.PlaneRepository
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val planeRepository: PlaneRepository
) : ViewModel(), PortfolioViewModelContract {

    private val _uiState: MutableStateFlow<AeroplaneListUiState> = MutableStateFlow(AeroplaneListUiState.Loading())
    override val uiState: StateFlow<AeroplaneListUiState> = _uiState

    init {
        loadPortfolio()
    }

    override fun onRefresh() {
        loadPortfolio()
    }

    private fun loadPortfolio() {
        viewModelScope.launch {
            _uiState.update { AeroplaneListUiState.Loading() }
            try{
                planeRepository.getPortfolio()?.let { portfolio ->
                    _uiState.update { AeroplaneListUiState.Data(portfolio) }
                } ?: _uiState.update { AeroplaneListUiState.Error }
            }
            catch (networkExceptions: Exception){
                _uiState.update { AeroplaneListUiState.Error }
            }
        }
    }
}

interface PortfolioViewModelContract {
    val uiState: StateFlow<AeroplaneListUiState>
    fun onRefresh()
}

fun mockPortfolioViewModelContract(uiState: AeroplaneListUiState) =
    object : PortfolioViewModelContract {
        override val uiState: StateFlow<AeroplaneListUiState> = MutableStateFlow(uiState)
        override fun onRefresh() {}
    }
