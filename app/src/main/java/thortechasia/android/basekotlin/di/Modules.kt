package thortechasia.android.basekotlin.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import thortechasia.android.basekotlin.data.db.AppDatabase
import thortechasia.android.basekotlin.data.remote.createWebService
import thortechasia.android.basekotlin.data.remote.provideOkHttpClient
import thortechasia.android.basekotlin.data.pref.PreferencesHelper
import thortechasia.android.basekotlin.data.remote.AuthInterceptor
import thortechasia.android.basekotlin.data.repository.TeamRepository
import thortechasia.android.basekotlin.data.repository.TeamRepositoryImpl
import thortechasia.android.basekotlin.data.remote.service.TeamService
import thortechasia.android.basekotlin.presentation.favorite.FavoriteViewModel
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.momakan.utils.scheduler.AppSchedulerProvider
import thortechasia.android.momakan.utils.scheduler.SchedulerProvider

val networkModule = module {
    single { PreferencesHelper(androidContext()) }
    single { provideOkHttpClient(get(), get()) }
    single { createWebService<TeamService>(get()) }
    single { AuthInterceptor(get()) }
    //add this line if u use rxJava instead courotine
//    single { AppSchedulerProvider() as SchedulerProvider }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(),get()) }
}

val dataBaseModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().teamDao() }
}


val repositoryModule = module {

    single<TeamRepository> { TeamRepositoryImpl(get(), get()) }
}

val myAppModule = listOf(networkModule, repositoryModule, viewModelModule, dataBaseModule)