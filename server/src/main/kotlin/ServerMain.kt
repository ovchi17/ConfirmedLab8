import controllers.CollectionMainCommands
import controllers.WorkWithCollection
import di.serverModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.concurrent.Executors
import kotlin.concurrent.thread

fun main() {

    startKoin {
        modules(serverModule)
    }

    val serverModule = ServerModuleGet().returnServerModule()
    val dataBaseManager = ServerModuleGet().returnDatabase()
    val workWithCollection = ServerModuleGet().returnWorkWithCollection()
    dataBaseManager.connectionDB
    val getInfo = dataBaseManager.maxId()
    println(getInfo)
    workWithCollection.setInitId(getInfo)
    dataBaseManager.uploadAllRoutes()
    dataBaseManager.uploadAllLogins()

    System.setProperty("log4j.configurationFile", "classpath:log4j2.xml")
    val logger: Logger = LogManager.getLogger(ServerModuleGet::class.java)
    logger.info("Запуск сервера")
    val threadPoolMain = Executors.newFixedThreadPool(10)

    thread { serverModule.serverReceiver() }
    thread { serverModule.commandExecutor() }
    thread { serverModule.serverSender() }


}

class ServerModuleGet : KoinComponent{
    val serverModule: ServerModule by inject()
    val dbModule: DataBaseManager by inject()
    val workWithCollection: CollectionMainCommands by inject()
    fun returnServerModule():ServerModule{
        return serverModule
    }

    fun returnDatabase(): DataBaseManager{
        return  dbModule
    }

    fun returnWorkWithCollection(): CollectionMainCommands{
        return workWithCollection
    }
}