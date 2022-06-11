package com.example.service

import com.example.domain.dao.UserDao
import com.example.domain.dao.toUser
import com.example.error.EntityNotFoundException
import com.example.extensions.any
import com.example.extensions.paginate
import com.example.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


private const val PAGE_SIZE = 20

object UserService {

    private val dao get() = UserDao
    private val mapper: (entity: UserDao) -> User = { it.toUser() }

    fun all(): List<User> = transaction {
        dao.all().map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        orderBy: Pair<Expression<*>, SortOrder> = dao.table.id to SortOrder.ASC
    ): List<User> = transaction {
        dao.all()
            .let { data -> page?.let { data.paginate(page, pageSize) } ?: data }
            .orderBy(orderBy)
            .map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sortOrder: SortOrder = SortOrder.ASC
    ): List<User> = transaction {
        all(page, pageSize, dao.table.id to sortOrder)
    }

    fun any(): Boolean = transaction {
        return@transaction dao.any()
    }

    fun any(predicate: (UserDao) -> Boolean): Boolean = transaction {
        return@transaction dao.any(predicate)
    }

    fun delete(id: Long) = transaction {
        val user = dao.findById(id) ?: throw EntityNotFoundException()
        user.delete()
    }

    fun deleteAll() = transaction { dao.table.deleteAll() }

    fun find(id: Long): User = transaction {
        dao.findById(id)
            ?.let(mapper)
            ?: throw EntityNotFoundException()
    }

    fun find(
        limit: Int,
        order: Pair<Expression<*>, SortOrder>,
    ): User = transaction {
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
    ): List<User> = transaction {

        dao.find(op)
            .orderBy(orderBy)
            .map(mapper)
    }

    fun find(op: SqlExpressionBuilder.() -> Op<Boolean>): List<User> = transaction {
        dao.find(op).map(mapper)
    }


    fun first(ex: Expression<*>): User = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.ASC
        )
    }

    fun firstLimit(
        limit: Int,
        ex: Expression<*>
    ): User = transaction {
        find(
            limit = limit,
            order = ex to SortOrder.ASC
        )
    }

    fun insert(model: User): User = transaction {
        return@transaction dao.insert(model)
    }

    fun insertAll(list: List<User>): List<User> = transaction {
        return@transaction dao.insertAll(list)
    }

    fun last(ex: Expression<*>): User? = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.DESC
        )
    }

    fun lastLimit(
        limit: Int,
        orderByColumn: Expression<*>
    ): List<User> = transaction {
        dao.all()
            .orderBy(orderByColumn to SortOrder.DESC)
            .limit(limit)
            .map { mapper(it) }
            .also { if (it.isEmpty()) throw EntityNotFoundException() }
    }


    fun update(id: Long, block: UserDao.() -> Unit): User = transaction {
        return@transaction dao.findById(id)
            ?.apply(block)
            ?.let(mapper)
            ?: throw Exception()
    }
}