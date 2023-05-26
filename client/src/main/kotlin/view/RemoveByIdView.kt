package view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class RemoveByIdView: View("BebraView"), KoinComponent {

    val textPropertyRes = SimpleStringProperty("")

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            label("Remove by Id"){
                textFill = Color.BLACK
                style {
                    fontSize = 20.px
                }
            }

        }
        vbox {
            hbox {
                label("id: "){
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
                text(textPropertyRes)
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