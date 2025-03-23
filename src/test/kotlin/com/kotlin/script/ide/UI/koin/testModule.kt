package com.kotlin.script.ide.UI.koin

import com.kotlin.script.ide.UI.repositories.TestConfigRepository
import com.kotlin.script.ide.repositories.*
import com.kotlin.script.ide.viewmodel.EditorPaneViewModel
import com.kotlin.script.ide.viewmodel.FileListViewModel
import com.kotlin.script.ide.viewmodel.TerminalViewModel
import com.kotlin.script.ide.viewmodel.TitleBarViewModel
import org.koin.dsl.module

val testModule = module {
    single { TestConfigRepository() as ConfigRepository }
    single { FolderRepository(get()) }
    single { TabsRepository() }
    single { TerminalRepository() }

    factory { TitleBarViewModel(get(), get()) }
    factory { FileListViewModel(get(), get()) }
    factory { EditorPaneViewModel(get()) }
    factory { TerminalViewModel(get(), get()) }
}