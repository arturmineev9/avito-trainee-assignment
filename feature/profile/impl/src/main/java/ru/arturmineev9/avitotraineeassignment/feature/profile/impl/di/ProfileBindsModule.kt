package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.repository.ProfileRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileBindsModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository
}
