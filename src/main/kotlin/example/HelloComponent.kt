package example

import vue_wrappers.*

external interface HelloComponent : VueComponent<HelloComponent.Data, HelloComponent.Methods> {

    interface Data {
        var username: String
    }

    interface Methods {
        var changeName: () -> Unit
    }

}

val helloComponent = builder<HelloComponent> {
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


