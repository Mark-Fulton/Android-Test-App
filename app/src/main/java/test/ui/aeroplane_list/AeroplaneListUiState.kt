package test.ui.aeroplane_list

import test.domain.model.Portfolio
import java.math.BigInteger

sealed class AeroplaneListUiState(val data: Portfolio?) {

    class Loading(list: Portfolio = Portfolio(BigInteger.ZERO, "Loading...", emptyList())) : AeroplaneListUiState(list)

    class Data(portfolio: Portfolio) : AeroplaneListUiState(portfolio)

    object Error : AeroplaneListUiState(null)

    val isRefreshing get() = this is Loading
}