@file:Suppress("unused", "MayBeConstant")

package vue_wrappers

@JsName("Vue")
external class Vue(options: dynamic) {
    companion object {
        fun component(name: String, options: dynamic)
    }
}

inline fun <T> jsObj(): T = js("{}")

inline fun <T> thisAs(): T = js("this")

inline fun <T> builder(block: T.() -> Unit): T {
    val obj = jsObj<T>()
    block(obj)
    return obj
}

external interface VueComponent<DATA, METHODS> {
    var name: String
    var template: String
    var data: () -> DATA
    var methods: METHODS
}

inline fun <DATA, METHODS> VueComponent<DATA, METHODS>.self(): DATA = thisAs()

fun <DATA, METHODS> VueComponent<DATA, METHODS>.defineMethods(block: METHODS.() -> Unit): METHODS {
    val methods = jsObj<METHODS>()
    block(methods)
    return methods
}

fun <DATA, METHODS> VueComponent<DATA, METHODS>.defineData(block: DATA.() -> Unit): () -> DATA {
    return {
        val data = jsObj<DATA>()
        block(data)
        data
    }
}
