import moduleWithResults.ResultModule
import moduleWithResults.WorkWithResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WorkerThreadCommand(info: ResultModule, num: Int): Runnable, KoinComponent {

    val getInfo = info
    val commandStarter = CommandStarter()
    val hashSHA = ShaBuilder()
    val serverModule: ServerModule by inject()
    val workWithResultModule: WorkWithResultModule by inject()
    var ct = num


    override fun run() {
        println("START | thread2 $ct")
        synchronized(this){
            processingCommand()
        }
        println("END | thread2 $ct")
    }

    private fun processingCommand(){
        try {
            commandStarter.mp(getInfo.commandName)?.execute(getInfo.args, getInfo.token, getInfo.uniqueKey)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}