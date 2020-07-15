package com.raywenderlich.android.taskie.model

/** Class to handle errors and success results in a user friendly way*/
//Generic result
sealed class Result<out T : Any>

/**
 * [data] generic data type of property : can be a String, List or whatever data type
 * Generic result type of on api request success*/
data class Success<out T : Any>(val data: T) : Result<T>()

/**
 * [error] hold the error property
 * Result out f nothing*/
data class Failure(val error: Throwable?) : Result<Nothing>()