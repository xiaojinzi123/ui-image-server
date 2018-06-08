package com.xiaojinzi.image.bean

class Action {

    object ACTION {

        val ACTION_ADD:String = "add"
        val ACTION_DELETE:String = "delete"

    }

    var id: Int? = null

    var action: String? = null

    var source: String? = null

    var target: String? = null

    constructor()

    /**
     * @see ACTION
     */
    constructor(action: String?, source: String?) {
        this.action = action
        this.source = source
    }

    constructor(action: String?, source: String?, target: String?) {
        this.action = action
        this.source = source
        this.target = target
    }

}
