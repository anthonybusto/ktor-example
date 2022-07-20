package com.example.domain.dao
import com.example.domain.table.OrderTable
import com.example.model.Order
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  OrderDao(id: EntityID<Long>) : LongEntity(id) {
    var firstName by OrderTable.firstName
    var lastName by OrderTable.lastName
    var address by OrderTable.address
    var city by OrderTable.city
    var state by OrderTable.state
    var country by OrderTable.country
    var zip by OrderTable.zip
    var total by OrderTable.total
    var userId by OrderTable.userId
    var orderDate by OrderTable.orderDate




    companion object : LongEntityClass<OrderDao>(OrderTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: Order): Order = this.new {
            firstName = model.firstName
            lastName = model.lastName
            address = model.address
            city = model.city
            state = model.state
            country = model.country
            zip = model.zip
            total = model.total
            userId = model.userId
            orderDate = model.orderDate

        }.toOrder()

        fun insertAll(list: List<Order>): List<Order> = list.map { insert(it) }
    }
}

fun OrderDao.toOrder() = Order(
    id = id.value,
    firstName = firstName,
    lastName = lastName,
    address = address,
    city = city,
    state = state,
    country = country,
    zip = zip,
    total = total,
    userId = userId,
    orderDate = orderDate,
    )