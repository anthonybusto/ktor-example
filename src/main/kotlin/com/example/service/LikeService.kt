package com.example.service

import com.example.domain.dao.LikeDao
import com.example.domain.dao.toLike
import com.example.error.EntityNotFoundException
import com.example.extensions.any
import com.example.extensions.paginate
import com.example.model.Like
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


private const val PAGE_SIZE = 20

object LikeService {

    private val dao get() = LikeDao
    private val mapper: (entity: LikeDao) -> Like = { it.toLike() }

    fun all(): List<Like> = transaction {
        dao.all().map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        orderBy: Pair<Expression<*>, SortOrder> = dao.table.id to SortOrder.ASC
    ): List<Like> = transaction {
        dao.all()
            .let { data -> page?.let { data.paginate(page, pageSize) } ?: data }
            .orderBy(orderBy)
            .map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sortOrder: SortOrder = SortOrder.ASC
    ): List<Like> = transaction {
        all(page, pageSize, dao.table.id to sortOrder)
    }

    fun any(): Boolean = transaction {
        return@transaction dao.any()
    }

    fun any(predicate: (LikeDao) -> Boolean): Boolean = transaction {
        return@transaction dao.any(predicate)
    }

    fun delete(id: Long) = transaction {
        val like = dao.findById(id) ?: throw EntityNotFoundException()
        like.delete()
    }

    fun deleteAll() = transaction { dao.table.deleteAll() }

    fun find(id: Long): Like = transaction {
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
    ): Like = transaction {
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
    ): List<Like> = transaction {

        dao.find(op)
            .orderBy(orderBy)
            .map(mapper)
    }

    fun find(op: SqlExpressionBuilder.() -> Op<Boolean>): List<Like> = transaction {
        dao.find(op).map(mapper)
    }


    fun first(ex: Expression<*>): Like = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.ASC
        )
    }

    fun firstLimit(
        limit: Int,
        ex: Expression<*>
    ): Like = transaction {
        find(
            limit = limit,
            order = ex to SortOrder.ASC
        )
    }

    fun insert(model: Like): Like = transaction {
        return@transaction dao.insert(model)
    }

    fun insertAll(list: List<Like>): List<Like> = transaction {
        return@transaction dao.insertAll(list)
    }

    fun last(ex: Expression<*>): Like? = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.DESC
        )
    }

    fun lastLimit(
        limit: Int,
        orderByColumn: Expression<*>
    ): List<Like> = transaction {
        dao.all()
            .orderBy(orderByColumn to SortOrder.DESC)
            .limit(limit)
            .map { mapper(it) }
            .also { if (it.isEmpty()) throw EntityNotFoundException() }
    }


    fun update(id: Long, block: LikeDao.() -> Unit): Like = transaction {
        return@transaction dao.findById(id)
            ?.apply(block)
            ?.let(mapper)
            ?: throw Exception()
    }
}