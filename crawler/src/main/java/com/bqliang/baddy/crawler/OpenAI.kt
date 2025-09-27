package com.bqliang.baddy.crawler

import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost

private const val DEEPSEEK_BASE_URL = "https://api.deepseek.com/v1"

fun createOpenAIClient(): OpenAI {
    val apiKey = System.getenv("DEEPSEEK_KEY")
    if (apiKey.isNullOrEmpty()) {
        throw IllegalStateException("DEEPSEEK_KEY is not set.")
    }

    return OpenAI(
        host = OpenAIHost(DEEPSEEK_BASE_URL),
        token = apiKey,
    )
}
