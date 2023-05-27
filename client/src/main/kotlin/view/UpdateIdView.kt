package view

import ProxyTokenizator
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class UpdateIdView: View("BebraView"), KoinComponent {

    private val idO = SimpleStringProperty()
    private val name = SimpleStringProperty()
    private val location11 = SimpleStringProperty()
    private val location12 = SimpleStringProperty()
    private val location13 = SimpleStringProperty()
    private val location21 = SimpleStringProperty()
    private val location22 = SimpleStringProperty()
    private val location23 = SimpleStringProperty()
    private val coordinate1 = SimpleStringProperty()
    private val coordinate2 = SimpleStringProperty()
    private val distance = SimpleStringProperty()
    private val result = SimpleStringProperty("")
    val proxyT = ProxyTokenizator()

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            label("Add"){
                textFill = Color.BLACK
                style {
                    fontSize = 20.px
                }
            }
        }
        vbox {
            hbox {
                label("Id: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                    textProperty().bindBidirectional(idO)
                }
            }
            hbox {
                label("name: "){
                    textFill = Color.BLACK
                    style {
                        fontSize = 15.px
                    }
                }
                textfield() {
                    promptText = "..."
                    textProperty().bindBidirectional(name)
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
                    textProperty().bindBidirectional(location11)
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
                    textProperty().bindBidirectional(location12)
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
                    textProperty().bindBidirectional(location13)
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
                    textProperty().bindBidirectional(location21)
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
                    textProperty().bindBidirectional(location22)
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
                    textProperty().bindBidirectional(location23)
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
                    textProperty().bindBidirectional(distance)
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
                    textProperty().bindBidirectional(coordinate1)
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
                    textProperty().bindBidirectional(coordinate2)
                }
            }
            hbox{
                label(result)
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
                        var id = idO.value.toLongOrNull()
                        var nm = name.value
                        var l11 = location11.value.toLongOrNull()
                        var l12 = location12.value.toLongOrNull()
                        var l13 = location13.value.toLongOrNull()
                        var l21 = location21.value.toLongOrNull()
                        var l22 = location22.value.toLongOrNull()
                        var l23 = location23.value.toLongOrNull()
                        var coord1 = coordinate1.value.toLongOrNull()
                        var coord2 = coordinate2.value.toLongOrNull()
                        var dist = distance.value.toLongOrNull()
                        if (id != null && nm != null && l11 != null && l12 != null && l13 != null && l21 != null && l22 != null && l23 != null && coord1 != null && coord2 != null && dist != null){
                            sender(id, nm, l11, l12, l13, l21, l22, l23, coord1, coord2, dist)
                        }
                    }
                }
            }
        }
    }
    private fun sender(id:Long, name: String, l11:Long, l12:Long, l13:Long, l21:Long, l22:Long, l23:Long, coord1: Long, coord2:Long, dist:Long){
        val list = mutableListOf<Any>(name, coord1, coord2, l11, l12, l13, l21, l22, l23, dist, id)
        val getResult = proxyT.proxyTokenizator("update_id", list, BasicInfo.token)
        result.set(getResult)
    }

}