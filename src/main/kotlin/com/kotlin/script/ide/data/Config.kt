package com.kotlin.script.ide.data

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val currentDirectory: String,
)
