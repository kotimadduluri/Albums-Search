package com.koti.testapp.network

/**
 * @author koti
 * Generic class to handle network response and status
 */
sealed class NetworkResponse<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val status: NetworkResponse.STATUS
) {
    class Success<T>(data: T, status: STATUS = STATUS.SUCCESS) :
        NetworkResponse<T>(data, status = status)

    class Loading<T>(data: T? = null, status: STATUS = STATUS.INPROGRESS) :
        NetworkResponse<T>(data, status = status)

    class Error<T>(throwable: Throwable? = null, data: T? = null, status: STATUS = STATUS.ERROR) :
        NetworkResponse<T>(data, throwable, status = status)

    enum class STATUS {
        INPROGRESS, SUCCESS, ERROR
    }
}