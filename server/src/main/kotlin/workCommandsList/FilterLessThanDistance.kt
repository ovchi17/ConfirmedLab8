package workCommandsList

import dataSet.Route
import dataSet.RouteComporator
import java.util.PriorityQueue

/**
 * Class FilterLessThanDistance. Delete all objects with distance less then.
 *
 * @author jutsoNNN
 * @since 1.0.0
 */
class FilterLessThanDistance: Command() {

    /**
     * execute method. Delete all objects where distance is less than given
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        var flag = false
        var counter = 0

        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())

        val checkDistance = (getArgs[0] as Double).toLong()

        if (collection.size == 0){
            workWithResultModule.setMessages("Empty collection")
        }else if(collection.size == 1){
            if (collection.peek().distance < checkDistance.toString().toLong()){
                counter++
                collection.poll()
            }else{
                workWithResultModule.setMessages("noResult")
            }
        }else{
            for (i in 0..collection.size - 1){
                if (collection.peek().distance < checkDistance.toString().toLong()){
                    counter++
                    collection.poll()
                    flag = true
                }

            }
        }
        if(!flag){
            workWithResultModule.setMessages("noResult")
        }
        if (counter > 0){
            workWithResultModule.setMessages("Success (deleted $counter elements)")
        }
        workWithCollection.clearCollection()
        workWithCollection.addAllElementToCollection(collection)
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}