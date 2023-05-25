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
        val listOfRoutes = mutableListOf<String>()

        for (obj in collection){
            val objRes = obj.id.toString() + " " + obj.name + " " + obj.creationDate.toString() + " " + obj.from.x.toString() + " " + obj.from.y.toString() + " " + obj.from.z.toString() + " " + obj.to.x.toString() + " " + obj.to.y.toString() + " " + obj.to.z.toString() + " " + obj.distance.toString() + " " + obj.coordinates.x.toString() + " " + obj.coordinates.y.toString() + " " + obj.owner
            listOfRoutes.add(objRes)
        }

        workWithResultModule.setArgs(listOfRoutes)
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}
