import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList

class TCPServer {
    private lateinit var serverSocket: ServerSocket
    val logger: Logger = LogManager.getLogger(TCPServer::class.java)
    val clients = CopyOnWriteArrayList<ClientHandler>()

    fun start(serverPort: Int) {
        serverSocket = ServerSocket(serverPort)
        println("Server started on port $serverPort")

        while (true) {
            val clientSocket = serverSocket.accept()
            val clientHandler = ClientHandler(clientSocket)
            println(clientHandler)
            clients.add(clientHandler)
            clientHandler.start()
        }
    }

    fun broadcast(message: String) {
        logger.info(message)
        println(clients)
        clients.forEach { clientHandler ->
            clientHandler.sendMessage(message)
        }
    }
}

class ClientHandler(private val clientSocket: Socket) : Thread(), KoinComponent {
    private lateinit var inputReader: BufferedReader
    private lateinit var outputWriter: PrintWriter
    val tcpServer: TCPServer by inject()

    override fun run() {
        try {
            inputReader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            outputWriter = PrintWriter(clientSocket.getOutputStream(), true)

            while (true) {
                val message = inputReader.readLine()
                if (message != null) {
                    println("Received message from client: $message")
                    tcpServer.broadcast(message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun sendMessage(message: String) {
        println("Send to client: $message")
        outputWriter.println(message)
    }

    fun close() {
        try {
            inputReader.close()
            outputWriter.close()
            clientSocket.close()
            println("Client disconnected")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}