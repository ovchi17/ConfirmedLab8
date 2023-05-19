package moduleWithResults

/**
 * Class WorkWithResultModule. Contains function to work with returned result, also contains ResultModule object
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class WorkWithResultModule {
    var result: ResultModule = ResultModule(mutableListOf(), Status.SUCCESS, null, "noCommand", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), "noToken", "noKey")

    /**
     * getMessages method. Return messages from commands
     *
     * @return message as List<String>
     */
    fun getMessages(): List<String>{
        return result.msgToPrint
    }

    /**
     * setMessages method. Set messages from commands
     *
     * @param messages
     */
    fun setMessages(messages: String) {
        result.msgToPrint.add(messages)
    }

    /**
     * getStatus method. Return Status from commands
     *
     * @return message as Status
     */
    fun getStatus(): Status{
        return result.status
    }

    /**
     * setStatus method. Set Status from commands
     *
     * @param status
     */
    fun setStatus(status: Status){
        result.status = status
    }

    /**
     * SetError method. Set errors from commands
     *
     * @param error
     */
    fun setError(error: String){
        result.errorDescription = error
    }

    /**
     * getError method. Return errors from commands
     *
     * @return message as String
     */
    fun getError(): String?{
        return result.errorDescription
    }

    /**
     * setCommand method. Set commands
     *
     * @param cmd as String
     */
    fun setCommand(cmd: String){
        result.commandName = cmd
    }

    /**
     * getCommand method. Get commands
     *
     * @return String
     */
    fun getCommand(): String{
        return result.commandName
    }

    /**
     * setArgs method. Set arguments
     *
     * @param arguments as List
     */
    fun setArgs(arguments: List<Any>){
        result.args.addAll(arguments)
    }

    /**
     * getArgs method. Get arguments
     *
     * @return List
     */
    fun getArgs(): List<Any>{
        return result.args
    }

    /**
     * setListNo method. Helps with distribution
     *
     * @param arguments List
     */
    fun setListNo(arguments: List<String>){
        result.listOfNo.addAll(arguments)
    }

    /**
     * setListLong method. Helps with distribution
     *
     * @param arguments List
     */
    fun setListLong(arguments: List<String>){
        result.listOfLong.addAll(arguments)
    }

    /**
     * setListObject method. Helps with distribution
     *
     * @param arguments List
     */
    fun setListObject(arguments: List<String>){
        result.listOfObject.addAll(arguments)
    }

    /**
     * setListObjectPlus method. Helps with distribution
     *
     * @param arguments List
     */
    fun setListObjectPlus(arguments: List<String>){
        result.listOfObjectPlus.addAll(arguments)
    }

    /**
     * getResultModule method. Returns the result module
     *
     * @return message as ResultModule
     */
    fun getResultModule(): ResultModule{
        return result
    }

    fun setToken(token: String){
        result.token = token
    }

    fun getToken(): String{
        return result.token
    }

    fun setUniqueKey(uniqueKey: String){
        result.uniqueKey = uniqueKey
    }

    fun getUniqueKey(): String{
        return result.uniqueKey
    }

    fun clear(){
        result = ResultModule(mutableListOf(), Status.SUCCESS, null, "noCommand", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), "noToken", "noKey")
    }

}