package view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class AddIfMaxView: View("BebraView"), KoinComponent {

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            label("Add if max"){
                textFill = Color.BLACK
                style {
                    fontSize = 20.px
                }
            }
        }
        vbox {
            hbox {
                label("name: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 11: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 12: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 13: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 21: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 22: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Location 23: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Distance: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Coordinate X: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
            hbox {
                label("Coordinate Y: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                }
            }
        }
        vbox(3, Pos.TOP_LEFT) {
            prefHeight = 160.0
            hbox(5) {
                button("Назад") {
                    prefWidth = 120.0
                    prefHeight = 35.0
                    style {
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        replaceWith<WorkingPage>()
                    }
                }
                button("Execute") {
                    prefWidth = 120.0
                    prefHeight = 35.0
                    style {
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        replaceWith<WorkingPage>()
                    }
                }
            }
        }
    }
}