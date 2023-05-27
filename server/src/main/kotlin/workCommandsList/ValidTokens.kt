package workCommandsList

import ShaBuilder

class ValidTokens: Command() {

    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val listValidLogin = mutableListOf<String>()
        val hashSHA = ShaBuilder()
        val keysList = serverModule.availableTokens.keys.toList()
        val tokenToCheck = hashSHA.toSha(getArgs[0].toString())
        if (serverModule.tokenToValid[tokenToCheck] == true){
            listValidLogin.add("Y")
            workWithResultModule.setArgs(listValidLogin)
        }else{
            listValidLogin.add("N")
            workWithResultModule.setArgs(listValidLogin)
        }

        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()

    }

}