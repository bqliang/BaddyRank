@file:OptIn(ExperimentalSerializationApi::class)

package com.bqliang.baddy.crawler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import org.koin.core.context.GlobalContext
import java.io.File

suspend inline fun <reified T> T.saveDataAsJson(
    file: File
): Unit = withContext(Dispatchers.IO) {
    val json: Json = GlobalContext.get().get()
    file.outputStream().use { outputStream ->
        json.encodeToStream(this@saveDataAsJson, outputStream)
    }
}
