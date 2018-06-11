package com.xiaojinzi.image.bean

class Action {

    object ACTION {

        val ACTION_ADD:String = "add"
        val ACTION_DELETE:String = "delete"
        val ACTION_MOVE:String = "move"

    }

    var id: Int? = null

    var action: String? = null

    var source: String? = null

    var sourceCategory: String? = null

    var target: String? = null

    var targetCategory: String? = null

    constructor()

    /**
     * @see ACTION
     */
    constructor(action: String?, source: String?, sourceCategory: String?) {
        this.action = action
        this.source = source
        this.sourceCategory = sourceCategory
    }

    /**
     * @see ACTION
     */
    constructor(action: String?, source: String?, sourceCategory: String?, target: String?, targetCategory: String?) {
        this.action = action
        this.source = source
        this.sourceCategory = sourceCategory
        this.target = target
        this.targetCategory = targetCategory
    }




}
