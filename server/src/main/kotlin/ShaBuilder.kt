import java.security.MessageDigest

class ShaBuilder {
    fun toSha(getString: String): String{
        val bytes = getString.toByteArray()
        val md = MessageDigest.getInstance("SHA-1")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}