package com.example.kanj.realmsample

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by kanj on 28/03/18.
 */
open class ChangingPerson : RealmObject() {
    @PrimaryKey var name: String? = null
    var age: Int? = null
}