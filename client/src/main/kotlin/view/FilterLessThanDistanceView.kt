package view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class FilterLessThanDistanceView: View("BebraView"), KoinComponent {

    val textPropertyRes = SimpleStringProperty("")

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            label("Command \'Filter less than distance\' "){
                textFill = Color.WHITE
                style {
                    fontSize = 20.px
                }
            }

        }
        vbox {
            hbox {
                label("distance: "){
                    textFill = Color.WHITE
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "Введите текст..."
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