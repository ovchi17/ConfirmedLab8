package workCommandsList

import java.util.*;
import dataSet.Route
import dataSet.RouteComporator
/**
 * Class Show. Show name and id of objects
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Show: Command() {

    /**
     * execute method. Show objects from collection
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())

        if (collection.size == 0){
            workWithResultModule.setMessages("emptyCollection")
        }else if(collection.size == 1){
            workWithResultModule.setMessages("Name: " + collection.peek().name.toString())
            workWithResultModule.setMessages(" Id: " + collection.peek().id.toString())
        }else{
            for (i in 0..collection.size - 1){
                workWithResultModule.setMessages("Name: " + collection.peek().name.toString())
                workWithResultModule.setMessages(" Id: " + collection.peek().id.toString())
                collection.poll()
            }
        }

        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}
