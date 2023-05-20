package view

import commandsHelpers.GetToken
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.GridPane
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
    val getToken = GetToken()
    //val textProperty = SimpleStringProperty("")
    val textPropertyRes = SimpleStringProperty("")

    init {
        with(root) {
            fieldset("Registration") {
                field("Login") {
                    add(usernameField)
                }
                field("Password") {
                    add(passwordField)
                }
                button("Register").action {
                    val token = registerUser()
                    textPropertyRes.set(token)
                }
                label(textPropertyRes)
            }
        }
    }

    private fun registerUser(): String {
        val login = usernameField.text
        val pas = passwordField.text
        if (login.length > 0 && pas.length > 0){
            if (getToken.loginAndGetToken(login, pas)){
                return getToken.token
            }else{
                return "This login is already registered"
            }
        }else{
            return "Not valid"
        }
    }
}