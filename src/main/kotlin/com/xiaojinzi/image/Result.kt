package com.xiaojinzi.image

class Result<T> {

    var data: T? = null

    var msg: String? = null

    var errorCode :Int = ERROR_NORERMAL

    var isSuccess: Boolean = false

    constructor(data: T) {
        this.data = data
        isSuccess = true
        msg = OK
    }

    constructor(msg: String?) {
        var msg = msg
        this.msg = msg
        isSuccess = false
    }



    constructor(msg: String?,errorCode:Int) {
        var msg = msg
        this.msg = msg
        this.errorCode = errorCode
        isSuccess = false

    }

    constructor(data: T?, msg: String?, isSuccess: Boolean) {
        this.data = data
        this.msg = msg
        this.isSuccess = isSuccess
    }


    companion object {
        val OK = "ok"
        val ERROR = "error"
        var ERROR_NORERMAL = 0;
        var ERROR_TOKENEXPIRE = 1;
    }

}
