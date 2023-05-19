package di

import ClientModule
import Tokenizator
import commandsHelpers.AddSet
import commandsHelpers.ExecuteScript
import org.koin.dsl.module
import usersView.ConsoleWriter


val koinModule = module {

    single {
        ConsoleWriter()
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

}
