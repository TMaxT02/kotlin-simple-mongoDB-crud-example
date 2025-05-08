import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class CalculatorTest {

    @BeforeEach
    fun setup(): Unit = runBlocking {
        val db = async { RestaurantCrud.setupConnection() }.await()
        if (db == null) throw RuntimeException("MongoDB-Connection failed")
        RestaurantCrud.clear()
    }

    @Test
    fun `Create Operation Test`() = runBlocking {
        val newRestaurant = Restaurant(ObjectId.get(), "Test Restaurant", "test-restaurant-id")
        RestaurantCrud.create(newRestaurant)

        val result = RestaurantCrud.read("test-restaurant-id")
        assertNotNull(result)
        assertEquals("Test Restaurant", result?.name)
    }

    @Test
    fun `Read Operation Test`() = runBlocking {
        val newRestaurant = Restaurant(ObjectId.get(), "Test Restaurant", "test-restaurant-id")
        RestaurantCrud.create(newRestaurant)

        val result = RestaurantCrud.read("test-restaurant-id")
        assertNotNull(result)
        assertEquals("Test Restaurant", result?.name)
    }

    @Test
    fun `Update Operation Test`() = runBlocking {
        val newRestaurant = Restaurant(ObjectId.get(), "Test Restaurant", "test-restaurant-id")
        RestaurantCrud.create(newRestaurant)

        RestaurantCrud.updateName("test-restaurant-id", "Updated Restaurant")

        val updated = RestaurantCrud.read("test-restaurant-id")
        assertNotNull(updated)
        assertEquals("Updated Restaurant", updated?.name)
    }

    @Test
    fun `Delete Operation Test`() = runBlocking {
        val restaurant = Restaurant(ObjectId.get(), "To Delete", "test-restaurant-id")
        RestaurantCrud.create(restaurant)

        assertNotNull(RestaurantCrud.read("test-restaurant-id"))

        RestaurantCrud.delete("test-restaurant-id")

        val result = RestaurantCrud.read("test-restaurant-id")
        assertNull(result)
    }
}
