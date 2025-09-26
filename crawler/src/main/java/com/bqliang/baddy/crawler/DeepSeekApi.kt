package com.bqliang.baddy.crawler

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost

object DeepSeekApi {
    private const val DEEPSEEK_MODEL_ID = "deepseek-chat"
    private const val DEEPSEEK_BASE_URL = "https://api.deepseek.com/v1"
    private val DEEPSEEK_KEY by lazy {
        System.getenv("DEEPSEEK_KEY")
    }

    suspend fun getAnswer(question: String): Result<String> = runCatching {
        val openAI = OpenAI(
            host = OpenAIHost(DEEPSEEK_BASE_URL),
            token = DEEPSEEK_KEY,
        )

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(DEEPSEEK_MODEL_ID), // 指定要使用的模型
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "直接输出结果, 不要任何多余的解释! 也不要任何的前后缀以及单位等等"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = question,
                )
            )
        )

        val completion = openAI.chatCompletion(chatCompletionRequest)
        val result = completion.choices.firstOrNull()?.message?.content

        if (result.isNullOrEmpty()) {
            throw IllegalStateException("API returned a null or empty response.")
        } else {
            result
        }
    }
}