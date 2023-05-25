package view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class ExitServerView: View("BebraView"), KoinComponent {

    val textPropertyRes = SimpleStringProperty("")

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            hbox {
                label("Command \'Exit Server\' ") {
                    textFill = Color.WHITE
                    style {
                        fontSize = 20.px
                    }
                }
            }
            hbox {
                text(textPropertyRes)
            }
        }
        vbox(3, Pos.BOTTOM_LEFT) {
            prefHeight = 160.0
            vbox {
                button("Назад") {
                    prefWidth = 122.0
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
            vbox {
                button("Execute") {
                    prefWidth = 122.0
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