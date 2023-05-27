import moduleWithResults.ResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class proxyTokenizator {

    val tokenizator = KoinTkn().returnTokenizator()
    fun proxyTokenizator(command: String, argument: List<String>, tkn: String): ResultModule{
        val res = tokenizator.tokenizator(command, argument, tkn)
        return res
    }

}

class KoinTkn: KoinComponent {
    val tokenizator: Tokenizator by inject()
    fun returnTokenizator(): Tokenizator{
        return tokenizator
    }
}