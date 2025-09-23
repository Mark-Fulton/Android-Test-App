package test.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.R
import test.domain.model.Ifc
import test.domain.model.Ife
import test.domain.model.Plane
import test.ui.theme.AerTheme
import java.math.BigDecimal

@Composable
fun AeroplaneListItem(item: Plane) {
    var ifcExpanded by remember { mutableStateOf(false) }
    var ifeExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.spacing_2x)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_4x))
        ) {
            // Header with Aircraft Registration and Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.aircraftRego,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                
                item.aircraftName?.let { name ->
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_2x)))
                    Text(
                        text = "\"$name\"",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_4x)))
            
            // Fleet Information
            item.fleetTypeIataCode?.let { iataCode ->
                DetailRow(
                    label = "Fleet Type IATA Code",
                    value = iataCode
                )
            }
            
            item.subFleet?.let { subFleet ->
                DetailRow(
                    label = "Sub Fleet",
                    value = subFleet
                )
            }
            
            item.gen?.let { generation ->
                DetailRow(
                    label = "Generation",
                    value = generation.toString()
                )
            }
            
            item.network?.let { network ->
                DetailRow(
                    label = "Network",
                    value = network
                )
            }
            
            item.isQantasLink?.let { isQantasLink ->
                DetailRow(
                    label = "Qantas Link",
                    value = if (isQantasLink) "Yes" else "No"
                )
            }
            
            item.uniqueLivery?.let { livery ->
                DetailRow(
                    label = "Unique Livery",
                    value = livery
                )
            }
            
            // IFC Information
            item.ifc?.let { ifc ->
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_2x)))
                
                ExpandableSection(
                    title = "Internet Connectivity (IFC)",
                    isExpanded = ifcExpanded,
                    onToggle = { ifcExpanded = !ifcExpanded }
                ) {
                    ifc.embodied?.let { embodied ->
                        DetailRow(
                            label = "Embodied",
                            value = if (embodied) "Yes" else "No"
                        )
                    }
                    
                    ifc.enabled?.let { enabled ->
                        DetailRow(
                            label = "Enabled",
                            value = if (enabled) "Yes" else "No"
                        )
                    }
                    
                    ifc.supplier?.let { supplier ->
                        DetailRow(
                            label = "Supplier",
                            value = supplier
                        )
                    }
                }
            }
            
            // IFE Information
            item.ife?.let { ife ->
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_2x)))
                
                ExpandableSection(
                    title = "In-Flight Entertainment (IFE)",
                    isExpanded = ifeExpanded,
                    onToggle = { ifeExpanded = !ifeExpanded }
                ) {
                    ife.type?.let { type ->
                        DetailRow(
                            label = "Type",
                            value = type
                        )
                    }
                    
                    ife.system?.let { system ->
                        DetailRow(
                            label = "System",
                            value = system
                        )
                    }
                    
                    ife.supplier?.let { supplier ->
                        DetailRow(
                            label = "Supplier",
                            value = supplier
                        )
                    }
                    
                    ife.streaming?.let { streaming ->
                        DetailRow(
                            label = "Streaming",
                            value = if (streaming) "Available" else "Not Available"
                        )
                    }
                    
                    ife.seatback?.let { seatback ->
                        DetailRow(
                            label = "Seatback",
                            value = if (seatback) "Available" else "Not Available"
                        )
                    }
                    
                    ife.overhead?.let { overhead ->
                        DetailRow(
                            label = "Overhead",
                            value = if (overhead) "Available" else "Not Available"
                        )
                    }
                    
                    ife.installed?.let { installed ->
                        DetailRow(
                            label = "Installed",
                            value = if (installed) "Yes" else "No"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() }
                .padding(vertical = dimensionResource(R.dimen.spacing_1x)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_2x))
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_1x)))
                content()
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(140.dp)
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
private fun AeroplaneListItemPreview() {
    AerTheme {
        AeroplaneListItem(
            item = Plane(
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
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
private fun AeroplaneListItemExpandedPreview() {
    AerTheme {
        AeroplaneListItem(
            item = Plane(
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
                    installed = true
                ),
                isQantasLink = false,
                uniqueLivery = "special_livery.png"
            )
        )
    }
}
