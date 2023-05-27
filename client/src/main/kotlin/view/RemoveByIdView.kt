package view

import ProxyTokenizator
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class RemoveByIdView: View("BebraView"), KoinComponent {

    private val filternum = SimpleStringProperty()
    private val result = SimpleStringProperty("")
    val proxyT = ProxyTokenizator()

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
                label("distance: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                    textProperty().bindBidirectional(filternum)
                }
            }
        }
        hbox{
            label(result)
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
                        var nm = filternum.value.toLongOrNull()
                        if (nm != null){
                            sender(nm)
                        }
                    }
                }
            }
        }
    }

    private fun sender(nm: Long){
        val list = mutableListOf<Any>()
        list.add(nm)
        val getResult = proxyT.proxyTokenizator("remove_by_id", list, BasicInfo.token)
        result.set(getResult)
    }

}