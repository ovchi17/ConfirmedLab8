import com.google.gson.Gson
import controllers.CollectionMainCommands
import moduleWithResults.ResultModule
import moduleWithResults.WorkWithResultModule
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import workCommandsList.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.LinkedBlockingQueue

/**
 * Class ServerModule.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class ServerModule {
    var socket = DatagramSocket(2022)
    val commandStarter = CommandStarter()
    val gson = Gson()
    val buffer = ByteArray(65535)
    val packet = DatagramPacket(buffer, buffer.size)
    val logger: Logger = LogManager.getLogger(ServerModule::class.java)
    val availableTokens = mutableMapOf<String, String>() // hash-token -> hashLogin
    val tokenToValid = mutableMapOf<String, Boolean>() // hash-token -> valid (t/f)
    val tokenToStatus = mutableMapOf<String, String>() // hash-token -> status (user/admin)
    val hashSHA = ShaBuilder()
    val workWithResultModule = WorkWithResultModule()
    val threadPoolReceiver = Executors.newFixedThreadPool(10)
    val threadPoolExecutor = Executors.newFixedThreadPool(10)
    val executor = Executors.newFixedThreadPool(5)
    val queueRecExe = LinkedBlockingQueue<ResultModule>()
    val queueExeSen = LinkedBlockingQueue<ResultModule>()
    var ct = 0


    /**
     * serverReceiver method. Receives args and command from client
     *
     */
    fun serverReceiver(){
        while (true){
            ct++
            socket.receive(packet)
            println("Receiver got $ct")
            val worker: Runnable = WorkerThread(packet, ct)
            threadPoolReceiver.execute(worker)
        }
    }

    fun commandExecutor(){
        while (true){
            var resModel = queueRecExe.take()
            println("Exe got $ct")
            println(resModel)
            val workerCommand: Runnable = WorkerThreadCommand(resModel, ct)
            threadPoolExecutor.execute(workerCommand)
        }
    }

    /**
     * serverSender method. Send to client ResultModule
     *
     * @param result arguments
     */
    fun serverSender() {
        while (true) {
            if (!queueExeSen.isEmpty()){
                var result = queueExeSen.take()
                println(queueExeSen)
                ForkJoinPool.commonPool().execute {
                    println("Sender got $ct")
                    val json = gson.toJson(result)
                    val changedToBytes = json.toByteArray()
                    val packetToSend = DatagramPacket(changedToBytes, changedToBytes.size, packet.address, packet.port)
                    println(result)
                    logger.info("Отправлен результат")
                    socket.send(packetToSend)
                    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                }
            }
        }
    }

}

class CommandStarter(): KoinComponent{

    val workWithCollection: CollectionMainCommands by inject()

    val info: Info = Info()
    val show: Show = Show()
    val add: Add = Add()
    val removeById: RemoveById = RemoveById()
    val clear: Clear = Clear()
    val save: Save = Save()
    val load: Load = Load()
    val updateCommand: UpdateCommand = UpdateCommand()
    val updateId: UpdateId = UpdateId()
    val exitServer: ExitServer = ExitServer()
    val removeFirst: RemoveFirst = RemoveFirst()
    val addIfMax: AddIfMax = AddIfMax()
    val history: History = History()
    val removeAllByDistance: RemoveAllByDistance = RemoveAllByDistance()
    val averageOfDistance: AverageOfDistance = AverageOfDistance()
    val filterLessThanDistance: FilterLessThanDistance = FilterLessThanDistance()
    val switch: Switch = Switch()
    val token: Token = Token()
    val logOut: LogOut = LogOut()

    fun mp(command: String): Command? {

        val COMMANDS = mapOf(
            "info" to info,
            "show" to show,
            "add" to add,
            "remove_by_id" to removeById,
            "clear" to clear,
            "save" to save,
            "load" to load,
            "update_id" to updateId,
            "update_command" to updateCommand,
            "exit_server" to exitServer,
            "remove_first" to removeFirst,
            "add_if_max" to addIfMax,
            "history" to history,
            "remove_all_by_distance" to removeAllByDistance,
            "average_of_distance" to averageOfDistance,
            "filter_less_than_distance" to filterLessThanDistance,
            "switch" to switch,
            "token" to token,
            "log_out" to logOut)

        if (command in COMMANDS) {
            workWithCollection.historyUpdate(command)
            return COMMANDS[command]
        }else{
            return null
        }
    }
}