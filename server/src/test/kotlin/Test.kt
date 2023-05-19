import dataSet.Coordinates
import dataSet.Location
import di.serverModule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import workCommandsList.Command
import workCommandsList.Token
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDate
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Test: KoinComponent {

    private var userRepository: DataBaseManager = DataBaseManager()

    val url = "jdbc:postgresql://localhost:5433/studs"
    val user = "postgres"
    val pas = "admin"

    @BeforeAll
    fun setUp() {
        DriverManager.getConnection("jdbc:postgresql://localhost:5433/studs", user, pas).use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("SELECT * FROM public.\"Route\"")
            }
        }
        try {
            val userRepository = DriverManager.getConnection(url, user, pas)
        } catch (e: SQLException) {
            throw e
        }
    }

    @AfterAll
    fun tearDown() {
        DriverManager.getConnection("jdbc:postgresql://localhost:5433/studs", user, pas).use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("DELETE FROM public.\"Route\"")
            }
        }
    }

    @Test
    fun TestToken() {

        startKoin {
            modules(serverModule)
        }

        val token: Token = Token()
        val sendList = mutableListOf<Any>("lg:ps")
        val login: String = "login"
        val uniqueToken: String = "bebra"
        val serverModule: ServerModule by inject()

        token.execute(sendList, login, uniqueToken)
        val n = serverModule.queueExeSen.take().token

        val sendList2 = mutableListOf<Any>("lg:ps")
        token.execute(sendList2, login, uniqueToken)
        val n2 = serverModule.queueExeSen.take().token

        assertEquals(n, n2)

    }

    @Test
    fun TestRoute() {
        var id:Long = 1
        var name: String = "AlexeyIvanovIskander"
        var creationDate: LocalDate = LocalDate.now()
        var from: Location = Location(3, 3,3)
        var to: Location = Location(3, 3,3)
        var distance: Long = 3
        var coordinates: Coordinates = Coordinates(3,3)
        var owner: String = "testuser"
        var saved: Boolean = true
        val route = dataSet.Route(id,name,creationDate,from, to,distance, coordinates,owner,saved)
        userRepository.addRoute(id,name,creationDate,3, 3,3, 3, 3,3,distance, 3,3,owner,saved)


    }

}