package view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class AverageOfDistanceView: View("BebraView"), KoinComponent {

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            label("Command \'Average of distance\' "){
                textFill = Color.WHITE
                style {
                    fontSize = 20.px
                }
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