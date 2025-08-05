package test.ui.aeroplane_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import test.R
import test.domain.model.Ifc
import test.domain.model.Ife
import test.domain.model.Portfolio
import test.domain.model.Plane
import test.ui.components.CalloutsComponent
import test.ui.components.AeroplaneListItem
import test.ui.theme.AerTheme
import java.math.BigDecimal
import java.math.BigInteger


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AeroplaneListScreen(contract: PortfolioViewModelContract) {
    val uiState = contract.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Qantas Aeroplane App")
                },
            )
        }, content = { values ->
            Column(
                modifier = Modifier
                    .padding(values)
                    .fillMaxSize(),
            ) {
                Box(
                    Modifier
                        .padding(dimensionResource(R.dimen.spacing_4x))
                        .fillMaxWidth()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_2x)),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_2x))
                    ) {
                        when (uiState) {
                            is AeroplaneListUiState.Loading -> {
                                item {
                                    CalloutsComponent(
                                        title = "Please Wait",
                                        body = "We are loading your planes..."
                                    )
                                }
                            }
                            is AeroplaneListUiState.Data -> {
                                if (uiState.data?.planes?.isEmpty() == true) {
                                    item {
                                        CalloutsComponent(
                                            title = "Empty Response",
                                            body = "There are no items within the plane response. \nGo and add some!"

                                        )
                                    }
                                }
                                else {
                                    items(
                                        items = uiState.data?.planes.orEmpty()
                                    ) {
                                        when (it) {
                                            is Plane -> {
                                                AeroplaneListItem(item = it)
                                            }
                                        }
                                    }
                                }
                            }
                            is AeroplaneListUiState.Error -> {
                                item("error", span = { GridItemSpan(maxLineSpan) }) {
                                    CalloutsComponent(
                                        title = "Please try again",
                                        body = "There has been an error retrieving your portfolio"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


@Preview
@Composable
private fun AeroplaneListScreenPreview() {
    val planeList = mutableListOf<Plane>()

    planeList.add(
        Plane(
            aircraftRego = "VH-VXA",
            aircraftName = "Broome",
            fleetTypeIataCode = "738",
            subFleet = "737-800 Original -75T MTOW",
            gen = 1,
            network = "Domestic",
            ifc = Ifc(
                embodied = true,
                enabled = true,
                supplier = "Company 123"
            ),
            ife = Ife(
                streaming = false,
                seatback = false,
                overhead = true,
                type = "Overhead",
                system = "System 123",
                supplier = "Company abc",
                installed = null
            ),
            isQantasLink = false,
            uniqueLivery = null
        )
    )

    planeList.add(
        Plane(
            aircraftRego = "VH-VXA",
            aircraftName = "Broome",
            fleetTypeIataCode = "738",
            subFleet = "737-800 Original -75T MTOW",
            gen = 1,
            network = "Domestic",
            ifc = Ifc(
                embodied = true,
                enabled = true,
                supplier = "Company 123"
            ),
            ife = Ife(
                streaming = false,
                seatback = false,
                overhead = true,
                type = "Overhead",
                system = "System 123",
                supplier = "Company abc",
                installed = null
            ),
            isQantasLink = false,
            uniqueLivery = null
        )
    )

    val contract = mockPortfolioViewModelContract(
        AeroplaneListUiState.Data(Portfolio(BigInteger.TEN, "", planeList))
    )
    AerTheme {
        Surface {
            AeroplaneListScreen(contract)
        }
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
private fun AeroplaneListScreenErrorPreview() {
    val contract = mockPortfolioViewModelContract(
        AeroplaneListUiState.Error
    )
    AerTheme {
        Surface {
            AeroplaneListScreen(contract)
        }
    }
}
