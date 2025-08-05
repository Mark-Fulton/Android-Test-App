package test.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
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
    Column(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.spacing_2x))
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.aircraftRego,
                Modifier.width(64.dp),
                maxLines = integerResource(R.integer.plane_list_item_actors_max_lines),
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(
                Modifier
                    .padding(dimensionResource(R.dimen.spacing_1x))
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.spacing_2x))
            ) {
                Text(
                    text = item.aircraftName!!,
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = "- " + "Network: ${item.network}",
                    maxLines = integerResource(R.integer.plane_list_item_actors_max_lines),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "- " + "Generation: ${item.gen}",
                    maxLines = integerResource(R.integer.plane_list_item_actors_max_lines),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            if(item.isQantasLink != null){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Within Network",
                        maxLines = integerResource(R.integer.plane_list_item_actors_max_lines),
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${item.isQantasLink}",
                        maxLines = integerResource(R.integer.plane_list_item_actors_max_lines),
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_2x)),
            color = Color.LightGray
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
