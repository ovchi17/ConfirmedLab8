package di

import DataBaseManager
import ServerModule
import TCPServer
import controllers.*
import moduleWithResults.WorkWithResultModule
import org.koin.dsl.module


val serverModule = module {

    single<CollectionMainCommands> {
        WorkWithCollection()
    }

    single {
        WorkWithFile()
    }

    single {
        Serializer()
    }

    single {
        TCPServer()
    }

    factory {
        WorkWithResultModule()
    }

    single {
        Parametrs()
    }

    single {
        ServerModule()
    }

    single {
        DataBaseManager()
    }

}
