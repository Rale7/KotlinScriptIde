package com.kotlin.script.ide.koin

import org.koin.dsl.module
import com.kotlin.script.ide.repositories.ConfigRepository
import com.kotlin.script.ide.repositories.FolderRepository
import com.kotlin.script.ide.repositories.TabsRepository
import com.kotlin.script.ide.repositories.TerminalRepository
import com.kotlin.script.ide.viewmodel.EditorPaneViewModel
import com.kotlin.script.ide.viewmodel.FileListViewModel
import com.kotlin.script.ide.viewmodel.TerminalViewModel
import com.kotlin.script.ide.viewmodel.TitleBarViewModel

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