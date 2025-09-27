package com.bqliang.baddy.crawler.captcha

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI

/**
 * 使用 AI 来解决文本验证码的实现。
 */
class AICaptchaSolver(
    private val openAIClient: OpenAI,
) : CaptchaSolver {
    private companion object {
        const val MODEL_ID = "deepseek-chat"
        const val SYSTEM_PROMPT = "直接输出结果, 不要任何多余的解释! 也不要任何的前后缀以及单位等等"
    }

    override suspend fun solveTextCaptcha(question: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(MODEL_ID),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = SYSTEM_PROMPT,
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = question,
                )
            )
        )

        val completion = openAIClient.chatCompletion(chatCompletionRequest)
        val result = completion.choices.firstOrNull()?.message?.content

        return if (result.isNullOrEmpty()) {
            throw IllegalStateException("DeepSeek return a null or empty response.")
        } else result
    }
}