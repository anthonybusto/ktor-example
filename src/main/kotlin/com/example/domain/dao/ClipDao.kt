import com.example.domain.dao.UsersDao
import com.example.domain.dao.toUser
import com.example.domain.table.ClipTable
import com.example.domain.table.RoomTable
import com.example.error.EntityNotFoundException
import com.example.model.Clip
import com.example.model.request.ClipBody
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  ClipDao(id: EntityID<Long>) : LongEntity(id) {
//    var userName by ClipTable.userName

    var user by UsersDao referencedOn ClipTable.user
    var url by ClipTable.url
    var likes by ClipTable.likes
    var comments by ClipTable.comments
    var description by ClipTable.description


    companion object : LongEntityClass<ClipDao>(ClipTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: ClipBody): Clip = this.new {
            user = UsersDao.findById(model.userId)
                ?: throw EntityNotFoundException("Couldn't create Clip object! No user found with ID=${model.userId}")

            url = model.url
            likes = model.likes
            comments = model.comments
            description = model.description
        }.toClip()

        fun insertAll(list: List<ClipBody>): List<Clip> = list.map { insert(it) }
    }
}

fun ClipDao.toClip() = Clip(
    id = id.value,
//    userId = user.id.value,
//    userName = user.userName,
    user = user.toUser(),
    url = url,
    likes = likes,
    comments = comments,
    description = description,
)