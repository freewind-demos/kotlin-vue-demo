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
external class Vue(options: dynamic)

@JsName("Vue")
external object MyVueComponent {
    fun component(name: String, options: dynamic)
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

fun <DATA, METHODS> VueComponent<DATA, METHODS>.defineMethods(block: METHODS.(() -> DATA) -> Unit): METHODS {
    val methods = jsObj<METHODS>()
    block(methods, ::thisAs)
    return methods
}

external interface MyComponent : VueComponent<MyComponent.Data, MyComponent.Methods> {

    interface Data {
        var username: String
    }

    interface Methods {
        var changeName: () -> Unit
    }

}


fun main(args: Array<String>) {
    MyVueComponent.component("hello-component", builder<MyComponent> {
        this.name = "hello-component"
        this.data = {
            jsObj<MyComponent.Data>().apply {
                this.username = "Freewind"
            }
        }
        this.methods = defineMethods { data ->
            this.changeName = {
                //  data().username += "!" // This doesn't work
                thisAs<MyComponent.Data>().username += "!"
            }
        }
        this.template = """<ul><li>Hello, {{username}} <button @click="changeName">Change</button></li></ul>"""
    })

    Vue(object {
        val el = "#hello"
        val data = object {
            val name = "Hello Demo"
        }
    })

}