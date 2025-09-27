package com.bqliang.baddy.crawler.captcha

/**
 * 验证码解决器接口
 * 定义了解决爬虫遇到的验证问题的契约
 */
interface CaptchaSolver {
    /**
     * 解决一个基于文本的问题（例如数学计算）
     * @param question 需要解答的问题文本
     * @return 问题的答案字符串
     */
    suspend fun solveTextCaptcha(question: String): String
}