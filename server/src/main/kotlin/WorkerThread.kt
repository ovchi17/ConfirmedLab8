import com.google.gson.Gson
import moduleWithResults.ResultModule
import moduleWithResults.Status
import moduleWithResults.WorkWithResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.DatagramPacket
class WorkerThread(packetGet: DatagramPacket, num: Int): Runnable, KoinComponent {

    val packet = packetGet
    val gson = Gson()
    val commandStarter = CommandStarter()
    val hashSHA = ShaBuilder()
    val serverModule: ServerModule by inject()
    val workWithResultModule: WorkWithResultModule by inject()
    var ct = num


    override fun run() {
        println("START | thread $ct")
            processingCommand()
        println("END | thread $ct")
    }

    private fun processingCommand(){
        try {
            ct++
            val json = String(packet.data, 0, packet.length)
            val getInfo = gson.fromJson(json, ResultModule::class.java)
            if (getInfo.token == "Update" || getInfo.commandName == "log_out"){
                //commandStarter.mp(getInfo.commandName)?.execute(getInfo.args, "noNeed", getInfo.uniqueKey)
                serverModule.queueRecExe.put(getInfo)
            } else if (hashSHA.toSha(getInfo.token) in serverModule.availableTokens.keys && serverModule.tokenToValid[hashSHA.toSha(getInfo.token)] === true){
                if (getInfo.commandName != "sessionIsOver"){
                    println("====================================================")
                    println(getInfo)
                    println("====================================================")
                    //serverModule.availableTokens.get(hashSHA.toSha(getInfo.token))
                    //    ?.let { commandStarter.mp(getInfo.commandName)?.execute(getInfo.args, it, getInfo.uniqueKey) }
                    serverModule.queueRecExe.put(getInfo)
                }else{
                    serverModule.availableTokens.remove(hashSHA.toSha(getInfo.token))
                    workWithResultModule.setStatus(Status.SUCCESS)
                    workWithResultModule.setUniqueKey(getInfo.uniqueKey)
                    //serverModule.serverSender(workWithResultModule.getResultModule())
                    serverModule.queueExeSen.put(workWithResultModule.getResultModule())
                    workWithResultModule.clear()
                }
            }else{
                workWithResultModule.setStatus(Status.ERROR)
                workWithResultModule.setError("noToken")
                workWithResultModule.setUniqueKey(getInfo.uniqueKey)
                //serverModule.serverSender(workWithResultModule.getResultModule())
                serverModule.queueExeSen.put(workWithResultModule.getResultModule())
                workWithResultModule.clear()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}