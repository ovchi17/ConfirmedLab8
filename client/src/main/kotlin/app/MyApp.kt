package app

import javafx.scene.Scene
import view.MyView
import tornadofx.*
import tornadofx.FX.Companion.primaryStage
import view.Login


class MyApp: App(MyView::class, Styles::class){
    override fun createPrimaryScene(view: UIComponent): Scene {
        val scene = Scene(view.root)
        primaryStage.width = 800.0
        primaryStage.height = 600.0
        return scene
    }
}