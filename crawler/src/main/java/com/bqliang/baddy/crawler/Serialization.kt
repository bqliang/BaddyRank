package com.bqliang.baddy.crawler

import kotlinx.serialization.json.Json

fun createJson(): Json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true

}