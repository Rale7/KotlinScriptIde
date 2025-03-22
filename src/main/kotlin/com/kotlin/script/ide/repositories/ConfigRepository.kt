package com.kotlin.script.ide.repositories

import com.kotlin.script.ide.data.Config
import kotlinx.serialization.json.Json
import java.io.File

class ConfigRepository {

    private val file = File("config.json")

    fun loadConfig(): Config {
        val jsonString = file.readText()
        return Json.decodeFromString(Config.serializer(), jsonString)
    }

    fun saveConfig(config: Config) {
        val jsonString = Json.encodeToString(Config.serializer(), config)
        file.writeText(jsonString)

    }
}