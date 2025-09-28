package com.bqliang.baddy.crawler.captcha

/**
 * 爬虫操作相关的自定义异常的基类。
 */
sealed class CrawlerException(message: String) : Exception(message)

/**
 * 当爬虫意外地遇到需要手动验证的页面时抛出。
 */
class CaptchaRequiredException(
    message: String = "Crawler was blocked by a captcha page."
) : CrawlerException(message)

/**
 * 当网站返回明确的“数据有误”错误时抛出。
 * 这通常意味着请求的参数（如排名类型或比赛类型）是无效的组合。
 */
class InvalidDataException(
    message: String = "The website reported invalid data for the request."
) : CrawlerException(message)