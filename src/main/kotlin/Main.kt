import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class Restaurant(

    @BsonId
    val id: ObjectId,

    val name: String,

    @BsonProperty("restaurant_id")
    val restaurantId: String

)
