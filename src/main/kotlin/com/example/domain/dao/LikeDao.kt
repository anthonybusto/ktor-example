package com.example.domain.dao
import com.example.domain.table.LikeTable
import com.example.model.Like
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  LikeDao(id: EntityID<Long>) : LongEntity(id) {
    var userName by LikeTable.userName
    var userId by LikeTable.userId
    var count by LikeTable.count


    companion object : LongEntityClass<LikeDao>(LikeTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: Like): Like = this.new {
            userName = model.userName
            userId = model.userId
            count = model.count
        }.toLike()

        fun insertAll(list: List<Like>): List<Like> = list.map { insert(it) }
    }
}

fun LikeDao.toLike() = Like(
    id = id.value,
    userName = userName,
    userId = userId,
    count = count,
)