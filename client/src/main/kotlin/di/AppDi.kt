package di

import ClientModule
import LoginsUpdate
import TCPClient
import Tokenizator
import commandsHelpers.AddSet
import commandsHelpers.ExecuteScript
import org.koin.dsl.module
import usersView.ConsoleWriter
import view.BasicInfo


val koinModule = module {

    single {
        ConsoleWriter()
    }

    single {
        TCPClient()
    }

    single {
        Tokenizator()
    }

    single {
        AddSet()
    }

    single {
        ExecuteScript()
    }

    single {
        ClientModule()
    }

    single {
        LoginsUpdate()
    }

}
