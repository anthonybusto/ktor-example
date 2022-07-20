package com.example.domain.dao

import com.example.domain.table.CommentTable
import com.example.model.Comment
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  CommentDao(id: EntityID<Long>) : LongEntity(id) {
    var userName by CommentTable.userName
    var userId by CommentTable.userId
    var content by CommentTable.content


    companion object : LongEntityClass<CommentDao>(CommentTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: Comment): Comment = this.new {
            userName = model.userName
            userId = model.userId
            content = model.content
        }.toComment()

        fun insertAll(list: List<Comment>): List<Comment> = list.map { insert(it) }
    }
}

fun CommentDao.toComment() = Comment(
    id = id.value,
    userName = userName,
    userId = userId,
    content = content,
    )