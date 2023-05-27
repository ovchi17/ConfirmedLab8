package view

import ClientModule
import LoginsUpdate
import Tokenizator
import app.Styles
import commandsHelpers.GetToken
import dataSet.FakeRoute
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
    val tokenizator: Tokenizator by inject()
    companion object {
        var logName = ""
        var token = ""
        var lang = "RU"

        var setLog: String
            get() = logName
            set(value) {
                logName = value
            }

        var setLang: String
            get() = lang
            set(value) {
                lang = value
            }

        var setToken: String
            get() = token
            set(value) {
                token = value
            }

    }
}

class MyView: View("BebraView"), KoinComponent {

    val textPropertyLang = SimpleStringProperty("Язык|Lang: " + BasicInfo.lang)

    override val root = vbox(5, Pos.CENTER) {
        alignment = javafx.geometry.Pos.CENTER
        addClass(Styles.blackBackground)

        hbox(5, Pos.CENTER) {
            vbox(5, Pos.CENTER){
                vbox(5, Pos.CENTER){
                    label("Здравствуйте! | Hello!"){
                        textFill = Color.WHITE
                        style {
                            fontSize = 40.px
                        }
                    }
                }
                prefHeight = 500.0
                hbox(5, Pos.CENTER){
                    button("Войти по логину") {
                        prefWidth = 150.0
                        prefHeight = 50.0
                        style {
                            fontSize = 16.px
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }
                        action {
                            replaceWith<Login>()
                        }
                    }
                    button("Войти по токену") {
                        prefWidth = 150.0
                        prefHeight = 50.0
                        style {
                            fontSize = 16.px
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }
                        action {
                            replaceWith<TokenEnter>()
                        }
                    }
                }
            }
        }
        hbox(5, Pos.BOTTOM_RIGHT){
            prefHeight = 85.0
            val buttonL = button("Язык: ${textPropertyLang.value}") {
                prefWidth = 120.0
                prefHeight = 50.0
                style {
                    fontSize = 12.px
                    backgroundColor += Color.web("#852178")
                    backgroundRadius += box(70.px)
                }
                action {
                    if(BasicInfo.lang == "RU"){
                        BasicInfo.setLang = "EN"
                        textPropertyLang.set("Язык|Lang: " + BasicInfo.lang)
                    }else{
                        BasicInfo.setLang = "RU"
                        textPropertyLang.set("Язык|Lang: " + BasicInfo.lang)
                    }
                }
            }
            buttonL.textProperty().bind(textPropertyLang)
        }
    }
}

class Login : View("BebraView"), KoinComponent {

    //override val root = Form()
    override val root = Form().apply {
        style {
            border = null
            backgroundColor += Color.DARKGRAY
        }
    }

    private val usernameField = textfield()
    private val passwordField = passwordfield()
    val getToken = GetToken()
    //val textProperty = SimpleStringProperty("")
    val textPropertyRes = SimpleStringProperty("")

    init {
        with(root) {
            hbox{
                prefWidth = 1000.0
                prefHeight = 1000.0
                style {
                    backgroundColor += Color.DARKGRAY
                }
                fieldset("Регистрация") {
                    prefHeight = 600.0
                    style {
                        border = null
                        backgroundColor += Color.DARKGRAY
                    }
                    field("Ваш логин:") {
                        add(usernameField)
                    }
                    field("Ваш пароль:") {
                        add(passwordField)
                    }
                    button("Войти/Зарегестрироваться"){
                        prefWidth = 200.0
                        prefHeight = 35.0

                        style{
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }

                        action {
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
                    }
                    label(textPropertyRes)
                }
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

class TokenEnter : View("BebraView"), KoinComponent {

    //override val root = Form()
    override val root = Form().apply {
        style {
            border = null
            backgroundColor += Color.DARKGRAY
        }
    }

    private val passwordField = passwordfield()
    val getToken = GetToken()
    val basicInfo = BasicInfo()
    //val textProperty = SimpleStringProperty("")
    val textPropertyRes = SimpleStringProperty("")

    init {
        with(root) {
            hbox{
                prefWidth = 1000.0
                prefHeight = 1000.0
                style {
                    backgroundColor += Color.DARKGRAY
                }
                fieldset("Регистрация") {
                    prefHeight = 600.0
                    style {
                        border = null
                        backgroundColor += Color.DARKGRAY
                    }
                    field("Ваш токен:") {
                        add(passwordField)
                    }
                    button("Войти"){
                        prefWidth = 200.0
                        prefHeight = 35.0

                        style{
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }

                        action {
                            val token = registerUser()
                            if (token == "Y"){
                                textPropertyRes.set("Успешно")
                                val timer = Timer()
                                timer.schedule(object : TimerTask() {
                                    override fun run() {
                                        runLater {
                                            replaceWith<WorkingPage>()
                                        }
                                    }
                                }, 2000)
                            }else{
                                textPropertyRes.set(token)
                            }
                        }
                    }
                    label(textPropertyRes)
                }
            }
        }
    }

    private fun registerUser(): String {
        BasicInfo.setLog = "noName"
        val pas = passwordField.text
        if (pas.length > 0){
            val sendList = mutableListOf<Any>()
            sendList.add(pas)
            basicInfo.clientModule.sender("check_valid_token", sendList, "Update")
            val getRes = basicInfo.clientModule.receiver(0)
            if (getRes.args[0] == "Y"){
                BasicInfo.setToken = pas
                return "Y"
            }else{
                return "Not valid"
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
                hbox(5, Pos.BOTTOM_LEFT){

                    prefHeight = 135.0

                    label("")

                    button("LogOut"){
                        prefWidth = 122.0
                        prefHeight = 35.0

                        style{
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }
                        action {
                            val sendList = mutableListOf<Any>()
                            basicInfo.clientModule.sender("log_out", sendList, BasicInfo.token)
                            basicInfo.clientModule.receiver(0)
                            replaceWith<MyView>()
                        }
                    }
                    button("TurnOff"){
                        prefWidth = 122.0
                        prefHeight = 35.0

                        style{
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }
                        action {
                            val sendList = mutableListOf<String>()
                            basicInfo.tokenizator.tokenizator("exit", sendList, BasicInfo.token)
                        }
                    }
                    button("ShowTable"){
                        prefWidth = 122.0
                        prefHeight = 35.0

                        style{
                            backgroundColor += Color.web("#852178")
                            backgroundRadius += box(70.px)
                        }
                        action {
                            replaceWith<TableShow>()
                        }
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
                        replaceWith<AddView>()
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
                        replaceWith<AddIfMaxView>()
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
                        replaceWith<AverageOfDistanceView>()
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
                        replaceWith<ClearView>()
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
                        replaceWith<ExitServerView>()
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
                        replaceWith<FilterLessThanDistanceView>()
                    }

                    setTooltip(Tooltip("** filter_less_than_distance distance : вывести количество элементов, значение поля distance которых меньше заданного"))
                }
                button("History"){
                    prefWidth = 160.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        replaceWith<HistoryView>()
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
                        replaceWith<InfoView>()
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
                        replaceWith<RemoveAllByDistanceView>()
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
                        replaceWith<RemoveByIdView>()
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
                        replaceWith<SaveView>()
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
                        replaceWith<UpdateIdView>()
                    }

                    setTooltip(Tooltip("** update id {element} : обновить значение элемента коллекции, id которого равен заданному"))
                }
            }
        }
    }
}

class TableShow: View("BebraView"), KoinComponent {

    val basicInfo = BasicInfo()
    var objStrings: ObservableList<FakeRoute> = mutableListOf<FakeRoute>().observable()

    init {
        val thread = thread {
            while (!Thread.currentThread().isInterrupted) {
                val argument = mutableListOf<String>()
                basicInfo.clientModule.sender("show", argument, BasicInfo.token)
                val resultAnswer = basicInfo.clientModule.receiver(0)
                val getResultModule = resultAnswer
                val rnResult = mutableListOf<FakeRoute>()
                if (getResultModule.status == Status.SUCCESS) {
                    for (msg in getResultModule.args) {
                        val rn = msg.toString().split(" ")
                        rnResult.add(FakeRoute(rn[0], rn[1], rn[2], rn[3], rn[4], rn[5], rn[6], rn[7], rn[8], rn[9], rn[10], rn[11], rn[12]))
                    }
                }
                Platform.runLater {
                    objStrings.setAll(rnResult)
                }
                Thread.sleep(7000)
            }
        }
        primaryStage.setOnCloseRequest {
            thread.interrupt()
        }
    }

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
            vbox {
                tableview(objStrings) {
                    column("ID", FakeRoute::id){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("NAME", FakeRoute::name){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("CreationDate", FakeRoute::creationDate){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l11", FakeRoute::location11){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l12", FakeRoute::location12){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l13", FakeRoute::location13){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l21", FakeRoute::location21){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l22", FakeRoute::location22){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("l23", FakeRoute::location23){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("distance", FakeRoute::distance){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("c1", FakeRoute::coordinate1){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("c2", FakeRoute::coordinate2){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                    column("OWNER", FakeRoute::owner){
                        cellFormat {
                            alignment = Pos.CENTER
                            text = it
                        }
                    }
                }
            }
        vbox(3,  Pos.BOTTOM_LEFT) {
            prefHeight = 160.0
            button("Назад"){
                prefWidth = 122.0
                prefHeight = 35.0

                style{
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

