package workCommandsList

class ValidLogins: Command() {
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val listValidLogin = mutableListOf<String>()
        val keysList = serverModule.availableTokens.keys.toList()
        for (k in keysList){
            if (serverModule.tokenToValid[k] == true){
                serverModule.availableTokens[k]?.let { listValidLogin.add(it) }
            }
        }
        workWithResultModule.setArgs(listValidLogin)
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()

    }
}