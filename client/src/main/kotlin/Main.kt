
import commandsHelpers.GetToken
import di.koinModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import usersView.AnswerToUser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import kotlin.system.exitProcess

fun main() {

    startKoin {
        modules(koinModule)
    }
    //Ilya

    val listNo = File("listNo.txt")
    val listLong = File("listLong.txt")
    val listObject = File("listObject.txt")
    val listObjectPlus = File("listObjectPlus.txt")
    val writeToConsole: AnswerToUser = AnswerToUser()
    val tokenizator = KoinStarter().returnTokenizator()
    val clientModule = KoinStarter().returnClientModule()
    val getToken = GetToken()
    System.setProperty("log4j.configurationFile", "classpath:log4j2.xml")
    val logger: Logger = LogManager.getLogger(KoinStarter::class.java)
    var authorizationFlag = false
    val sendList = mutableListOf<Any>()

    writeToConsole.writeToConsoleLn("Для получения списка команд введите: help")
    clientModule.start()
    logger.info("Начало программы")
    tokenizator.downloadLists()

    while (true){
        if (authorizationFlag){
            writeToConsole.writeToConsole("> ")
            val getCommandFromUser: List<String> = ((readln().lowercase()) + " 1").split(" ")
            writeToConsole.writeToConsole("Ваш токен: ")
            val tkn: String = readln()
            val command = getCommandFromUser[0]
            val argument = mutableListOf<String>()
            for (i in 1..getCommandFromUser.size - 1) {
                argument.add(getCommandFromUser[i])
            }
            logger.info("Запуск команды: $command")
            if (command == "log_out"){
                clientModule.sender("log_out", sendList, tkn)
                clientModule.receiver(0)
                writeToConsole.writeToConsoleLn("Вы вышли из профиля")
                authorizationFlag = false
                writeToConsole.writeToConsoleLn("Завершить работу консольного приложения Y/N")
                val ans: String = readln()
                if (ans == "Y"){
                    exitProcess(0)
                }
            }else{
                tokenizator.tokenizator(command, argument, tkn)
            }
        }else{
            writeToConsole.writeToConsoleLn("Здравствуйте! Для дальнейшей работы с консолью, пожалуйста, введите ваш логин и пароль.")
            writeToConsole.writeToConsole("Ваш логин: ")
            val login: String = readln()
            writeToConsole.writeToConsoleLn("-----------------------------------------------")
            writeToConsole.writeToConsole("Ваш пароль: ")
            val pas: String = readln()
            if (login.length > 0 && pas.length > 0){
                if (getToken.loginAndGetToken(login, pas)){
                    authorizationFlag = true
                }
            }
        }

    }
}

class KoinStarter: KoinComponent{
    val tokenizator: Tokenizator by inject()
    val clientModule: ClientModule by inject()
    fun returnTokenizator(): Tokenizator{
        return tokenizator
    }

    fun returnClientModule(): ClientModule{
        return clientModule
    }
}
