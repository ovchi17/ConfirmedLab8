package workCommandsList

/**
 * Class Clear. Remove all objects from the collection
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Clear: Command() {

    /**
     * execute method. Clear collection
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String) {

        workWithCollection.clearCollection()
        dbModule.clearRoute()
        workWithResultModule.setMessages("cleared")
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}