package commandsHelpers

import java.util.*


class KeyGenerator {

    fun generateRandomKey(): String {
        val uses = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val length = 15
        val random = Random()

        return (1..length)
            .map { uses[random.nextInt(uses.length)] }
            .joinToString("")
    }

}