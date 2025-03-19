package koin

import org.koin.dsl.module
import repositories.ConfigRepository
import repositories.FolderRepository
import repositories.TabsRepository
import repositories.TerminalRepository
import viewmodel.EditorPaneViewModel
import viewmodel.FileListViewModel
import viewmodel.TerminalViewModel
import viewmodel.TitleBarViewModel

val appModule = module {
    single { ConfigRepository() }
    single { FolderRepository(get()) }
    single { TabsRepository() }
    single { TerminalRepository() }

    factory { TitleBarViewModel(get(), get()) }
    factory { FileListViewModel(get(), get()) }
    factory { EditorPaneViewModel(get()) }
    factory { TerminalViewModel(get(), get()) }
}