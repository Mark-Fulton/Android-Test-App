package test.domain

import test.domain.model.Portfolio

interface PlaneRepository {
    suspend fun getPortfolio(): Portfolio?
}