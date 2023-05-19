package usersView

import moduleWithResults.ResultModule
import moduleWithResults.Status
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WorkWithModule: KoinComponent {

    val typeMessages: TypeMessages = TypeMessages()
    val writeToConsole: ConsoleWriter by inject()
    val answerToUser: AnswerToUser = AnswerToUser()

    fun displayModule(module: ResultModule){
        val getResultModule = module
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
    }

}