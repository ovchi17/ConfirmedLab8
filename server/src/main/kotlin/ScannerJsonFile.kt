import org.koin.core.component.KoinComponent


/**
 * Class ScannerJsonFile. Scan Json File
 *
 * @author justoNNN
 * @since 1.0.0
 */
class ScannerJsonFile: KoinComponent {
//
//    val workWithCollection: CollectionMainCommands by inject()
//
//    /**
//     * scanFile method.
//     *
//     */
//    fun scanFile(){
//        val workWithFile: WorkWithFile = WorkWithFile()
//        val pathToFile: String = System.getenv("DataOfCollection.txt")
//        val serializer: Serializer = Serializer()
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
//    }
}