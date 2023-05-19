package workCommandsList

import dataSet.Route
import java.io.File
import java.util.*

/**
 * Class Load. Load file from server.
 *
 * @author jutsoNNN
 * @since 1.0.0
 */
class Load: Command() {
//    //private var pathToFile: String = System.getenv("DataOfCollection.txt")
//    //private var fileReader: FileReader = FileReader(pathToFile)
//
//    /**
//     * execute method. Save collection to file
//     *
//     * @param getArgs arguments
//     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){
////        val pathToFile: String = System.getProperty("DataOfCollection.server")
////        по факту нет файла=нет путя=нечего загружать, нужно реализовать именно файл НОРМАЛЬНО.
//        val pathToFile: String = System.getenv("DATAOFCOLLECTION")
//        if (!workWithFile.checkFile(pathToFile)){
//            val list = serializer.deserialize(workWithFile.readFile(File(pathToFile)))
//            val collection: PriorityQueue<Route> = workWithCollection.listToCollection(list)
//            var maxId: Int = 0
//            for(i in list.indices){
//                if (collection.element().id > maxId){
//                    maxId = collection.element().id.toInt()
//                }
//                workWithCollection.addElementToCollection(collection.toList().get(i))
//            }
//            if (workWithCollection.getId() < maxId.toLong()){
//                while(workWithCollection.getId() < maxId.toLong()){
//                    workWithCollection.idPlusOne()
//                }
//            }
//        }
//        workWithResultModule.setMessages("loaded")
//        workWithResultModule.setUniqueKey(uniqueToken)
//
//        //serverModule.serverSender(workWithResultModule.getResultModule())
//        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
//        workWithResultModule.clear()
    }
}