package com.alxnns1.utils

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Error(val message: String = ""): ResultOf<Nothing>()
    object Loading : ResultOf<Nothing>()
    object Initial : ResultOf<Nothing>()
}