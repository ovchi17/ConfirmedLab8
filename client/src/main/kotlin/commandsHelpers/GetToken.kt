package commandsHelpers

import ClientModule
import moduleWithResults.Status
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import usersView.AnswerToUser

class GetToken: KoinComponent {

    val clientModule: ClientModule by inject()
    val answerToUser: AnswerToUser = AnswerToUser()

    fun loginAndGetToken(login: String, pass: String): Boolean{
        val logPass = "$login:$pass"
        val sendList = mutableListOf<Any>()
        sendList.add(logPass)
        clientModule.sender("token", sendList, "Update")
        val resultAnswer = clientModule.receiver(0)
        if (resultAnswer.status == Status.TOKEN){
            answerToUser.writeToConsoleLn("Успешно! Ваш токен: ${resultAnswer.token}")
            return true
        }else{
            answerToUser.writeToConsoleLn("Данный логин уже зарегестирован!")
            return false
        }
    }
}