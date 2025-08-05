package test.data.service.plane_list.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import test.domain.model.Portfolio
import test.domain.model.Plane

@JsonClass(generateAdapter = true)
data class AeroplaneResponse(
    @Json(name = "fleet")
    val fleet: List<Aircraft>
)

// Mapper from AeroplaneResponse to Portfolio (domain)
fun AeroplaneResponse.toPortfolio(): Portfolio {
    return Portfolio(
        totalAmount = fleet.size.toBigInteger(), // Example: total number of planes
        planes = fleet.map { it.toPlane() }
    )
}

// Mapper from Aircraft (JSON) to Plane (domain)
fun Aircraft.toPlane(): Plane {
    return Plane(
        aircraftRego = this.aircraftRego,
        aircraftName = this.aircraftName,
        fleetTypeIataCode = this.fleetTypeIataCode,
        subFleet = this.subFleet,
        gen = this.gen,
        network = this.network,
        ifc = this.ifc?.toDomain(),
        ife = this.ife?.toDomain(),
        isQantasLink = this.isQantasLink,
        uniqueLivery = this.uniqueLivery
    )
}

// Mapper for Ifc
fun Ifc.toDomain(): test.domain.model.Ifc {
    return test.domain.model.Ifc(
        embodied = this.embodied,
        enabled = this.enabled,
        supplier = this.supplier
    )
}

// Mapper for Ife
fun Ife.toDomain(): test.domain.model.Ife {
    return test.domain.model.Ife(
        streaming = this.streaming,
        seatback = this.seatback,
        overhead = this.overhead,
        type = this.type,
        system = this.system,
        supplier = this.supplier,
        installed = this.installed
    )
}

data class Fleet(
    val fleet: List<Aircraft>
)

data class Aircraft(
    val aircraftName: String? = null,
    val fleetTypeIataCode: String? = null,
    val subFleet: String? = null,
    val gen: Int? = null,
    val network: String? = null,
    val ifc: Ifc? = null,
    val ife: Ife? = null,
    val isQantasLink: Boolean? = null,
    val uniqueLivery: String? = null,
    val aircraftRego: String
)

data class Ifc(
    val embodied: Boolean? = null,
    val enabled: Boolean? = null,
    val supplier: String? = null
)

data class Ife(
    val streaming: Boolean? = null,
    val seatback: Boolean? = null,
    val overhead: Boolean? = null,
    val type: String? = null,
    val system: String? = null,
    val supplier: String? = null,
    val installed: Boolean? = null
)