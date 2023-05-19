package workCommandsList

/**
 * Class Switch.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Switch: Command() {

    /**
     * execute method. Switch collection (PQ -> LL or LL -> PQ)
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        var keyCollection = workWithCollection.checkCollection()

        if (keyCollection == "PQ"){
            workWithCollection.changeCollection()
            workWithResultModule.setMessages("changeToCollLL")
        }else{
            workWithCollection.changeCollection()
            workWithResultModule.setMessages("changeToCollPQ")
        }

        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }

}