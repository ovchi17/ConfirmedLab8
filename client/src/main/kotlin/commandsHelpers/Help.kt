package commandsHelpers

import usersView.ConsoleWriter

/**
 * Class Help. Shows all commands.
 *
 * @author OvchinnikovI17
 * @since 1.0.0
 */
class Help {

    /**
     * execute method. Returns add commands with info
     *
     */
    fun execute() {
        val writeToConsole: ConsoleWriter = ConsoleWriter()

        writeToConsole.printToConsoleLn("***")

        writeToConsole.printToConsoleLn("helpHelp")
        writeToConsole.printToConsoleLn("helpInfo")
        writeToConsole.printToConsoleLn("helpShow")
        writeToConsole.printToConsoleLn("helpAdd")
        writeToConsole.printToConsoleLn("helpUpdate")
        writeToConsole.printToConsoleLn("helpRemove")
        writeToConsole.printToConsoleLn("helpClear")
        writeToConsole.printToConsoleLn("helpSave")
        writeToConsole.printToConsoleLn("helpLoad")
        writeToConsole.printToConsoleLn("helpExecute")
        writeToConsole.printToConsoleLn("helpExit")
        writeToConsole.printToConsoleLn("helpRemoveFirst")
        writeToConsole.printToConsoleLn("helpAddIfMax")
        writeToConsole.printToConsoleLn("helpHistory")
        writeToConsole.printToConsoleLn("helpRemoveAllByDistance")
        writeToConsole.printToConsoleLn("helpAverageOfDistance")
        writeToConsole.printToConsoleLn("helpFilterLessThanDistance")
        writeToConsole.printToConsoleLn("helpSwitchCollection")

        writeToConsole.printToConsoleLn("***")

    }
}