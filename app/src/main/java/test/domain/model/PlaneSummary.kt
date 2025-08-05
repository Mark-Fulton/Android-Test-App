package test.domain.model

import java.math.BigInteger

data class Portfolio(
    val totalAmount: BigInteger,
    val portfolioName: String? = null,
    val planes: List<Plane>
)

data class Plane(
    val aircraftRego: String,
    val aircraftName: String? = null,
    val fleetTypeIataCode: String? = null,
    val subFleet: String? = null,
    val gen: Int? = null,
    val network: String? = null,
    val ifc: Ifc? = null,
    val ife: Ife? = null,
    val isQantasLink: Boolean? = null,
    val uniqueLivery: String? = null
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