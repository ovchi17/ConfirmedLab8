import com.google.gson.Gson
import commandsHelpers.KeyGenerator
import moduleWithResults.ResultModule
import moduleWithResults.Status
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import usersView.AnswerToUser
import usersView.LogSingle
import view.BasicInfo
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.channels.SelectionKey
import java.nio.channels.Selector

class LoginsUpdate: KoinComponent {

    var currentResult = mutableListOf<LogSingle>()
    val tokenizator: Tokenizator by inject()
    val argument = mutableListOf<String>()
    val clientModule: ClientModule by inject()
    private var receiverThread: Thread? = null

    fun receiver() {
        receiverThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                clientModule.sender("get_valid_logins", argument, BasicInfo.token)
                val resultAnswer = clientModule.receiver(0)
                val getResultModule = resultAnswer
                val rnResult = mutableListOf<LogSingle>()
                if (getResultModule.status == Status.SUCCESS) {
                    for (msg in getResultModule.args) {
                        rnResult.add(LogSingle(msg as String))
                    }
                }
                currentResult = rnResult
                println(currentResult)
                Thread.sleep(7000)
            }
        }
        receiverThread?.start()
    }

    fun stopReceiverThread() {
        receiverThread?.interrupt()
        receiverThread = null
    }
}