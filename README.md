Kotlin JS Hello World Demo
=======================

Hello world from kotlin-js.


Run:

```
./gradlew clean compileKotlin2Js
npm install
npm install -g http-server
http-server . -o
```

It will open <http://localhost:8080/index.html> automatically, you can click on the buttons on the page.

Issue
-----

In file `src/main/kotlin/vue_wrappers/VueComponent.kt`, around line 70, you can see:

```
this.methods = defineMethods { data ->
    this.changeName = {
        //  data().username += "!" // This doesn't work
        thisAs<MyComponent.Data>().username += "!"
    }
}
```

The code is working, but if you uncomment the `data().username += "!"` line, and click the buttons, it will report error in Browser's developer console:

```
Uncaught TypeError: Cannot read property 'username' of undefined
    at VueComponent.<anonymous> (kotlin-vue-demo.js:42)
    at invoker (vue.js:2034)
    at HTMLButtonElement.fn._withTask.fn._withTask (vue.js:1833)
```

Related question: <https://stackoverflow.com/questions/49794446/is-it-possible-to-pass-inline-functions-in-kotlin?noredirect=1#comment86615517_49794446>


