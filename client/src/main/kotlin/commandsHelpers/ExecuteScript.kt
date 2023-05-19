package commandsHelpers

import Tokenizator
import org.jetbrains.kotlin.konan.file.File
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import moduleWithResults.*

/**
 * Class ExecuteScript. Run commands from file.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class ExecuteScript: KoinComponent {

    var getLink = ""
    var fileLink = File("")
    var stopRecursion = 5
    var checkerRecursion = 0
    var addChecker = 0
    var params = ""
    var specialForAdd = ""
    val tokenizator: Tokenizator by inject()
    val workWithResultModule = WorkWithResultModule()

    /**
     * execute method. Starts script
     *
     * @return info from command as ResultModule
     */
    fun execute(str: List<Any>): ResultModule {

        getLink = str[0] as String

        if (File(getLink).exists){
            fileLink = File(getLink)
            if (stopRecursion >= checkerRecursion) {
                val commandFromFile = fileLink.readStrings()
                for (line in commandFromFile) {
                    val args = line.split(" ")
                    if (args[0] == "execute_script") {
                        checkerRecursion++
                        val sendList = mutableListOf<Any>()
                        sendList.add(args[1])
                        execute(sendList)
                    }else if (args[0] == "add" || args[0] == "add_if_max"){
                        addChecker = 10
                        specialForAdd = args[0]
                    }else{
                        if (addChecker > 0){
                            params = params + line + " "
                            addChecker -= 1
                            if (addChecker == 0){
                                val addList = mutableListOf<String>()
                                addList.add(params)
                                params = ""
                                tokenizator.tokenizatorAdder(specialForAdd, addList)
                            }
                        }else{
                            tokenizator.tokenizator(args[0], listOf(args[1]), args[2])
                        }
                    }
                }
            }else{
                workWithResultModule.setError("recursion")
                workWithResultModule.setStatus(Status.ERROR)
                return workWithResultModule.getResultModule()
            }
        }else{
            workWithResultModule.setMessages("noFile")
        }

        checkerRecursion -= 1
        if (checkerRecursion == 0) {
            workWithResultModule.setMessages("success")
        }
        return workWithResultModule.getResultModule()

    }
}
