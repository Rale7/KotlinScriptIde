package data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppState {

    private val _folder = MutableStateFlow(Folder())
    val folder = _folder.asStateFlow()

    fun changeFolder(path: String) {
        _folder.value = folder.value.copy(path = path)
    }

}
