package view

import tornadofx.*

class MyView: View("BebraView") {
    override val root = vbox {
        button("Press me").action {
            replaceWith<Login>()
        }
        label("Waiting")
    }
}

class Login : View("BebraView") {
    override val root = Form()

    private val usernameField = textfield()
    private val passwordField = passwordfield()

    init {
        with(root) {
            fieldset("Registration") {
                field("Username") {
                    add(usernameField)
                }
                field("Password") {
                    add(passwordField)
                }
                button("Register").action {
                    registerUser()
                }
            }
        }
    }

    private fun registerUser() {
        val username = usernameField.text
        val password = passwordField.text

    }
}