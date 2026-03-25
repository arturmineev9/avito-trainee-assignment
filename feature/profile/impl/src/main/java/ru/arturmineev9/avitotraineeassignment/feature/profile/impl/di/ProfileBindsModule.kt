package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.GetProfileUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.LogoutUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.ThemeUseCases
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UpdateNameUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UploadAvatarUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.repository.ProfileRepositoryImpl
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.GetProfileUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.LogoutUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.ThemeUseCasesImpl
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.UpdateNameUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.UploadAvatarUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileBindsModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindGetProfileUseCase(
        impl: GetProfileUseCaseImpl
    ): GetProfileUseCase

    @Binds
    @Singleton
    abstract fun bindLogoutUseCase(
        impl: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds
    @Singleton
    abstract fun bindThemeUseCases(
        impl: ThemeUseCasesImpl
    ): ThemeUseCases

    @Binds
    @Singleton
    abstract fun bindUpdateNameUseCase(
        impl: UpdateNameUseCaseImpl
    ): UpdateNameUseCase

    @Binds
    @Singleton
    abstract fun bindUploadAvatarUseCase(
        impl: UploadAvatarUseCaseImpl
    ): UploadAvatarUseCase
}
