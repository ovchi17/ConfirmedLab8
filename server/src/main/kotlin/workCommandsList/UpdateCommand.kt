package workCommandsList

import moduleWithResults.Status

/**
 * Class UpdateCommand.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class UpdateCommand: Command() {

    var listOfNo: List<String> = listOf("help", "info", "show", "clear", "save", "load", "exit", "exit_server", "remove_first", "history", "average_of_distance", "switch", "update_command")
    var listOfLong: List<String> = listOf("remove_by_id", "remove_all_by_distance", "filter_less_than_distance")
    var listOfObject: List<String> = listOf("add_if_max", "add")
    var listOfObjectPlus: List<String> = listOf("update_id")

    /**
     * execute method. Send to client command list available on server
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String) {

        workWithCollection.clearCollection()

        workWithResultModule.setStatus(Status.UPDATE)
        workWithResultModule.setListNo(listOfNo)
        workWithResultModule.setListLong(listOfLong)
        workWithResultModule.setListObject(listOfObject)
        workWithResultModule.setListObjectPlus(listOfObjectPlus)
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}