package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase.LoginUseCase
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase.LoginWithGoogleUseCase
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase.RegisterUseCase
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.data.repository.AuthRepositoryImpl
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase.LoginUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase.LoginWithGoogleUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase.RegisterUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindsModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    @Singleton
    abstract fun bindLoginWithGoogleUseCase(
        impl: LoginWithGoogleUseCaseImpl
    ): LoginWithGoogleUseCase

    @Binds
    @Singleton
    abstract fun bindRegisterUseCase(
        impl: RegisterUseCaseImpl
    ): RegisterUseCase
}
