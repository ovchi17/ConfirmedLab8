package workCommandsList

import DataBaseManager
import dataSet.Route
import dataSet.RouteComporator
import java.util.PriorityQueue

/**
 * Class Save. Save to file in JSON format.
 *
 * @author jutsoNNN
 * @since 1.0.0
 */
class Save: Command() {

    /**
     * execute method. Save collection to file
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        dbModule.savingTrue()

        workWithResultModule.setMessages("saved")
        workWithResultModule.setUniqueKey(uniqueToken)

        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()

     }
}