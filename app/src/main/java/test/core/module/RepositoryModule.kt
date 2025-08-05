package test.core.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.data.service.plane_list.PlaneListService
import test.domain.PlaneRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providePlaneRepository(planeListService: PlaneListService): PlaneRepository =
        planeListService
}
