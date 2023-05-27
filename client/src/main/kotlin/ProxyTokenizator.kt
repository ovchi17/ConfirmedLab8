import moduleWithResults.ResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProxyTokenizator {

    val cl = KoinCM().returnClientModule()
    fun proxyTokenizator(command: String, args: List<Any>, tkn: String): String{
        cl.sender(command, args, tkn)
        val res = cl.receiver(0)
        return res.msgToPrint[0]
    }

}

class KoinCM: KoinComponent {
    val clientModule: ClientModule by inject()

    fun returnClientModule(): ClientModule {
        return clientModule
    }
}