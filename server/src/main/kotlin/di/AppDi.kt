package di

import DataBaseManager
import ServerModule
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
