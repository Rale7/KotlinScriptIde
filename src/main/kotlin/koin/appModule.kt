package koin

import data.AppState
import org.koin.dsl.module
import viewmodel.FileListViewModel
import viewmodel.TitleBarViewModel

val appModule = module {
    single { AppState() }

    factory { TitleBarViewModel(get()) }
    factory { FileListViewModel(get()) }
}