package com.example.service

import com.example.domain.dao.CommentDao
import com.example.domain.dao.toComment
import com.example.error.EntityNotFoundException
import com.example.extensions.any
import com.example.extensions.paginate
import com.example.model.Comment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


private const val PAGE_SIZE = 20

object CommentService {

    private val dao get() = CommentDao
    private val mapper: (entity: CommentDao) -> Comment = { it.toComment() }

    fun all(): List<Comment> = transaction {
        dao.all().map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        orderBy: Pair<Expression<*>, SortOrder> = dao.table.id to SortOrder.ASC
    ): List<Comment> = transaction {
        dao.all()
            .let { data -> page?.let { data.paginate(page, pageSize) } ?: data }
            .orderBy(orderBy)
            .map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sortOrder: SortOrder = SortOrder.ASC
    ): List<Comment> = transaction {
        all(page, pageSize, dao.table.id to sortOrder)
    }

    fun any(): Boolean = transaction {
        return@transaction dao.any()
    }

    fun any(predicate: (CommentDao) -> Boolean): Boolean = transaction {
        return@transaction dao.any(predicate)
    }

    fun delete(id: Long) = transaction {
        val comment = dao.findById(id) ?: throw EntityNotFoundException()
        comment.delete()
    }

    fun deleteAll() = transaction { dao.table.deleteAll() }

    fun find(id: Long): Comment = transaction {
//        dao.find {
//            //UserTable.age.eq(1)
//           // UserTable.age.eq(1).and(UserTable.userName.eq("Aaaa"))
//        }
        dao.findById(id)
            ?.let(mapper)
            ?: throw EntityNotFoundException()
    }

    fun find(
        limit: Int,
        order: Pair<Expression<*>, SortOrder>,
    ): Comment = transaction {
        dao.all()
            .orderBy(order)
            .limit(limit)
            .singleOrNull()
            ?.let(mapper)
            ?: throw EntityNotFoundException()
    }


    fun find(
        orderBy: Pair<Expression<*>, SortOrder>,
        op: SqlExpressionBuilder.() -> Op<Boolean>
    ): List<Comment> = transaction {

        dao.find(op)
            .orderBy(orderBy)
            .map(mapper)
    }

    fun find(op: SqlExpressionBuilder.() -> Op<Boolean>): List<Comment> = transaction {
        dao.find(op).map(mapper)
    }


    fun first(ex: Expression<*>): Comment = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.ASC
        )
    }

    fun firstLimit(
        limit: Int,
        ex: Expression<*>
    ): Comment = transaction {
        find(
            limit = limit,
            order = ex to SortOrder.ASC
        )
    }

    fun insert(model: Comment): Comment = transaction {
        return@transaction dao.insert(model)
    }

    fun insertAll(list: List<Comment>): List<Comment> = transaction {
        return@transaction dao.insertAll(list)
    }

    fun last(ex: Expression<*>): Comment? = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.DESC
        )
    }

    fun lastLimit(
        limit: Int,
        orderByColumn: Expression<*>
    ): List<Comment> = transaction {
        dao.all()
            .orderBy(orderByColumn to SortOrder.DESC)
            .limit(limit)
            .map { mapper(it) }
            .also { if (it.isEmpty()) throw EntityNotFoundException() }
    }


    fun update(id: Long, block: CommentDao.() -> Unit): Comment = transaction {
        return@transaction dao.findById(id)
            ?.apply(block)
            ?.let(mapper)
            ?: throw Exception()
    }
}