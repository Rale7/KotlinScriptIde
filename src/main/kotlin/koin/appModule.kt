package koin

import org.koin.dsl.module
import repositories.FolderRepository
import repositories.TabsRepository
import viewmodel.EditorPaneViewModel
import viewmodel.FileListViewModel
import viewmodel.TitleBarViewModel

val appModule = module {
    single { FolderRepository() }
    single { TabsRepository() }

    factory { TitleBarViewModel(get(), get()) }
    factory { FileListViewModel(get(), get()) }
    factory { EditorPaneViewModel(get()) }
}