package com.xiaojinzi.image.bean

class User {

    var id: Int? = null

    var name: String? = null

    var pass: String? = null

    var userToken: String? = null

    constructor(id: Int?, name: String?, pass: String?) {
        this.id = id
        this.name = name
        this.pass = pass
    }

    constructor(name: String?,pass: String?){
        this.name = name;
        this.pass = pass;
    }

    constructor()

}
