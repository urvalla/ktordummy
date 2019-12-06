package com.fridayhack.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.dao.LongEntity
import org.joda.time.DateTime

abstract class BaseTable(name: String = "", columnName: String = "id") : LongIdTable(name = name, columnName = columnName) {
    val createdAt = datetime("created_at").default(DateTime.now()).index()
    val updatedAt = datetime("updated_at").default(DateTime.now()).index()
}

abstract class BaseEntity(id: EntityID<Long>, table: BaseTable) : LongEntity(id) {
    var createdAt by table.createdAt
    var updatedAt by table.updatedAt
}
