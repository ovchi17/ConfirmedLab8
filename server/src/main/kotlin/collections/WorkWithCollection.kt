package controllers

import dataSet.*
import java.time.Instant
import java.util.*

/**
 * WorkWithCollection class. Implements CollectionMainCommands
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class WorkWithCollection: CollectionMainCommands {

    private var priorityQueueCollection = PriorityQueue<Route>(RouteComporator())
    private var linkedListCollection = LinkedList<Route>()
    var initData = Date.from(Instant.now())
    var commandHistory:MutableList<String> = mutableListOf()
    var idManager: Long = 1;
    var k: String = "PQ"


    /**
     * getCollection method. Return collection
     *
     * @return PriorityQueue<Route>
     */

    @Synchronized
    override fun getCollection(): Collection<Route> {
        if (k == "PQ"){
            return priorityQueueCollection
        }else{
            return linkedListCollection
        }
    }

    /**
     * getId method. Returns id
     *
     * @return message as Long
     */
    @Synchronized
    override fun getId(): Long {
        return idManager
    }

    /**
     * idPlusOne method. id increases by one
     *
     */
    @Synchronized
    override fun idPlusOne() {
        idManager++
    }

    @Synchronized
    override fun setInitId(id: Long) {
        idManager = id
    }

    /**
     * clearCollection method. Clear collection
     *
     */

    @Synchronized
    override fun clearCollection() {
        if (k == "PQ"){
            while (!priorityQueueCollection.isEmpty()){
                priorityQueueCollection.remove()
            }
        }else{
            while (!linkedListCollection.isEmpty()){
                linkedListCollection.remove()
            }
        }
    }

    /**
     * addElementToCollection method. Add element to collection
     *
     * @param routeObject: Route. Element to be added
     */

    @Synchronized
    override fun addElementToCollection(routeObject: Route) {
        if (k == "PQ"){
            priorityQueueCollection.add(routeObject)
        }else{
            linkedListCollection.add(routeObject)
        }
    }

    /**
     * addAllElementToCollection method. Add all element to collection
     *
     * @param collection
     */
    @Synchronized
    override fun addAllElementToCollection(collection: Collection<Route>) {
        if (k == "PQ"){
            priorityQueueCollection.addAll(collection)
        }else{
            linkedListCollection.addAll(collection)
        }
    }

    /**
     * historyUpdate method. Update history of commands(max size is 14)
     *
     * @param commandFromUser: String. New command that will be added in history
     */
    @Synchronized
    override fun historyUpdate(commandFromUser: String){
        if (commandHistory.size == 14){
            commandHistory.remove(commandHistory[0])
        }
        commandHistory.add(commandFromUser)
    }

    /**
     * getHistory method. Show history of commands (max size is 14)
     *
     * @return commandHistory: MutableList<String>. The list of commands that was executed.
     */
    @Synchronized
    override fun getHistory(): MutableList<String>{
        return commandHistory
    }

    /**
     * getInitDate method. Returns the collection init date
     *
     * @return initDate: Date. The date of collection init
     */
    @Synchronized
    override fun getInitDate():Date {
        return initData
    }

    /**
     * changeCollection method. PQ -> LL || LL -> PQ
     *
     */
    @Synchronized
    override fun changeCollection() {
        if (k == "PQ"){
            linkedListCollection.addAll(priorityQueueCollection)
            clearCollection()
            k = "LL"
        }else{
            priorityQueueCollection.addAll(linkedListCollection)
            clearCollection()
            k = "PQ"
        }
    }

    /**
     * collectionToList method. Convert Collection to List
     *
     * @param collection: PriorityQueue<Route>. PriorityQueue Collection that heeded to convert
     * @return priorityQueueCollection.toList(). Returns PriorityQueue Collection in List format
     */
    @Synchronized
    override fun collectionToList(): List<Route> {
        return if (k == "PQ"){
            priorityQueueCollection.toList()
        }else{
            linkedListCollection.toList()
        }
    }

    /**
     * pollCollection method. Poll function
     *
     * @return object: Route?. The date of collection init
     */
    @Synchronized
    override fun pollCollection(): Route? {
        return if (k == "PQ"){
            priorityQueueCollection.poll()
        }else{
            linkedListCollection.poll()
        }
    }

    /**
     * peekCollection method. Peek collection
     *
     * @return message as Route?
     */
    @Synchronized
    override fun peekCollection(): Route? {
        return if (k == "PQ"){
            priorityQueueCollection.peek()
        }else{
            linkedListCollection.peek()
        }
    }

    /**
     * checkCollection method. Returns info
     *
     * @return message as String
     */
    @Synchronized
    override fun checkCollection(): String{
        return k
    }

    /**
     * listToCollection method. Convert List to Collection
     *
     * @param list: List<Route>. List that heeded to convert
     * @return list.toCollection(PriorityQueue<Route>(RouteComporator())). Returns List in PriorityQueue Collection format
     */
    @Synchronized
    override fun listToCollection(list: List<Route>): PriorityQueue<Route>{
        return list.toCollection(PriorityQueue<Route>(RouteComporator()))
    }

}