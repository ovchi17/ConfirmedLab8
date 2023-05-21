package view

import ClientModule
import LoginsUpdate
import app.Styles
import commandsHelpers.GetToken
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Tooltip
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.paint.Color
import javafx.scene.text.Font.font
import javafx.scene.text.FontWeight
import moduleWithResults.Status
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*
import usersView.LogSingle
import java.util.*
import kotlin.concurrent.thread

class BasicInfo: KoinComponent {

    val loginsUpdate: LoginsUpdate by inject()
    val clientModule: ClientModule by inject()
    companion object {
        var logName = ""
        var token = ""

        var setLog: String
            get() = logName
            set(value) {
                logName = value
            }

        var setToken: String
            get() = token
            set(value) {
                token = value
            }

    }
}

class MyView: View("BebraView"), KoinComponent {
    override val root = vbox {
        alignment = javafx.geometry.Pos.CENTER
        addClass(Styles.blackBackground)

        label("Здравствуйте!"){
            textFill = Color.WHITE
            style {
                fontSize = 40.px
            }
        }


        button("Войти") {
            prefWidth = 100.0
            prefHeight = 60.0
            style {
                fontSize = 20.px
            }
            action {
                replaceWith<Login>()
            }
        }
    }
}

class Login : View("BebraView"), KoinComponent {

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
                    if (token.length == 19 || token.length == 24){
                        textPropertyRes.set(token)
                        val timer = Timer()
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                runLater {
                                    replaceWith<WorkingPage>()
                                }
                            }
                        }, 5000)
                    }else{
                        textPropertyRes.set(token)
                    }
                }
                label(textPropertyRes)
            }
        }
    }

    private fun registerUser(): String {
        val login = usernameField.text
        BasicInfo.setLog = login
        val pas = passwordField.text
        if (login.length > 0 && pas.length > 0){
            if (getToken.loginAndGetToken(login, pas)){
                BasicInfo.setToken = getToken.token
                return getToken.token
            }else{
                return "This login is already registered"
            }
        }else{
            return "Not valid"
        }
    }
}

class WorkingPage : View("BebraView"), KoinComponent{

    val login = Login()
    val textPropertyName = SimpleStringProperty(BasicInfo.logName)
    val basicInfo = BasicInfo()
    //val turnOn = basicInfo.loginsUpdate.receiver()
    var logs: ObservableList<LogSingle> = mutableListOf<LogSingle>().observable()

    init {
        val thread = thread {
            while (!Thread.currentThread().isInterrupted) {
                val argument = mutableListOf<String>()
                basicInfo.clientModule.sender("get_valid_logins", argument, BasicInfo.token)
                val resultAnswer = basicInfo.clientModule.receiver(0)
                val getResultModule = resultAnswer
                val rnResult = mutableListOf<LogSingle>()
                if (getResultModule.status == Status.SUCCESS) {
                    for (msg in getResultModule.args) {
                        rnResult.add(LogSingle(msg as String))
                    }
                }
                Platform.runLater {
                    logs.setAll(rnResult)
                }
                Thread.sleep(7000)
            }
        }
        primaryStage.setOnCloseRequest {
            thread.interrupt()
        }
    }

    override val root = stackpane {
        hbox {
            vbox{
                // Левая часть
                prefWidth = 400.0
                style {
                    backgroundColor += Color.LIGHTGRAY
                }
                hbox(7, Pos.TOP_LEFT){
                    label(" Добро пожаловать, "){
                        font = font("Segoe UI", FontWeight.LIGHT, 20.0)
                    }
                    label(textPropertyName){
                        font = font("Segoe UI", FontWeight.BOLD, 20.0)
                    }
                }
                vbox {
                    tableview(logs) {
                        readonlyColumn("Login", LogSingle::loginH)
                    }
                }
            }

            vbox (7, Pos.TOP_CENTER) {
                // Средняя часть
                prefWidth = 400.0
                style {
                    backgroundColor += Color.DARKGRAY
                }
                vbox(0, Pos.TOP_LEFT) {
                    label("Список команд:"){
                        style {
                            fontSize = 20.px
                        }
                    }
                }

                button("Add"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** add {element} : добавить новый элемент в коллекцию"))
                }
                button("AddIfMax"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"))
                }
                button("AverageOfDistance"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** average_of_distance : вывести среднее значение поля distance для всех элементов коллекции"))
                }
                button("Clear"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** clear : очистить коллекцию"))
                }
                button("ExitServer"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** exitServer : Выключения сервера"))
                }
                button("FilterLessThanDistance"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** filter_less_than_distance distance : вывести элементы, значение поля distance которых меньше заданного"))
                }
                button("History"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** history : вывести последние 14 команд (без их аргументов)"))
                }
                button("Info"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"))
                }
                button("RemoveAllByDistance"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** remove_all_by_distance distance : удалить из коллекции все элементы, значение поля distance которого эквивалентно заданному"))
                }
                button("RemoveById"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** remove_by_id id : удалить элемент из коллекции по его id"))
                }
                button("Save"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** save : сохранить коллекцию в файл"))
                }
                button("UpdateId"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        //TODO: add
                    }

                    setTooltip(Tooltip("** update id {element} : обновить значение элемента коллекции, id которого равен заданному"))
                }
            }
        }
    }

}
