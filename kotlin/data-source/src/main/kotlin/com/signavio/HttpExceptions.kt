package com.signavio

/**
 * Is thrown when a HTTP status 404 - Not Found shall be returned
 */
class NotFoundException(override var message:String): Exception()

/**
 * Is thrown when a HTTP status 400 - Bad Request shall be returned
 */
class BadRequestException(override var message:String): Exception()