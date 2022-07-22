package com.example.service

import ClipDao
import com.example.error.EntityNotFoundException
import com.example.extensions.any
import com.example.extensions.paginate
import com.example.model.Clip
import com.example.model.request.ClipBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import toClip


private const val PAGE_SIZE = 20


object ClipService {

    private val dao get() = ClipDao
    private val mapper: (entity: ClipDao) -> Clip = { it.toClip() }

    fun all(): List<Clip> = transaction {
        dao.all().map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        orderBy: Pair<Expression<*>, SortOrder> = dao.table.id to SortOrder.ASC
    ): List<Clip> = transaction {
        dao.all()
            .let { data -> page?.let { data.paginate(page, pageSize) } ?: data }
            .orderBy(orderBy)
            .map(mapper)
    }

    fun all(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sortOrder: SortOrder = SortOrder.ASC
    ): List<Clip> = transaction {
        all(page, pageSize, dao.table.id to sortOrder)
    }

    fun any(): Boolean = transaction {
        return@transaction dao.any()
    }

    fun any(predicate: (ClipDao) -> Boolean): Boolean = transaction {
        return@transaction dao.any(predicate)
    }

    fun delete(id: Long) = transaction {
        val clip = dao.findById(id) ?: throw EntityNotFoundException()
        clip.delete()
    }

    fun deleteAll() = transaction { dao.table.deleteAll() }

    fun find(id: Long): Clip = transaction {
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
    ): Clip = transaction {
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
    ): List<Clip> = transaction {

        dao.find(op)
            .orderBy(orderBy)
            .map(mapper)
    }

    fun find(op: SqlExpressionBuilder.() -> Op<Boolean>): List<Clip> = transaction {
        dao.find(op).map(mapper)
    }


    fun first(ex: Expression<*>): Clip = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.ASC
        )
    }

    fun firstLimit(
        limit: Int,
        ex: Expression<*>
    ): Clip = transaction {
        find(
            limit = limit,
            order = ex to SortOrder.ASC
        )
    }

    fun insert(model: ClipBody): Clip = transaction {
        return@transaction dao.insert(model)
    }

    fun insertAll(list: List<ClipBody>): List<Clip> = transaction {
        return@transaction dao.insertAll(list)
    }

    fun last(ex: Expression<*>): Clip? = transaction {
        find(
            limit = 1,
            order = ex to SortOrder.DESC
        )
    }

    fun lastLimit(
        limit: Int,
        orderByColumn: Expression<*>
    ): List<Clip> = transaction {



        dao.all()
            .orderBy(orderByColumn to SortOrder.DESC)
            .limit(limit)
            .map { mapper(it) }
            .also { if (it.isEmpty()) throw EntityNotFoundException() }
    }


    fun update(id: Long, block: ClipDao.() -> Unit): Clip = transaction {
        return@transaction dao.findById(id)
            ?.apply(block)
            ?.let(mapper)
            ?: throw EntityNotFoundException()
    }
}