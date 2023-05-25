package app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val blackBackground by cssclass()
        val whiteText by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        blackBackground {
            backgroundColor += c("#000000")
        }

        whiteText {
            textFill = c("#FFFFFF")
        }
    }
}