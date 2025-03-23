package com.kotlin.script.ide.repositories

import com.kotlin.script.ide.data.Config
import kotlinx.serialization.json.Json
import java.io.File

open class ConfigRepository {

    private val file = File("config.json")

    open fun loadConfig(): Config {
        val jsonString = file.readText()
        return Json.decodeFromString(Config.serializer(), jsonString)
    }

    open fun saveConfig(config: Config) {
        val jsonString = Json.encodeToString(Config.serializer(), config)
        file.writeText(jsonString)

    }
}