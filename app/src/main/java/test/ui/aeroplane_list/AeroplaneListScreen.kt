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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    
    // Filter planes based on search query
    val filteredPlanes = when (uiState) {
        is AeroplaneListUiState.Data -> {
            if (searchQuery.isBlank()) {
                uiState.data?.planes.orEmpty()
            } else {
                uiState.data?.planes?.filter { plane ->
                    plane.aircraftRego.contains(searchQuery, ignoreCase = true) ||
                    plane.aircraftName?.contains(searchQuery, ignoreCase = true) == true ||
                    plane.fleetTypeIataCode?.contains(searchQuery, ignoreCase = true) == true ||
                    plane.subFleet?.contains(searchQuery, ignoreCase = true) == true ||
                    plane.network?.contains(searchQuery, ignoreCase = true) == true
                }.orEmpty()
            }
        }
        else -> emptyList()
    }
    
    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { isSearchActive = false },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Search aircraft...") },
                leadingIcon = {
                    IconButton(onClick = {
                        if (isSearchActive) {
                            isSearchActive = false
                            searchQuery = ""
                        } else {
                            isSearchActive = true
                        }
                    }) {
                        Icon(
                            imageVector = if (isSearchActive) Icons.Default.ArrowBack else Icons.Default.Search,
                            contentDescription = if (isSearchActive) "Back" else "Search"
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isSearchActive) 0.dp else dimensionResource(R.dimen.spacing_4x))
            ) {
                // Search results in the expanded view
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_2x)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_2x))
                ) {
                    if (filteredPlanes.isEmpty() && searchQuery.isNotBlank()) {
                        item {
                            CalloutsComponent(
                                title = "No Results",
                                body = "No aircraft found matching \"$searchQuery\""
                            )
                        }
                    } else if (searchQuery.isBlank()) {
                        item {
                            CalloutsComponent(
                                title = "Search for a plane",
                                body = "Go on, Do it... Im waiting"
                            )
                        }
                    } else {
                        items(
                            items = filteredPlanes
                        ) {
                            when (it) {
                                is Plane -> {
                                    AeroplaneListItem(item = it)
                                }
                            }
                        }
                    }
                }
            }
        }, content = { values ->
            Box(
                modifier = Modifier
                    .padding(values)
                    .fillMaxSize()
            ) {
                // Main content
                Column(
                    modifier = Modifier.fillMaxSize()
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
                                    // Loading is now handled by the dialog overlay
                                    // Keep this empty or show a skeleton screen
                                }
                                is AeroplaneListUiState.Data -> {
                                    if (filteredPlanes.isEmpty() && searchQuery.isNotBlank()) {
                                        item {
                                            CalloutsComponent(
                                                title = "No Results",
                                                body = "No aircraft found matching \"$searchQuery\""
                                            )
                                        }
                                    } else if (uiState.data?.planes?.isEmpty() == true) {
                                        item {
                                            CalloutsComponent(
                                                title = "Empty Response",
                                                body = "There are no items within the plane response. \nGo and add some!"
                                            )
                                        }
                                    } else {
                                        items(
                                            items = filteredPlanes
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
                
                // Search Bar - Full screen overlay when active

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

@Preview(device = Devices.PIXEL_2)
@Composable
private fun AeroplaneListScreenSearchPreview() {
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
            aircraftRego = "VH-ZNA",
            aircraftName = "Adelaide",
            fleetTypeIataCode = "789",
            subFleet = "Dreamliner 787-9",
            gen = 2,
            network = "International",
            ifc = Ifc(
                embodied = false,
                enabled = false,
                supplier = null
            ),
            ife = Ife(
                streaming = true,
                seatback = true,
                overhead = false,
                type = "Seatback",
                system = "System 456",
                supplier = "Company xyz",
                installed = true
            ),
            isQantasLink = false,
            uniqueLivery = "special_livery.png"
        )
    )

    val contract = mockPortfolioViewModelContract(
        AeroplaneListUiState.Data(Portfolio(BigInteger.valueOf(2), "", planeList))
    )
    AerTheme {
        Surface {
            AeroplaneListScreen(contract)
        }
    }
}
