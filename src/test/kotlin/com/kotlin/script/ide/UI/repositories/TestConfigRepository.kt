package com.kotlin.script.ide.UI.repositories

import com.kotlin.script.ide.data.Config
import com.kotlin.script.ide.repositories.ConfigRepository

class TestConfigRepository : ConfigRepository() {

    var config = Config(currentDirectory = ".")

    override fun loadConfig(): Config {
        return config
    }

    override fun saveConfig(config: Config) {
        this.config = config
    }

}