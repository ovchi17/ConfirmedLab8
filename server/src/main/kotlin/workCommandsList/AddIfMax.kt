package workCommandsList

import ShaBuilder
import dataSet.Coordinates
import dataSet.Location
import dataSet.Route
import dataSet.RouteComporator
import java.time.LocalDate
import java.util.*

/**
 * Class AddIfMax. Adds a new object to the collection if Distance parameter is greater than the others.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class AddIfMax: Command() {

    /**
     * execute method. Add object if distance is max
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String) {

        val str = getArgs as List<Any>
        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())
        val hashSHA = ShaBuilder()

        workWithCollection.idPlusOne()
        var id: Long = workWithCollection.getId()
        val name: String?
        val coordinates: Coordinates
        val creationDate: LocalDate = LocalDate.now()
        val from: Location
        val to: Location
        val distance: Long
        val stopper: Long = 1

        name = str[0] as String
        val coord1: Long = (str[1] as Double).toLong()
        val coord2: Long = (str[2] as Double).toLong()
        val location1: Long = (str[3] as Double).toLong()
        val location2: Long = (str[4] as Double).toLong()
        val location3: Int = (str[5] as Double).toInt()
        val location1_2: Long = (str[6] as Double).toLong()
        val location2_2: Long = (str[7] as Double).toLong()
        val location3_2: Int = (str[8] as Double).toInt()
        distance = (str[9] as Double).toLong()
        val owner = serverModule.availableTokens[hashSHA.toSha(login)].toString()
        val saved: Boolean = false

        coordinates = Coordinates(coord1, coord2)
        to = Location(location1, location2, location3)
        from = Location(location1_2, location2_2, location3_2)

        val routeToAdd: Route = Route(
            id,
            name = name,
            coordinates = coordinates,
            creationDate = creationDate,
            from = from,
            to = to,
            distance = distance,
            owner = owner,
            saved = false
        )

        if (collection.size == 0){
            workWithCollection.addElementToCollection(routeToAdd)
            workWithResultModule.setMessages("success")
        }else if(collection.size == 1){
            if (distance > collection.peek().distance){
                workWithCollection.addElementToCollection(routeToAdd)
                workWithResultModule.setMessages("success")
            }else{
                workWithResultModule.setMessages("noSuccess")
            }
        }else{
            var intSr: Int = 0
            for (i in 0..collection.size - 1){
                if (distance > collection.peek().distance) {
                    intSr += 1
                }
            }
            if (intSr == collection.size){
                workWithCollection.addElementToCollection(routeToAdd)
                workWithResultModule.setMessages("success")
            }else{
                workWithResultModule.setMessages("noSuccess")
            }
        }

        workWithResultModule.setUniqueKey(uniqueToken)

        serverModule.availableTokens[hashSHA.toSha(login)]?.let {
            dbModule.addRoute(id, name, creationDate, location1, location2, location3, location1_2, location2_2, location3_2, distance, coord1, coord2,
                it, saved
            )
        }

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}