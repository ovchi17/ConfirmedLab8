package workCommandsList


import ShaBuilder
import dataSet.Route
import dataSet.RouteComporator
import java.util.PriorityQueue

/**
 * Class RemoveFirst. Remove the first object
 *
 * @author jutsoNNN
 * @since 1.0.0
 */
class RemoveFirst: Command(){

    /**
     * execute method. Remove first object in collection
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())
        val hashSHA = ShaBuilder()
        val owner = serverModule.availableTokens[hashSHA.toSha(login)].toString()

        if (collection.size == 0){
            workWithResultModule.setMessages("emptyCollection")
        }else{
            val checkObject = workWithCollection.peekCollection()
            if (checkObject != null) {
                if (checkObject.owner == owner){
                    dbModule.deleteRoute(checkObject.id)
                    workWithCollection.pollCollection()
                    workWithResultModule.setMessages("cleared")
                }else{
                    workWithResultModule.setMessages("notYou")
                }
            }
        }
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}