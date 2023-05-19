package commandsHelpers

import ClientModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.system.exitProcess

/**
 * Class Exit. Stop client module.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Exit: KoinComponent {

    val clientModule: ClientModule by inject()

    /**
     * execute method. Stop client module
     *
     */
    fun execute(token: String){
        val sendList = mutableListOf<Any>()
        clientModule.sender("sessionIsOver", sendList, token)
        val resultAnswer = clientModule.receiver(0)
        clientModule.stop()

        exitProcess(0)
    }
}