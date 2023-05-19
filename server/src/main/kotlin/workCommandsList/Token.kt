package workCommandsList

import ShaBuilder
import moduleWithResults.Status

class Token: Command() {

    val hashSHA = ShaBuilder()

    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val lognpass = (getArgs[0].toString()).split(":")
        val resultPas =  hashSHA.toSha(lognpass[1])
        val resultLog = hashSHA.toSha(lognpass[0])
        println(serverModule.availableTokens)
        if (resultLog in serverModule.availableTokens.values){
            println(resultLog)
            val middleResult = resultPas + resultLog.take(10)
            val middle = hashSHA.toSha(middleResult)
            var token: String = "hell-omyn-amei-sme1"
            if ("hellomynameis" in lognpass[0]){
                val chunks = middle.chunked(5)
                token = chunks[0] + "-" + chunks[1] + "-" + chunks[2] + "-" + chunks[3] + "-" + chunks[4]
            }else{
                val chunks = middle.chunked(4)
                token = chunks[0] + "-" + chunks[1] + "-" + chunks[2] + "-" + chunks[3]
            }
            if (!serverModule.availableTokens.containsKey(hashSHA.toSha(token))){
                workWithResultModule.setStatus(Status.ERROR)
                workWithResultModule.setUniqueKey(uniqueToken)
                //serverModule.serverSender(workWithResultModule.getResultModule())
                serverModule.queueExeSen.put(workWithResultModule.getResultModule())
                workWithResultModule.clear()
            }else{
                serverModule.tokenToValid[hashSHA.toSha(token)] = true
                workWithResultModule.setStatus(Status.TOKEN)
                workWithResultModule.setToken(token)
                workWithResultModule.setUniqueKey(uniqueToken)
                serverModule.queueExeSen.put(workWithResultModule.getResultModule())
                workWithResultModule.clear()
            }
        }else{
            val middleResult = resultPas + resultLog.take(10)
            val middle = hashSHA.toSha(middleResult)
            var token: String = "hell-omyn-amei-sme1"
            if ("hellomynameis" in lognpass[0]){
                val chunks = middle.chunked(5)
                token = chunks[0] + "-" + chunks[1] + "-" + chunks[2] + "-" + chunks[3] + "-" + chunks[4]
            }else{
                val chunks = middle.chunked(4)
                token = chunks[0] + "-" + chunks[1] + "-" + chunks[2] + "-" + chunks[3]
            }

            println(token)

            workWithResultModule.setStatus(Status.TOKEN)
            workWithResultModule.setToken(token)
            workWithResultModule.setUniqueKey(uniqueToken)
            serverModule.availableTokens[hashSHA.toSha(token)] = resultLog
            serverModule.tokenToValid[hashSHA.toSha(token)] = true
            serverModule.tokenToStatus[hashSHA.toSha(token)] = if (token.length == 19) "user" else "admin"

            //serverModule.serverSender(workWithResultModule.getResultModule())
            serverModule.queueExeSen.put(workWithResultModule.getResultModule())

            workWithResultModule.clear()
        }
        }
}