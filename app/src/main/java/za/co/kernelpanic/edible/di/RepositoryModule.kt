package za.co.kernelpanic.edible.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.kernelpanic.edible.repository.EdibleRepository
import za.co.kernelpanic.edible.repository.db.EdibleRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEdibleRepository(impl: EdibleRepositoryImpl): EdibleRepository
}