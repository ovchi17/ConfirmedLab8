package commandsHelpers

import Tokenizator
import moduleWithResults.ResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import usersView.AnswerToUser

/**
 * Class Update. Works with Result module with Status.UPDATE.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Update: KoinComponent {

    val tokenizator: Tokenizator by inject()
    val answerToUser = AnswerToUser()

    /**
     * execute method. Check new commands
     *
     * @param module ResultModule
     */
    fun execute(module: ResultModule){
        val lstNo = module.listOfNo
        val lstLong = module.listOfLong
        val lstObject = module.listOfObject
        val lstObjectPlus = module.listOfObjectPlus
        var flag = false
        val flag1 = checker(lstNo, tokenizator.listOfNo)
        val flag2 = checker(lstLong, tokenizator.listOfLong)
        val flag3 = checker(lstObject, tokenizator.listOfObject)
        val flag4 = checker(lstObjectPlus, tokenizator.listOfObjectPlus)

        if (flag1 || flag2 || flag3 || flag4){
            flag = true
        }

        if (flag == false){
            answerToUser.writeToConsoleLn("Установлена актуальная версия")
        }

        tokenizator.uploadLists()

    }

    fun checker(listModule: MutableList<String>, listTkn: MutableList<String>): Boolean{
        var flag = false
        for (i in listModule){
            if (i !in listTkn){
                flag = true
                answerToUser.writeToConsoleLn("Получена новая команда $i")
                tokenizator.listOfNo.add(i)
            }
        }
        return flag
    }

}