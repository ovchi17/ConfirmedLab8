package workCommandsList

import ShaBuilder
import moduleWithResults.Status

class LogOut: Command() {

    val hashSHA = ShaBuilder()

    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        if (hashSHA.toSha(login) in serverModule.availableTokens.keys)    {
            serverModule.tokenToValid[hashSHA.toSha(login)] = false
            println("deactivated")
        }


        workWithResultModule.setStatus(Status.TOKEN)
        workWithResultModule.setToken(login)
        workWithResultModule.setUniqueKey(uniqueToken)

        serverModule.queueExeSen.put(workWithResultModule.getResultModule())

        workWithResultModule.clear()

    }
}