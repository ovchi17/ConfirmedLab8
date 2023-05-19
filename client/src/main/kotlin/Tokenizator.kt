

import commandsHelpers.*
import moduleWithResults.ResultModule
import moduleWithResults.Status
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import usersView.AnswerToUser
import usersView.ConsoleWriter
import usersView.TypeMessages
import usersView.WorkWithModule
import java.io.File


/**
 * Tokenizator class.
 *
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Tokenizator: KoinComponent {

    var listOfNo: MutableList<String> = mutableListOf()
    var listOfLong: MutableList<String> = mutableListOf()
    var listOfString = listOf("execute_script")
    var listOfObject: MutableList<String> = mutableListOf()
    var listOfObjectPlus: MutableList<String> = mutableListOf()

    val listNo = File("listNo.txt")
    val listLong = File("listLong.txt")
    val listObject = File("listObject.txt")
    val listObjectPlus = File("listObjectPlus.txt")

    /**
     * downloadLists method. Download info from file
     *
     */
    fun downloadLists(){
        if (listNo.exists()) {
            val lines = listNo.readLines()
            for (line in lines) {
                listOfNo.addAll(line.split(" "))
            }
        }
        if (listLong.exists()) {
            val lines = listLong.readLines()
            for (line in lines) {
                listOfLong.addAll(line.split(" "))
            }
        }
        if (listObject.exists()) {
            val lines = listObject.readLines()
            for (line in lines) {
                listOfObject.addAll(line.split(" "))
            }
        }
        if (listObjectPlus.exists()) {
            val lines = listObjectPlus.readLines()
            for (line in lines) {
                listOfObjectPlus.addAll(line.split(" "))
            }
        }
    }

    /**
     * uploadLists method. Upload info to file
     *
     */
    fun uploadLists(){

        val resultNo = listOfNo.joinToString(" ")
        val resultLong = listOfLong.joinToString(" ")
        val resultObject = listOfObject.joinToString(" ")
        val resultObjectPlus = listOfObjectPlus.joinToString(" ")

        if (listNo.exists()) {
            listNo.writeText(resultNo)
        }
        if (listLong.exists()) {
            listLong.writeText(resultLong)
        }
        if (listObject.exists()) {
            listObject.writeText(resultObject)
        }
        if (listObjectPlus.exists()) {
            listObjectPlus.writeText(resultObjectPlus)
        }
    }

    /**
     * commandsList method. Returns command type
     *
     * @param name String, command name
     * @return String, command type
     */
    fun commandsList(name: String): String{

        if (name in listOfNo){
            return "listOfNo"
        }else if (name in listOfLong){
            return  "listOfLong"
        }else if (name in listOfString){
            return "listOfString"
        }else if (name in listOfObject){
            return "listOfObject"
        }else if (name in listOfObjectPlus){
            return "listOfObjectPlus"
        }else{
            return "noCommand"
        }
    }

    var writeToConsole: ConsoleWriter = ConsoleWriter()
    val typeMessages: TypeMessages = TypeMessages()
    val answerToUser: AnswerToUser = AnswerToUser()
    val executeScript: ExecuteScript by inject()
    val addSet: AddSet by inject()
    val clientModule: ClientModule by inject()
    val help = Help()
    val exit = Exit()
    val update = Update()
    val displayModule: WorkWithModule = WorkWithModule()
    val logger: Logger = LogManager.getLogger(Tokenizator::class.java)

    /**
     * tokenizator method. Tokenizate massive to commands with right arguments.
     *
     * @param command: Command. Contains the command to be executed.
     * @param mass: Array of String arguments.
     */
    fun tokenizator(command: String, mass: List<String>, tkn: String){
        val sendList = mutableListOf<Any>()
        val clientModule: ClientModule by inject()
        if (commandsList(command) == "listOfLong"){
            logger.info("Начала запуска команды по шаблону listOfLong")
            var newToken:Long = 1
            try {
                newToken = mass[0].toLong()
            }catch (e: NumberFormatException){
                answerToUser.writeToConsoleLn("Ошибка в парматрах, установлено значение по умолчанию")
            }
            sendList.add(newToken)
            clientModule.sender(command, sendList, tkn) // Следить
            val resultAnswer = clientModule.receiver(0)
            displayModule.displayModule(resultAnswer)
        }else if(commandsList(command) == "listOfString"){
            logger.info("Начала запуска команды по шаблону listOfString")
            sendList.add(mass[0])
            val getResultModule: ResultModule = executeScript.execute(sendList)
            if (getResultModule.status == Status.SUCCESS) {
                for (msg in getResultModule.msgToPrint) {
                    if (typeMessages.msgToPrint(msg) != null) {
                        writeToConsole.printToConsoleLn(msg)
                    } else {
                        answerToUser.writeToConsoleLn(msg)
                    }
                }
                answerToUser.writeToConsoleLn(" ")
            }else{
                getResultModule.errorDescription?.let { writeToConsole.printToConsoleLn(it) }
            }
        }else if(commandsList(command) == "listOfObject"){
            logger.info("Начала запуска команды по шаблону listOfObject")
            val name = addSet.name("noInfo")
            val coord1: Long = addSet.coord1("noInfo")
            val coord2: Long = addSet.coord2("noInfo")
            val location1: Long = addSet.location1("noInfo")
            val location2: Long = addSet.location2("noInfo")
            val location3: Int = addSet.location3("noInfo")
            val location1_2: Long = addSet.location12("noInfo")
            val location2_2: Long = addSet.location22("noInfo")
            val location3_2: Int = addSet.location32("noInfo")
            val distance = addSet.distance("noInfo")
            val list = mutableListOf<Any>(name, coord1, coord2, location1, location2, location3, location1_2, location2_2, location3_2, distance)
            sendList.addAll(list)
            clientModule.sender(command, sendList, tkn) // Следить
            val resultAnswer = clientModule.receiver(0)
            displayModule.displayModule(resultAnswer)
        }else if(commandsList(command) == "listOfObjectPlus"){
            logger.info("Начала запуска команды по шаблону listOfObjectPlus")
            val name = addSet.name("noInfo")
            val coord1: Long = addSet.coord1("noInfo")
            val coord2: Long = addSet.coord2("noInfo")
            val location1: Long = addSet.location1("noInfo")
            val location2: Long = addSet.location2("noInfo")
            val location3: Int = addSet.location3("noInfo")
            val location1_2: Long = addSet.location12("noInfo")
            val location2_2: Long = addSet.location22("noInfo")
            val location3_2: Int = addSet.location32("noInfo")
            val distance = addSet.distance("noInfo")
            val id: Long = addSet.id("noInfo")
            val list = mutableListOf<Any>(name, coord1, coord2, location1, location2, location3, location1_2, location2_2, location3_2, distance, id)
            sendList.addAll(list)
            clientModule.sender(command, sendList, tkn) // Следить
            val resultAnswer = clientModule.receiver(0)
            displayModule.displayModule(resultAnswer)
        }else if(commandsList(command) == "listOfNo"){
            logger.info("Начала запуска команды по шаблону listOfNo")
            if (command == "help"){
                help.execute()
            }else if(command == "exit"){
                exit.execute(tkn)
            }else{
                clientModule.sender(command, sendList, tkn) //Следить
                val resultAnswer = clientModule.receiver(0)
                if (command == "update_command"){
                    update.execute(resultAnswer)
                }else{
                    displayModule.displayModule(resultAnswer)
                }
            }
        }else if(commandsList(command) == "noCommand"){
            logger.info("Неверная команда")
            writeToConsole.printToConsoleLn("infoAbout")
        }
    }

    /**
     * tokenizatorAdder method. In fact, second tokenizator
     *
     * @param command: Command. Contains the command to be executed.
     * @param mass: Array of String arguments.
     */
    fun tokenizatorAdder(command: String, mass: List<String>){
        val sendList = mutableListOf<Any>()
        val args = mass[1].split(" ")

        val name = addSet.name(args[0])
        val coord1: Long = addSet.coord1(args[1])
        val coord2: Long = addSet.coord2(args[2])
        val location1: Long = addSet.location1(args[3])
        val location2: Long = addSet.location2(args[4])
        val location3: Int = addSet.location3(args[5])
        val location1_2: Long = addSet.location12(args[6])
        val location2_2: Long = addSet.location22(args[7])
        val location3_2: Int = addSet.location32(args[8])
        val distance = addSet.distance(args[9])
        val list = listOf<Any>(name, coord1, coord2, location1, location2, location3, location1_2, location2_2, location3_2, distance)
        println(list)
        sendList.addAll(list)

    }

}