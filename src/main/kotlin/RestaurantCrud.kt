import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.BsonInt64
import org.bson.Document

object RestaurantCrud {
    private lateinit var database: MongoDatabase
    private lateinit var collection: MongoCollection<Restaurant>

    suspend fun setupConnection(
        databaseName: String = "test",
        connectionEnvVariable: String = "MONGODB_URI"
    ): MongoDatabase? {
        /*
        You need to go to https://cloud.mongodb.com, create a cluster, and set a username with its password.
        The connection string will then be generated on the website.
        You just need to enter the username and password into the string, and then insert it into the connectString variable on line 22.
        A more detailed official documentation (guide) can be found at https://www.mongodb.com/developer/products/mongodb/getting-started-kotlin-driver/.
        */
        val connectString = System.getenv(connectionEnvVariable)
            ?: "mongodb+srv://<usename>:<password>@cluster0.sq3aiau.mongodb.net/?retryWrites=true&w=majority"
        val client = MongoClient.create(connectString)
        val db = client.getDatabase(databaseName)

        return try {
            val command = Document("ping", BsonInt64(1))
            db.runCommand(command)
            println("Pinged your deployment. You successfully connected to MongoDB!")
            database = db
            collection = database.getCollection("restaurants")
            database
        } catch (me: MongoException) {
            System.err.println(me)
            null
        }
    }

    suspend fun create(restaurant: Restaurant) {
        collection.insertOne(restaurant)
    }

    suspend fun read(restaurantId: String): Restaurant? {
        return collection.find(Document("restaurant_id", restaurantId)).firstOrNull()
    }

    suspend fun updateName(restaurantId: String, newName: String) {
        val updateDocument = Document("\$set", Document("name", newName))
        collection.updateOne(Document("restaurant_id", restaurantId), updateDocument)
    }

    suspend fun delete(restaurantId: String) {
        collection.deleteOne(Document("restaurant_id", restaurantId))
    }

    suspend fun clear() {
        collection.deleteMany(Document())
    }
}
