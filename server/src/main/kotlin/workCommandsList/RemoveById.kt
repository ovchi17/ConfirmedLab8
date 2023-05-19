package workCommandsList

import ShaBuilder
import dataSet.Route
import dataSet.RouteComporator
import java.util.*

/**
 * Class RemoveById. Remove the element with the given id
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class RemoveById: Command() {

    /**
     * execute method. Remove object by given id
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String) {

        val checkId = (getArgs[0] as Double).toLong()
        var setMessageForMoreThenOne = "noId"
        val hashSHA = ShaBuilder()
        val owner = serverModule.availableTokens[hashSHA.toSha(login)].toString()

        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())

        if (collection.size == 0){
            workWithResultModule.setMessages("emptyCollection")
        }else if(collection.size == 1){
            val checkObject = collection.peek()
            if (checkObject.id == checkId && checkObject.owner == owner){
                dbModule.deleteRoute(checkObject.id)
                workWithCollection.clearCollection()
                workWithResultModule.setMessages("cleared")
            }else{
                workWithResultModule.setMessages("noId")
                workWithResultModule.setMessages("notYou")
            }
        }else{
            workWithCollection.clearCollection()
            for (i in 0..collection.size - 1){
                val checkObject = collection.peek()
                if (checkObject.id == checkId && checkObject.owner == owner) {
                    dbModule.deleteRoute(checkObject.id)
                    collection.poll()
                    setMessageForMoreThenOne = "cleared"
                }else{
                    workWithCollection.addElementToCollection(collection.peek())
                    collection.poll()
                }
            }
            workWithResultModule.setMessages(setMessageForMoreThenOne)
            if (setMessageForMoreThenOne != "cleared"){
                workWithResultModule.setMessages("notYou")
            }
        }
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}