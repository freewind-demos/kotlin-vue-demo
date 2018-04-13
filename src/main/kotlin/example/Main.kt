@file:Suppress("unused", "MayBeConstant")

package example

import vue_wrappers.Vue

fun main(args: Array<String>) {
    Vue.component(helloComponent.name, helloComponent)

    Vue(object {
        val el = "#hello"
        val data = object {
            val name = "Hello Demo"
        }
    })

}