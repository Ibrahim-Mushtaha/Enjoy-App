package com.ix.ibrahim7.rxjavaapplication.util

class ResultRequest<T> private constructor(val status: Status, val message: String?, val data: T?) {
    enum class Status {
        SUCCESS, ERROR, LOADING, EMPTY
    }

    companion object {
        fun <T> success(data: T): ResultRequest<T> {
            return ResultRequest(Status.SUCCESS, null, data)
        }

        fun <T> success(data: T, message: String?): ResultRequest<T> {
            return ResultRequest(Status.SUCCESS, message, data)
        }

        fun <T> error(msg: String?, data: T): ResultRequest<T> {
            return ResultRequest(Status.ERROR, msg, data)
        }

        fun <T> loading(data: T?): ResultRequest<T> {
            return ResultRequest(Status.LOADING, null, data)
        }

        fun <T> empty(data: T?): ResultRequest<T> {
            return ResultRequest(Status.EMPTY, null, data)
        }
    }
}