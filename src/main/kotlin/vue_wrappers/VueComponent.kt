@file:Suppress("unused", "MayBeConstant")

package vue_wrappers

//inline fun <reified T : Any> addStaticMembersTo(source: Any) {
//    val c = T::class.js.asDynamic()
//    val ownNames = js("Object").getOwnPropertyNames(source) as Array<String>
//    val protoNames = js("Object").getOwnPropertyNames(source.asDynamic().constructor.prototype) as Array<String>
//
//    for (name in ownNames + protoNames) {
//        c[name] = source.asDynamic()[name]
//    }
//}

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

external interface MyComponent : VueComponent<MyComponent.Data, MyComponent.Methods> {

    interface Data {
        var username: String
    }

    interface Methods {
        var changeName: () -> Unit
    }

}

fun myComponent() = builder<MyComponent> {
    this.name = "hello-component"
    this.template = """<ul><li>Hello, {{username}} <button @click="changeName">Change</button></li></ul>"""
    this.data = defineData {
        this.username = "Freewind"
    }
    this.methods = defineMethods {
        this.changeName = {
            self().username += "!"
        }
    }
}


fun main(args: Array<String>) {
    Vue.component("hello-component", myComponent())

    Vue(object {
        val el = "#hello"
        val data = object {
            val name = "Hello Demo"
        }
    })

}