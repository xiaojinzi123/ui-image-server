package com.xiaojinzi.image

class Result<T> {

    var data: T? = null

    var msg: String? = null

    var isSuccess: Boolean = false

    constructor(data: T) {
        this.data = data
        isSuccess = true
        msg = OK
    }

    constructor(msg: String) {
        var msg = msg
        this.msg = msg
        isSuccess = false
        msg = ERROR
    }

    companion object {
        val OK = "ok"
        val ERROR = "error"
    }

}
