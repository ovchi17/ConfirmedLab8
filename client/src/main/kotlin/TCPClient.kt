import view.BasicInfo
import view.Chat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TCPClient {
    private lateinit var socket: Socket
    private lateinit var inputReader: BufferedReader
    private lateinit var outputWriter: PrintWriter
    val listOfMessages = mutableListOf<String>()
    val basicInfo = BasicInfo()

    fun connect(serverAddress: String, serverPort: Int) {
        socket = Socket(serverAddress, serverPort)
        //println("Connected to server: ${socket.inetAddress.hostAddress}")
        inputReader = BufferedReader(InputStreamReader(socket.getInputStream()))
        outputWriter = PrintWriter(socket.getOutputStream(), true)
    }

    fun sendRequest(request: String) {
        outputWriter.println(request)
    }

    fun startReceiver() {
        val receiverThread = Thread {
            try {
                while (true) {
                    val response = inputReader.readLine()
                    if (response != null) {
                        listOfMessages.add(response)
                        basicInfo.add(response)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                close()
            }
        }
        receiverThread.start()
    }

    fun close() {
        try {
            inputReader.close()
            outputWriter.close()
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

