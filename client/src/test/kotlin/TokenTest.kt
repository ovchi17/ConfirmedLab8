import commandsHelpers.GetToken
import di.koinModule
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

internal class TokenTest: KoinComponent {

    val getToken: GetToken = GetToken()
    val clientModule: ClientModule = ClientModule()

    @Test
    fun testTokenSystem(){

        startKoin {
            modules(koinModule)
        }
        val sendList = mutableListOf<Any>()

        clientModule.start()
        getToken.loginAndGetToken("log", "pas")

    }
}