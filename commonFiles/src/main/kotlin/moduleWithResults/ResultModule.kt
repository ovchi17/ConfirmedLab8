package moduleWithResults
/**
 * Data Class ResultModule. Contains results messages
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
data class ResultModule (
    var msgToPrint:MutableList<String>,
    var status: Status,
    var errorDescription: String?,
    var commandName: String,
    var args: MutableList<Any>,
    var listOfNo: MutableList<String>,
    var listOfLong: MutableList<String>,
    var listOfObject: MutableList<String>,
    var listOfObjectPlus: MutableList<String>,
    var token: String,
    var uniqueKey: String
)

