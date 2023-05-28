package view

import ClientModule
import LoginsUpdate
import Tokenizator
import app.Styles
import commandsHelpers.GetToken
import dataSet.Coordinates
import dataSet.FakeRoute
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.event.EventHandler
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
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import kotlin.random.Random

class BasicInfo: KoinComponent {

    val loginsUpdate: LoginsUpdate by inject()
    val clientModule: ClientModule by inject()
    val tokenizator: Tokenizator by inject()
    companion object {
        var logName = ""
        var token = ""
        var lang = "ru"
        var bundle = ResourceBundle.getBundle("messages", Locale("ru"))

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

        var setBundle: ResourceBundle
            get() = bundle
            set(value) {
                bundle = value
            }

    }
}

class Point(val x: Double, val y: Double)

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
                    button(BasicInfo.bundle.getString("enterLogin")) {
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
                    button(BasicInfo.bundle.getString("enterToken")) {
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
            prefHeight = 80.0
            val buttonL = button("Язык: ${textPropertyLang.value}") {
                prefWidth = 120.0
                prefHeight = 50.0
                style {
                    fontSize = 12.px
                    backgroundColor += Color.web("#852178")
                    backgroundRadius += box(70.px)
                }
                action {
                    if(BasicInfo.lang == "ru"){
                        BasicInfo.setLang = "en"
                        BasicInfo.setBundle = ResourceBundle.getBundle("messages", Locale("en"))
                        textPropertyLang.set("Язык|Lang: " + BasicInfo.lang)
                    }else{
                        BasicInfo.setLang = "ru"
                        BasicInfo.setBundle = ResourceBundle.getBundle("messages", Locale("ru"))
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
                fieldset(BasicInfo.bundle.getString("registration")) {
                    prefHeight = 600.0
                    style {
                        border = null
                        backgroundColor += Color.DARKGRAY
                    }
                    field(BasicInfo.bundle.getString("yourLogin")) {
                        add(usernameField)
                    }
                    field(BasicInfo.bundle.getString("yourPas")) {
                        add(passwordField)
                    }
                    hbox(4){
                        button(BasicInfo.bundle.getString("entReg")){
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
                        button(BasicInfo.bundle.getString("backB")){
                            prefWidth = 100.0
                            prefHeight = 35.0

                            style{
                                backgroundColor += Color.web("#852178")
                                backgroundRadius += box(70.px)
                            }
                            action {
                                replaceWith<MyView>()
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
                fieldset(BasicInfo.bundle.getString("registration")) {
                    prefHeight = 600.0
                    style {
                        border = null
                        backgroundColor += Color.DARKGRAY
                    }
                    field(BasicInfo.bundle.getString("yourToken")) {
                        add(passwordField)
                    }
                    hbox(4){
                        button(BasicInfo.bundle.getString("entReg")){
                            prefWidth = 200.0
                            prefHeight = 35.0

                            style{
                                backgroundColor += Color.web("#852178")
                                backgroundRadius += box(70.px)
                            }

                            action {
                                val token = registerUser()
                                if (token == "Y"){
                                    textPropertyRes.set("")
                                    val timer = Timer()
                                    timer.schedule(object : TimerTask() {
                                        override fun run() {
                                            runLater {
                                                replaceWith<WorkingPage>()
                                            }
                                        }
                                    }, 1000)
                                }else{
                                    textPropertyRes.set(token)
                                }
                            }
                        }
                        button(BasicInfo.bundle.getString("backB")){
                            prefWidth = 120.0
                            prefHeight = 35.0

                            style{
                                backgroundColor += Color.web("#852178")
                                backgroundRadius += box(70.px)
                            }
                            action {
                                replaceWith<MyView>()
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
                    label(BasicInfo.bundle.getString("greet")){
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
                    label(BasicInfo.bundle.getString("listC")){
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("addD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("addIfMaxD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("averageOfDistanceD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("clearD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("exitServerD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("filterLessThanDistanceD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("historyD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("infoD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("removeAllByDistanceD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("removeByIdD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("saveD")))
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

                    setTooltip(Tooltip(BasicInfo.bundle.getString("updateIdD")))
                }
            }
        }
    }
}

class TableShow: View("BebraView"), KoinComponent {

    val basicInfo = BasicInfo()
    var objStrings: ObservableList<FakeRoute> = mutableListOf<FakeRoute>().observable()
    var idToCoord = mutableMapOf<String, String>()

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
                        idToCoord[rn[0]] = "${rn[10]} ${rn[11]}"
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
            hbox(3){
                button(BasicInfo.bundle.getString("backB")){
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
                button(BasicInfo.bundle.getString("draw")){
                    prefWidth = 122.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        replaceWith<Draw>()
                    }
                }
            }
        }
    }
}

class Draw : View() {
    val backgroundImage = Image(this::class.java.getResource("/picMap.png").toExternalForm())
    val canvas = canvas(width = 600.0, height = 600.0)
    val gc = canvas.graphicsContext2D
    var startPoint = Point(300.0, 200.0)
    var endPoint = Point(600.0, 600.0)
    val tableShow = TableShow()
    private val filternum = SimpleStringProperty("")

    override val root = hbox {
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox{
            prefWidth = 600.0
            prefHeight = 600.0
            stackpane {
                imageview(backgroundImage) {
                    fitWidth = 600.0
                    fitHeight = 600.0
                }
                add(canvas)
            }
        }
        vbox(4){
            prefWidth = 200.0
            prefHeight = 600.0

            hbox(4) {
                label("Id: "){
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
            hbox(4) {
                button(BasicInfo.bundle.getString("start")) {
                    prefWidth = 100.0
                    prefHeight = 35.0

                    style{
                        backgroundColor += Color.web("#852178")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        if (filternum.value != "" && filternum.value in tableShow.idToCoord.keys.toList()){
                            startDrawing()
                        }
                    }
                }
                button(BasicInfo.bundle.getString("backB")){
                    prefWidth = 100.0
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
    private fun startDrawing() {
        val sp = tableShow.idToCoord[filternum.value]?.split(" ")
        startPoint = Point(((sp?.get(0)?.toDouble() ?:50.0 ) * 10) % 600, ((sp?.get(1)?.toDouble() ?:50.0 ) * 10) % 600)
        endPoint = Point(Random.nextInt(100, 601).toDouble(), Random.nextInt(100, 601).toDouble())
        val animationTimer = object : AnimationTimer() {
            var progress = 0.0

            override fun handle(now: Long) {
                if (progress >= 1.0) {
                    stop()
                }
                gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

                gc.fill = c("red")
                gc.fillOval(startPoint.x - 7.5, startPoint.y - 7.5, 15.0, 15.0)
                gc.fillOval(endPoint.x - 7.5, endPoint.y - 7.5, 15.0, 15.0)

                gc.stroke = c("blue")
                gc.lineWidth = 4.0

                val currentX = startPoint.x + (endPoint.x - startPoint.x) * progress
                val currentY = startPoint.y + (endPoint.y - startPoint.y) * progress

                gc.strokeLine(startPoint.x, startPoint.y, currentX, currentY)

                progress += 0.01

                if (progress >= 1.0) {
                    stop()
                }
            }
        }
        animationTimer.start()
    }
}

