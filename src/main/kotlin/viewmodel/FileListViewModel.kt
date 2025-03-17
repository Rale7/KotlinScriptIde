package viewmodel

import data.AppState

class FileListViewModel(private val appState: AppState) {

    val path = appState.folder

}