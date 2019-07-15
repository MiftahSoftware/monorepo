package com.knoldus.utils

import org.slf4j.{Logger, LoggerFactory}

trait LoggerUtil {

  val logger: Logger = LoggerFactory.getLogger(getClass)

}

object LoggerUtil extends LoggerUtil
